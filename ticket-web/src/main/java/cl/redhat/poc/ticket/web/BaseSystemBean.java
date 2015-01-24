package cl.redhat.poc.ticket.web;

import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import cl.redhat.poc.ticket.web.util.CargarProperties;

@ViewScoped
@ManagedBean(name = "baseBean")
public class BaseSystemBean implements Serializable {

	private static final long serialVersionUID = -6239437588285327644L;
	
	Logger logger = Logger.getLogger(BaseSystemBean.class);
	
	// atributos para manejo de valores de configuracion
	private static final String PROP_FILE="/ticket-rest.properties"; 
	
	public static String REST_HOSTNAME;
	public static String REST_PORT;

	public void loadDatosBase() {
		
		try {
			
			//1- Se cargan datos de configuracion base...
			
			if(REST_HOSTNAME==null){
				logger.info("Cargando valores rest base...");
				Properties properties = null;
				properties=CargarProperties.cargaPropiedades(properties, PROP_FILE);
				if(properties!=null){
					REST_HOSTNAME=properties.getProperty("ticket.rest.server.name");
					REST_PORT=properties.getProperty("ticket.rest.server.port");
				}
				logger.info("Valores base rest cargados...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}