package cl.redhat.poc.ticket.business.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cl.redhat.poc.ticket.business.persistence.dao.TicketDAO;
import cl.redhat.poc.ticket.business.vo.TicketVO;

@Stateless
public class TicketManagerBean implements TicketManager{

	@EJB TicketDAO ticketDAO;
	
	public List<TicketVO> findTickets(TicketVO ticket) {
		
		List<TicketVO> lsTickets = null;
		
		try{
			
			lsTickets = ticketDAO.getTicketsByParameters(ticket);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return lsTickets;
	}

	@Override
	public TicketVO creaTicket(TicketVO ticketVO) {
		try{
			
			ticketVO = ticketDAO.crearTicket(ticketVO);
			
		}catch(Exception e){
			e.printStackTrace();
			ticketVO = null;
		}
		return ticketVO;
	}

}
