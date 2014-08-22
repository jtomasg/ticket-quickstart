package cl.isl.poc.ticket.actions;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import cl.redhat.poc.ticket.business.vo.TicketVO;

public class TicketCustomAction {

	@Process
	public Message creaTicketFromJSON(Message message){
		try{
			
			String strJSON = (String) message.getBody().get(Body.DEFAULT_LOCATION);
			
			if(strJSON!=null){
				try{
					
					TicketVO ticketVO = new ObjectMapper().readValue(strJSON, TicketVO.class);
					
					message.getBody().add("ticket", ticketVO);
					
				}catch(Exception e){
					System.out.println("No se pudo parsear el JSON a un TicketVO. El JSON es:\n'"+strJSON+"'");
					e.printStackTrace();
				}
			}else
				System.out.println("No existe JSON valido!");
			
		}catch(Exception e){
			
		}
		return message;
	}
	
	@Process
	public Message generaTicketJSON(Message message){
		try{
			
			TicketVO ticketVO = (TicketVO) message.getBody().get("ticketRespuesta");
			
			String strJSON = new ObjectMapper().writeValueAsString(ticketVO);
			
			message.getBody().add(Body.DEFAULT_LOCATION, strJSON);
			
		}catch(Exception e){
			
		}
		return message;
	}
	
}
