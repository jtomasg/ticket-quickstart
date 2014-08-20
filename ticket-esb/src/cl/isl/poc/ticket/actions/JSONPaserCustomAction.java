package cl.isl.poc.ticket.actions;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import cl.redhat.poc.ticket.business.vo.TicketVO;

public class JSONPaserCustomAction {
	
	@Process
	public Message toJSON(Message message){
		
		try{
			
			List<TicketVO> lsTickets = (List<TicketVO>) message.getBody().get("tickets");

			// POJO a JSON
				String strJSON = new ObjectMapper().writeValueAsString(lsTickets);

				System.out.println("====JSON: \n"+strJSON);
				
				message.getBody().add(Body.DEFAULT_LOCATION,strJSON);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
}
