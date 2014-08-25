package cl.isl.poc.ticket.actions;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import cl.redhat.poc.ticket.business.vo.TicketVO;
import cl.redhat.poc.ticket.business.vo.UsuarioVO;

public class JSONPaserCustomAction {
	
	@Process
	public Message toJSON(Message message){
		
		try{
			
			@SuppressWarnings("unchecked")
			List<TicketVO> lsTickets = (List<TicketVO>) message.getBody().get("tickets");

			// POJO a JSON
				String strJSON = new ObjectMapper().writeValueAsString(lsTickets);

				// System.out.println("====JSON: \n"+strJSON);
				
				message.getBody().add(Body.DEFAULT_LOCATION,strJSON);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	@Process
	public Message usuarioToJSON(Message message){
		
		try{
			
			UsuarioVO usuarioVO = (UsuarioVO) message.getBody().get("usuarioVO");

			// POJO a JSON
				String strJSON = new ObjectMapper().writeValueAsString(usuarioVO);

				// System.out.println("====JSON: \n"+strJSON);
				
				message.getBody().add(Body.DEFAULT_LOCATION,strJSON);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
}
