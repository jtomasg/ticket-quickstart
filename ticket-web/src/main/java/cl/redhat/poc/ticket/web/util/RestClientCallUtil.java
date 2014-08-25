package cl.redhat.poc.ticket.web.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestClientCallUtil {

	public String callJsonRemoteRest(String endpointURL) throws Exception {

		String strJson = null;
		ClientConfig clientConfig = new DefaultClientConfig();

		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);

		Client client = Client.create(clientConfig);

		WebResource webResource = client.resource(endpointURL);

		ClientResponse response = webResource.accept("application/json")
				.type("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		strJson = response.getEntity(String.class);
		
		return strJson;
	}

}
