package cl.isl.poc.ticket.actions;

import java.util.HashMap;
import java.util.Map;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.configure.ConfigProperty;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import cl.redhat.poc.ticket.business.vo.TicketVO;
import cl.redhat.poc.ticket.business.vo.UsuarioVO;

public class RestUrlPaserCustomAction {

	@ConfigProperty
	String pathParams;
	
	@Process
	public Message getParams(Message message){
		try{
			
			HttpRequest req = HttpRequest.getRequest(message);
			
			String restParams = req.getRequestURI()
					.replace(req.getRequestPath(), "").
					replace(req.getContextPath(), "");
			
			Map<String,String> parametros = getPathParams(restParams);
			
			if(parametros!=null){
			// Filtro a utilizar en llamada a logica de negocio de busqueda de tickets
				TicketVO ticketVO = new TicketVO();
				ticketVO.setOwnerID( parametros.get("oid")!=null?new Long(parametros.get("oid")):null );
				message.getBody().add("filtro",ticketVO);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return message;
	}
	
	@Process
	public Message getParamsUsuario(Message message){
		try{
			
			HttpRequest req = HttpRequest.getRequest(message);
			
			String restParams = req.getRequestURI()
					.replace(req.getRequestPath(), "").
					replace(req.getContextPath(), "");
			
			Map<String,String> parametros = getPathParams(restParams);
			
			if(parametros!=null){
			// Filtro a utilizar en llamada a logica de negocio de busqueda de tickets
				UsuarioVO usuarioVO = new UsuarioVO();
				String email =  parametros.get("email")!=null?new String(parametros.get("email")):null;
				message.getBody().add("email", email);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return message;
	}
	
	private Map<String,String> getPathParams(String urlParams){
		
		Map<String,String> mapParams = null;
		
		try{
			
			if(pathParams!=null && (urlParams !=null && !urlParams.equals(""))){
				mapParams = new HashMap<String, String>();
				
				String[] params = pathParams.replaceAll("\\{","").split("\\}");
				String[] values = urlParams.split("\\/");
				
				for(int i=0; i<params.length;i++){
					try{
						
						mapParams.put(params[i], values[(2*i)+2]);
						
					}catch(ArrayIndexOutOfBoundsException oobe){
						System.out.println("ERROR: parametros inconsistentes en la peticion!");
						mapParams = null;
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapParams;
	}
	
	
}
