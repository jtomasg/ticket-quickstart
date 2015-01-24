package cl.redhat.poc.ticket.services;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.switchyard.component.bean.Service;

import cl.redhat.poc.ticket.business.facade.TicketManager;
import cl.redhat.poc.ticket.model.vo.TicketVO;
import cl.redhat.poc.ticket.model.vo.UsuarioVO;
import cl.redhat.poc.ticket.service.locator.ServiceLocator;
import cl.redhat.poc.ticket.util.CargarProperties;

@Service(TicketService.class)
public class TicketServiceBean implements TicketService {
	
	Logger logger = Logger.getLogger(TicketServiceBean.class);
	
	private static final String PROP_FILE="/ticket.properties"; 
	
	private static String HOSTNAME;
	private static String PORT;
	private static String USER;
	private static String PASSWD;
	
	private static String DEPLOYMENT_ID;
	private static String PROCESS_DEF_ID;

	private TicketManager ticketManagerEjb;
	
	@PostConstruct
	private void getValores(){
		try{
			
			System.out.println("Cargando valores...");
			
			if(HOSTNAME==null){
				Properties properties = null;
				properties=CargarProperties.cargaPropiedades(properties, PROP_FILE);
				if(properties!=null){
					HOSTNAME=properties.getProperty("ticket.bpms.server.name");
					PORT=properties.getProperty("ticket.bpms.server.post");
					USER=properties.getProperty("ticket.bpms.admin.user");
					PASSWD=properties.getProperty("ticket.bpms.admin.password");
					DEPLOYMENT_ID=properties.getProperty("ticket.bpms.deployment.name")+":"+properties.getProperty("ticket.bpms.deployment.version");
					PROCESS_DEF_ID=properties.getProperty("ticket.bpms.process.id");
				}
			}
			
			//System.out.println("HOSTNAME: "+HOSTNAME);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public TicketVO[] getAllTickets() {
		return this.getTickets(null);
	}

	@Override
	public TicketVO[] getTickets(String uid) {

		TicketVO[] arrTickets=null;

		try{
			
			ticketManagerEjb = (TicketManager) ServiceLocator.getInstance()
				.doRemoteLookUp("", "ticket-business", "", "TicketManagerBean",
						TicketManager.class.getName());
			
			// get all tickets...
			TicketVO ticketFiltro= null;
			
			// si existe valor de firltro...
			if(uid!=null){
				ticketFiltro = new TicketVO();
				ticketFiltro.setOwner(new UsuarioVO(Long.valueOf(uid), null, null, null, null));
			}
			
			List<TicketVO> lsTickets = ticketManagerEjb.findTickets(ticketFiltro);
			
			if(lsTickets!=null){
				arrTickets = new TicketVO[lsTickets.size()];
				for(int i=0;i< lsTickets.size();i++){
					arrTickets[i]=lsTickets.get(i);
				}
			}
			
			//System.out.println("Tickets: "+(lsTickets==null?0:lsTickets.size()));
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return arrTickets;
	}
	
	public TicketVO getTicketById(String tid) {

		TicketVO ticket=null;

		try{
			
			ticketManagerEjb = (TicketManager) ServiceLocator.getInstance()
				.doRemoteLookUp("", "ticket-business", "", "TicketManagerBean",
						TicketManager.class.getName());
			
			// get all tickets...
			TicketVO ticketFiltro= null;
			
			// si existe valor de firltro...
			if(tid!=null){
				ticketFiltro = new TicketVO();
				ticketFiltro.setId(new Long(tid));
			}
			
			List<TicketVO> lsTickets = ticketManagerEjb.findTickets(ticketFiltro);
			
			if(lsTickets!=null){
				ticket=lsTickets.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ticket;
	}

	@Override
	public TicketVO saveUpdateTicket(TicketVO ticket) {
		
		TicketVO ticketRespuesta = null;
		
		try{
			
			//1. validar parametros base para la creacion del ticket
				System.out.println("Host: '"+HOSTNAME+"'");
			
			//2. iniciar proceso bpm de 'ciclo de vida' del ticket
//				RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT, USER, PASSWD);
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("estadoId", 1); // estado inicial "Espera soporte"
//				params.put("ownerId", ticket.getOwner().getId()); // usuario que crea el ticket
//				params.put("supportId", 1);// usuario por defecto (usuario default)
//				logger.info("Iniciando proceso de nuevo ticket...");
//				logger.info("Parametros: "+params.toString());
//				ProcessInstance startProcessWithParameters = rc.startProcessWithParameters(rc, DEPLOYMENT_ID, PROCESS_DEF_ID, params );
//				logger.info("Proceso iniciado con ID: "+startProcessWithParameters.getId());
//				Long pid = startProcessWithParameters.getId();
//				ticket.setProcessID(pid);
			
			//3. crear registro en bd...
				logger.info("Guardando datos en BD de negocio...");
				ticketRespuesta = ticketManagerEjb.creaTicket(ticket);
				logger.info("Ticket '"+ticket.getId()+"' creado exitosamente!!");
				
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return ticketRespuesta;
	}

	@Override
	public TicketVO deleteTicket(String tid) {
		// TODO Auto-generated method stub
		return null;
	}

}
