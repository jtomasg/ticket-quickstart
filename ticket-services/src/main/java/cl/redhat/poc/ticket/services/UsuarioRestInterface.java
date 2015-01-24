package cl.redhat.poc.ticket.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
public interface UsuarioRestInterface {

	@GET
	@Path("/nombre/{nuser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUsuarioByNombre(@PathParam("nuser") String nombre);
	
	
}
