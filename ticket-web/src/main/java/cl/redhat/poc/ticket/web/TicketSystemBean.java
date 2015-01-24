package cl.redhat.poc.ticket.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;

import cl.redhat.poc.ticket.model.vo.SistemaVO;
import cl.redhat.poc.ticket.model.vo.TicketVO;
import cl.redhat.poc.ticket.web.util.RestClientCallUtil;

@ViewScoped
@ManagedBean(name = "ticketBean")
public class TicketSystemBean extends BaseSystemBean implements Serializable {

	private static final long serialVersionUID = -6239437588285327644L;
	
	Logger logger = Logger.getLogger(TicketSystemBean.class);
	
	private TicketVO[] lsTickets;
	private TicketVO ticket;
	private String uid;
	private String email;
	
	@ManagedProperty(value="#{usuarioBean}")
	UsuarioBean userBean;
	
	@PostConstruct
	public void postContruct() {
		
		try {
			
			super.loadDatosBase();
			
			//1-  se cargan datos del usuario de conexion...
				buscarTicketsPorUsuario(userBean.getUsuario().getId().toString());
			
			initTicket();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void initTicket(){
		this.ticket = new TicketVO();
		ticket.setSistema(new SistemaVO());
	}
	
	public void buscarTickets(){
		buscarTicketsPorUsuario(email);
	}
	
	public void buscarTicketsPorUsuario(String uid) {
		try {

			logger.info("== Buscando tickets...");

			String endpointURL = "http://"+REST_HOSTNAME+":"+REST_PORT+"/rest/tickets/usuarios/"+ uid;

			String output = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL);

			ObjectMapper mapper = new ObjectMapper();
			TicketVO[] tickets = mapper.readValue(output, TicketVO[].class);

			this.lsTickets = tickets;

			logger.info("Busqueda de tickets realizada!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getDetallesTicket(String tid){
		try {

			logger.info("== Obteniendo datos del ticket ID: '"+tid+"'...");

			String endpointURL = "http://"+REST_HOSTNAME+":"+REST_PORT+"/rest/tickets/"+ tid;

			String output = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL);

			ObjectMapper mapper = new ObjectMapper();
			this.ticket = mapper.readValue(output, TicketVO.class);

			logger.info("Datos del ticket "+this.ticket.getId()+" obtenidos!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void claimTicket(String tid, String uid){
		
	}
	
	public void crearTicket(){
		try{
			
			logger.info("Creando ticket...");
			
			String endpointURL = "http://"+REST_HOSTNAME+":"+REST_PORT+"/rest/tickets/nuevo";

			String output = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL, this.ticket);
			
			logger.info("Ticket creado exitosamente!\n"+output);
			
		}catch(Exception e){
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
	public UsuarioBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UsuarioBean userBean) {
		this.userBean = userBean;
	}

	public TicketVO getTicket() {
		return ticket;
	}

	public void setTicket(TicketVO ticket) {
		this.ticket = ticket;
	}
	public TicketVO[] getLsTickets() {
		return lsTickets;
	}

	public void setLsTickets(TicketVO[] lsTickets) {
		this.lsTickets = lsTickets;
	}

}