package com.redhat.internal.bpmsuite.client;


import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jbpm.services.task.utils.ContentMarshallerHelper;

import com.redhat.internal.bpmsuite.client.RestClientSimple.PreemptiveAuth;
import com.redhat.internal.bpmsuite.client.responses.content.Content;
import com.redhat.internal.bpmsuite.client.responses.startProcess.CommandResponse;
import com.redhat.internal.bpmsuite.client.responses.task.Task;

public class RestClientSimple {

	private static final String BASE_URL= "http://%s:%s/business-central/rest/";
	
	private final String username;
	private final String password;
	private final String baseUrl;
		
	public RestClientSimple(final String hostname,String port, final String user, final String passwd){
		this.baseUrl = String.format(BASE_URL, hostname, port);
		this.username = user;
		this.password = passwd;
	}

	public CommandResponse.ProcessInstance startProcessWithParameters(RestClientSimple client, String deploymentId, String processDefinitionId, Map<String, Object> params) throws Exception {
		String newInstanceUrl = baseUrl + "runtime/" + deploymentId 	+ "/execute";
		String data = RestJBPM.getStartProcess(deploymentId, processDefinitionId, params);
		System.out.println("Request data-->"+data);
		String dataFromService = client.getDataFromService(newInstanceUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
		com.redhat.internal.bpmsuite.client.responses.startProcess.CommandResponse commandResponse = 
				createResponse(com.redhat.internal.bpmsuite.client.responses.startProcess.CommandResponse.class, dataFromService);
		return commandResponse.getProcessInstance();
	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTaskData(RestClientSimple client, Task bpmTask) throws Exception {
		Content content = client.getContentById(client, bpmTask.getJaxbTaskData().getDocumentContentId());
		Map<String, Object> data = null;
		if ((content != null) && (content.getSerializedContent() != null)) {
			byte[] contentBytes = Base64.decodeBase64(content.getSerializedContent());
			Object unmarshalledObject = ContentMarshallerHelper.unmarshall(contentBytes, null);
			if(unmarshalledObject != null && unmarshalledObject instanceof Map){
				data = ((Map<String, Object>)unmarshalledObject);
            }
		}
		if(data != null){
			for (Map.Entry<String, Object> entry : data.entrySet()){
				System.out.println(entry.getKey() + "/" + entry.getValue());
			}
		}
		return data;
	}
	
	private String getDataFromService(String urlpath, String method,
			String data, boolean b) throws Exception  {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 250000);
		HttpConnectionParams.setSoTimeout(params, 250000);
		HttpClient client = new DefaultHttpClient(params);

		((DefaultHttpClient)client).getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(username, password)
				);
		HttpContext localContext = new BasicHttpContext();

		// Generate BASIC scheme object and stick it to the local execution context
		BasicScheme basicAuth = new BasicScheme();
		localContext.setAttribute("preemptive-auth", basicAuth);

		// Add as the first request interceptor
		((DefaultHttpClient)client).addRequestInterceptor(new PreemptiveAuth(), 0);

		HttpUriRequest request = null;
		if ("GET".equalsIgnoreCase(method)) {
			request = new HttpGet(urlpath);
		} else if ("POST".equalsIgnoreCase(method)) {
			request = new HttpPost(urlpath);

			InetAddress inetAddr = InetAddress.getLocalHost();
			String hostname = inetAddr.getHostName();
			request.setHeader("User-Agent", "org.kie.services.client (1 "+ "/" + hostname + ")");
			((HttpPost)request).setEntity(new StringEntity(data ,"application/xml", "UTF-8"));
		}
		try {
			HttpResponse response = client.execute(request, localContext);
			final int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				throw new Exception("Respuesta inesperada desde servidor " + response.getStatusLine()
						+ " para " + request.getRequestLine());
			}
			String rawResponse = EntityUtils.toString(response.getEntity());
			System.out.println("Call " + request.getURI()+" :: result = " + rawResponse);
			return rawResponse;
		} catch (Exception e) {
			throw e;
		} 
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	static class PreemptiveAuth implements HttpRequestInterceptor {

		public void process(
				final HttpRequest request, 
				final HttpContext context) throws HttpException, IOException {

			AuthState authState = (AuthState) context.getAttribute(
					ClientContext.TARGET_AUTH_STATE);

			// If no auth scheme avaialble yet, try to initialize it preemptively
			if (authState.getAuthScheme() == null) {
				AuthScheme authScheme = (AuthScheme) context.getAttribute(
						"preemptive-auth");
				CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(
						ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context.getAttribute(
						ExecutionContext.HTTP_TARGET_HOST);
				if (authScheme != null) {
					Credentials creds = credsProvider.getCredentials(
							new AuthScope(
									targetHost.getHostName(), 
									targetHost.getPort()));
					if (creds == null) {
						throw new HttpException("No credentials for preemptive authentication");
					}
					authState.setAuthScheme(authScheme);
								authState.setCredentials(creds);
				}
			}

		}

	}

	public List<com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary> getTaskListForPotentialOwner(RestClientSimple client, String deploymentId, String actorId) throws Exception {
		String taskListUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getTaskListForPotentialOwner(deploymentId, actorId);
		System.out.println("data"+data);
		List<com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary> taskSummary = 
				new ArrayList<com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary>();
		String dataFromService = client.getDataFromService(taskListUrl, "POST", data, false);
		com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse commandResponse = 
				createResponse(com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.class, dataFromService);
		if(commandResponse.getTaskSummaryList() != null){
			taskSummary =  commandResponse.getTaskSummaryList().getTaskSummary();
		}
		return taskSummary;
	}
	
	public com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary getTasksByStatusByProcessInstanceId(RestClientSimple client,String deploymentId, long processInstanceId, String actorId) throws Exception {
		String taskUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getTasksByStatusByProcessInstanceId(deploymentId, actorId, processInstanceId);
		System.out.println("Request data-->"+data);
		String dataFromService = client.getDataFromService(taskUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
		com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse commandResponse = 
				createResponse(com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.class, dataFromService);
		com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary task = null;
		if(commandResponse.getTaskSummaryList() != null){
			List<com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary> taskSummary =  commandResponse.getTaskSummaryList().getTaskSummary();
			if(taskSummary != null && taskSummary.size() > 0){
				task = taskSummary.get(0);
			}
		}
		return task;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T createResponse(Class<T> genericType, String xmlData) throws JAXBException {  
		JAXBContext context = JAXBContext.newInstance(genericType);  
		StringReader reader = new StringReader(xmlData);  
		Unmarshaller unmarshaller = context.createUnmarshaller();  
		T commandResponse = (T) unmarshaller.unmarshal(reader);  
		return commandResponse;  
	}

	public com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary getTasksByStatusByProcessInstanceIdTaskId(
			RestClientSimple client,String deploymentId, Long processInstanceId, Long taskId,
			String actorId) throws Exception {
		String taskUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getTasksByStatusByProcessInstanceId(deploymentId, actorId, processInstanceId);
		System.out.println("Request data-->"+data);
		String dataFromService = client.getDataFromService(taskUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
		com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse commandResponse = 
				createResponse(com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.class, dataFromService);
		com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary task = null;
		if(commandResponse.getTaskSummaryList() != null){
			List<com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary> taskSummary =  commandResponse.getTaskSummaryList().getTaskSummary();
			if(taskSummary != null && taskSummary.size() > 0){
				 for(com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary taskSelected : taskSummary){
					 if(taskId == taskSelected.getId()){
						 task = taskSelected;
						 break;
					 }
				 }
			}
		}
		return task;
	}  
	
	
	public void claimTask(RestClientSimple client,String deploymentId, Long taskId,	String actorId) throws Exception {
		String taskUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getClaimTask(deploymentId, taskId, actorId);
		System.out.println("Request data-->"+data);
		String dataFromService = client.getDataFromService(taskUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
	}

	public void startTask(RestClientSimple client, String deploymentId, Long taskId, String actorId) throws Exception {
		String taskUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getStartTask(deploymentId, taskId, actorId);
		System.out.println("Request data startTask-->"+data);
		String dataFromService = client.getDataFromService(taskUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data startTask-->"+dataFromService);
		System.out.println("--------");
	}

	public void completeTask(RestClientSimple client, String deploymentId, Long taskId,
			String actorId, Map<String, Object> params) throws Exception {
		String taskUrl = baseUrl + "task/execute/";
		String data = RestJBPM.getCompleteTask(deploymentId, taskId, actorId, params);
		System.out.println("Request data completeTask-->"+data);
		String dataFromService = client.getDataFromService(taskUrl, "POST", data, false);
		System.out.println("--------");
		System.out.println("Response data completeTask-->"+dataFromService);
		System.out.println("--------");
	}

	public Task getTaskById(RestClientSimple client, Long taskId) throws Exception {
		String taskUrl = baseUrl + "task/"+taskId;
		String dataFromService = client.getDataFromService(taskUrl, "GET", null, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
		Task task = createResponse(Task.class, dataFromService);
		return task;
	}

	public Content getContentById(RestClientSimple client, Long documentContentId) throws Exception {
		String taskUrl = baseUrl + "task/content/"+documentContentId;
		String dataFromService = client.getDataFromService(taskUrl, "GET", null, false);
		System.out.println("--------");
		System.out.println("Response data-->"+dataFromService);
		System.out.println("--------");
		Content content = createResponse(Content.class, dataFromService);
		return content;
	}
	
	//NOTA
	//	 curl -v -X POST -u bpmsAdmin 'http://localhost:8080/business-central/rest/runtime/cl.gob.isl:spe:1.1/withvars/process/instance/1/signal?signal='


}
