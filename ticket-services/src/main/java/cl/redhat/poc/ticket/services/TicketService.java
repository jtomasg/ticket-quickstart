package cl.redhat.poc.ticket.services;

import cl.redhat.poc.ticket.model.vo.TicketVO;

public interface TicketService {

	public TicketVO getTicketById(String tid);
	
	public TicketVO[] getTickets(String uid);
	
	public TicketVO[] getAllTickets();
	
	public TicketVO saveUpdateTicket(TicketVO ticket);
	
	public TicketVO deleteTicket(String tid);
	
}
