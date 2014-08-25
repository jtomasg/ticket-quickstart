package cl.redhat.poc.ticket.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import cl.redhat.poc.ticket.web.util.RestClientCallUtil;
import cl.redhat.poc.ticket.web.vo.TicketVO;

@ViewScoped
@ManagedBean(name = "ticketBean")
public class TicketSystemBean implements Serializable {

	private static final long serialVersionUID = -6239437588285327644L;

	private TicketVO[] lsTickets;
	private String uid;
	private String email;

	@PostConstruct
	public void postContruct() {
		// getTicketsDummy();
		try {
            UsuarioBean userBean = new UsuarioBean();
			buscarTicketsPorUsuario(userBean.getUsuario().getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @SuppressWarnings("unused")
	// private void getTicketsDummy(){
	// lsTickets = new ArrayList<TicketVO>();
	// Long id = new Date().getTime();
	// for(long i=id;i<(id+10);i++){
	// TicketVO ticket = new TicketVO();
	// ticket.setId(new Long(i));
	// ticket.setAsunto("Ticket "+1);
	// ticket.setFechaCreacion(new Date());
	// ticket.setOwnerID(1l);
	// ticket.setSistema(1l);
	// lsTickets.add(ticket);
	// }
	// }

	public TicketVO[] getLsTickets() {
		return lsTickets;
	}

	public void setLsTickets(TicketVO[] lsTickets) {
		this.lsTickets = lsTickets;
	}
	
	public void buscarTickets(){
		buscarTicketsPorUsuario(email);
	}

	public void buscarTicketsPorUsuario(String uid) {
		try {

			System.out.println("== Buscando tickets...");

			String endpointURL = "http://localhost:8180/ticket-esb/http/getTickets/oid/"
					+ uid;

			String output = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL);

			ObjectMapper mapper = new ObjectMapper();
			TicketVO[] tickets = mapper.readValue(output, TicketVO[].class);

			this.lsTickets = tickets;

			System.out.println("Busqueda de tickets realizada!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}