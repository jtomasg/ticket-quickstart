package cl.redhat.poc.ticket.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cl.redhat.poc.ticket.model.vo.TicketVO;

@Path("/tickets")
public interface TicketRestInterface {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTickets();
	
	@GET
	@Path("/{tid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketById(@PathParam("tid") String tid);
	
	@GET
	@Path("/usuarios/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickets(@PathParam("uid") String uid);
	
	@POST
	@Path("/nuevo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TicketVO saveUpdateTicket(TicketVO ticketVo);
	
}
