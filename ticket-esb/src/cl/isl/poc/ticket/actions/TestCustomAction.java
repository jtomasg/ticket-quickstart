package cl.isl.poc.ticket.actions;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import cl.redhat.poc.ticket.business.vo.TicketVO;

public class TestCustomAction {

	@Process
	public Message creaTickets(Message message){
		try{
			
			Date fecha = new Date();
			TicketVO ticketVO = new TicketVO();
			ticketVO.setAsunto("Ticket Ejemplo "+fecha.getTime());
			ticketVO.setDescripcion("Ticket Ejemplo "+fecha.getTime()+" blablabla...");
			ticketVO.setFechaCreacion(fecha);
			ticketVO.setOwnerID(1l);
			ticketVO.setSistema(1l);
			ticketVO.setPrioridad(3l);
			ticketVO.setEstado(1l);
			
			String strJSON = new ObjectMapper().writeValueAsString(ticketVO);
			
			message.getBody().add(Body.DEFAULT_LOCATION, strJSON);
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	@Process
	public Message setHTMLRespuesta(Message message){
		try{
			
			String strHtmlRespuesta = "<html>";
			String strJSON = (String) message.getBody().get(Body.DEFAULT_LOCATION);
			
			if(strJSON!=null){
				try{
					
					TicketVO ticketVO = new ObjectMapper().readValue(strJSON, TicketVO.class);
					
					strHtmlRespuesta = strHtmlRespuesta + "<h1>Ticket Creado!</h1>Ticket ID: "+ ticketVO.getId()+"</br>Fecha Creaci&oacute;n: "+ticketVO.getFechaCreacion().toString();
					
				}catch(Exception e){
					System.out.println("No se pudo parsear el JSON a un TicketVO. El JSON es:\n"+strJSON);
					strHtmlRespuesta = strHtmlRespuesta + "<h1>Ocurri&oacute; un error!</h1>No se pudo parsear el JSON a un TicketVO.</br></br>El JSON es:</br>"
							+ "<span style='font-size:8pt;color:red;'>"+strJSON+"</span>";
				}
			}
			
			strHtmlRespuesta = strHtmlRespuesta + "</html>";
			message.getBody().add(Body.DEFAULT_LOCATION,strHtmlRespuesta);
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
}
