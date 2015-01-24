package com.redhat.internal.bpmsuite.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.internal.bpmsuite.client.responses.startProcess.CommandResponse.ProcessInstance;
import com.redhat.internal.bpmsuite.client.responses.task.Task;
import com.redhat.internal.bpmsuite.client.responses.task.potencialowner.CommandResponse.TaskSummaryList.TaskSummary;

public class Usage {

	private static final String HOSTNAME="localhost";
	private static final String PORT="8280";
	private static final String USER= "tomas";
	private static final String PASSWD="tomas.14";
	
	private static final String DEPLOYMENT_ID="cl.redhat.poc.ticket:ticket-bpm:0.0.1-SNAPSHOT";
	private static final String PROCESS_DEF_ID= "ticket-bpm.ticket-lifecycle";
	
	public static void main(String[] args) throws Exception{
		
		RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT, USER, PASSWD);
		Usage u = new Usage();
		//INICIAR PROCESO
		u.iniciarProceso(rc);
		
		//Listar posibles tareas
//		List<TaskSummary> ltp = u.listarTareasPotenciales(rc);
//		for (TaskSummary taskSummary : ltp) {
//			p("TAREA "+taskSummary.getName(),true);
//			Task task = rc.getTaskById(rc, taskSummary.getId());
//			Map<String, Object> taskData = rc.getTaskData(rc, task);
//			for (String key: taskData.keySet()) {
//				p("Key["+key+"]="+taskData.get(key), false);
//			}
//		}
		
		
		//Completar tarea
//		u.completarTareaConClaim(rc);
		
		//Obtener variables de proceso
//		u.obtenerVariablesProceso(rc);
		

		
	}
	
	
	public void completarTareaConClaim(RestClientSimple rc) throws Exception {
		p("Buscando tareas asociadas a :"+USER, true);
		List<TaskSummary> taskListForPotentialOwner = rc.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
		for (TaskSummary ts : taskListForPotentialOwner) {
			String task = "tarea {[%s][%s][%s][%s]}";
			task = String.format(task, ts.getId(), ts.getName(),ts.getProcessInstanceId(), ts.getStatus());
			p("Reclamando "+task, false);
//			rc.claimTask(rc, DEPLOYMENT_ID, ts.getId(), USER);
			rc.startTask(rc, DEPLOYMENT_ID, ts.getId(), USER);
			p("Completando "+task,false);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tipo_entrada_","PRESENCIAL");
			params.put("existe_siap_",false);
			params.put("existe_reip_",false);
			params.put("trae_reip_",false);
			params.put("identidad_valida_",true);
			params.put("observaciones_","OBSERVACIONES "+ new Date());
			params.put("reip_valida_",false);
			rc.completeTask(rc, DEPLOYMENT_ID, ts.getId(), USER, params);
		}
		
		
	}


	public void obtenerVariablesProceso(RestClientSimple rc) {
		
		
	}


	public Long iniciarProceso(RestClientSimple rc) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("estadoId", 3);
		params.put("ownerId", 1);
		params.put("supportId", 1);
		
		p("Iniciando procesos con parametros",true);
		p("Parametros: "+params.toString(),false);
		ProcessInstance startProcessWithParameters = rc.startProcessWithParameters(rc, DEPLOYMENT_ID, PROCESS_DEF_ID, params );
		p("Proceso iniciado con ID: "+startProcessWithParameters.getId(),false);
		p("startProcessWithParameters.toString(): "+startProcessWithParameters.toString(),false);
		return startProcessWithParameters.getId();
	};
	
	public List<TaskSummary> listarTareasPotenciales(RestClientSimple rc) throws Exception{
		p("Buscando tareas asociadas a :"+USER, true);
		List<TaskSummary> taskListForPotentialOwner = rc.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
		p("ID\t\tNOMBRE TAREA\t\tID_INSTANCIA_PROCESO\t\tSTATUS", false);
		p("==\t\t============\t\t====================\t\t======", false);
		for (TaskSummary ts : taskListForPotentialOwner) {
			String task = "%s\t\t%s\t\t%s\t\t\t\t%s";
			p(String.format(task, ts.getId(), ts.getName(),ts.getProcessInstanceId(), ts.getStatus()),false);
		}
		return taskListForPotentialOwner;
	}
	
	private static void p(String t, boolean titulo){
		StringBuilder sb =  new StringBuilder();
		sb.append(titulo? "\n\n" : "");
		sb.append(titulo? t.toUpperCase() : "\t>> "+t);
		sb.append(titulo? ".......................................": "");
		System.out.println(sb.toString());
	}
	
}
