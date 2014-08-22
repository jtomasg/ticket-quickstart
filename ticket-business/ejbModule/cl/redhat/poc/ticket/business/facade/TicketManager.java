package cl.redhat.poc.ticket.business.facade;

import java.util.List;

import javax.ejb.Remote;

import cl.redhat.poc.ticket.business.vo.TicketVO;

@Remote
public interface TicketManager {

	public List<TicketVO> findTickets(TicketVO ticket);
	
	public TicketVO creaTicket(TicketVO ticket);
	
}
