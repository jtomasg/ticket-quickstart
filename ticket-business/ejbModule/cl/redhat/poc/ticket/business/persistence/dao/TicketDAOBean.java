package cl.redhat.poc.ticket.business.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.redhat.poc.ticket.business.persistence.model.Estado;
import cl.redhat.poc.ticket.business.persistence.model.Prioridad;
import cl.redhat.poc.ticket.business.persistence.model.Sistema;
import cl.redhat.poc.ticket.business.persistence.model.Ticket;
import cl.redhat.poc.ticket.business.persistence.model.Usuario;
import cl.redhat.poc.ticket.model.vo.EstadoVO;
import cl.redhat.poc.ticket.model.vo.SistemaVO;
import cl.redhat.poc.ticket.model.vo.TicketVO;
import cl.redhat.poc.ticket.model.vo.UsuarioVO;

@Stateless
public class TicketDAOBean implements TicketDAO {

	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;

	public List<TicketVO> getTicketsByParameters(TicketVO filtro) {
		
		List<TicketVO> lsTicketVOs = null;
		
		try{
			
			String strQuery = "SELECT t FROM Ticket t WHERE 1=1 ";
			
			if(filtro!=null){
				strQuery = strQuery + (filtro.getAsunto()!=null?" AND t.asunto LIKE '%"+filtro.getAsunto()+"%'":"")
					+ (filtro.getId()!=null?" AND t.caseId = "+filtro.getId():"")
					+ (filtro.getDescripcion()!=null?" AND t.descripcion LIKE '%"+filtro.getDescripcion()+"%'":"")
					+ (filtro.getFechaModificacion()!=null?" AND t.fechaModificacion >= :fecha1 ":"")
					+ (filtro.getFechaCreacion()!=null?" AND t.fechaCreacion <= :fecha2 ":"")
					+ (filtro.getOwner()!=null && filtro.getOwner().getId()!=null?" AND t.owner.id = "+filtro.getOwner().getId():"");
			}
			
			Query query = entityManager.createQuery(strQuery);
			
			if(filtro!=null){
				if(filtro.getFechaModificacion()!=null)
					query.setParameter("fecha1", filtro.getFechaModificacion());
				if(filtro.getFechaCreacion()!=null)
					query.setParameter("fecha2", filtro.getFechaCreacion());
			}
			
			List<Ticket> lsTickets = (List<Ticket>) query.getResultList();
			
			if(lsTickets!=null){
				lsTicketVOs = new ArrayList<TicketVO>();
				for(Ticket ticket : lsTickets){
					lsTicketVOs.add(this.ticketEntityToVO(ticket));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lsTicketVOs;
	}
	
	private TicketVO ticketEntityToVO(Ticket ticket){
		TicketVO ticketVO = null;
		
		if(ticket!=null){
			ticketVO = new TicketVO();
			ticketVO.setAsunto(ticket.getAsunto());
			ticketVO.setDescripcion(ticket.getAsunto());
			ticketVO.setFechaCreacion(ticket.getFechaCreacion());
			ticketVO.setFechaModificacion(ticket.getFechaModificacion());
			ticketVO.setId(ticket.getCaseId());
			ticketVO.setOwner(usuarioEntityToVO(ticket.getOwner()));
			ticketVO.setPrioridad(ticket.getPrioridad().getId());
			ticketVO.setEstado(estadoEntityToVO(ticket.getEstado()));
			ticketVO.setSistema(sistemaEntityToVO(ticket.getSistema()));
		}
		
		return ticketVO;
	}

	
	public TicketVO crearTicket(TicketVO ticketVO) {
		
		try{
			
			Ticket ticket = ticketVOToEntity(ticketVO);
			
			entityManager.persist(ticket);
			entityManager.flush();
			
			ticketVO = this.ticketEntityToVO(ticket);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ticketVO;
	}
	
	private Ticket ticketVOToEntity(TicketVO ticketVO){
		Ticket ticket = null;
		
		if(ticketVO!=null){
			
			ticket = new Ticket();
			ticket.setAsunto(ticketVO.getAsunto());
			ticket.setDescripcion(ticketVO.getDescripcion());
			ticket.setFechaCreacion(new Date());
			
			Estado estado = entityManager.getReference(Estado.class, ticketVO.getEstado());
			Prioridad prioridad = entityManager.find(Prioridad.class, ticketVO.getPrioridad());
			Usuario owner = entityManager.find(Usuario.class, ticketVO.getOwner().getId());
			Sistema sistema = entityManager.find(Sistema.class,  ticketVO.getSistema().getId());
			
			ticket.setEstado(estado);
			ticket.setPrioridad(prioridad);
			ticket.setOwner(owner);
			ticket.setSistema(sistema);
			
		}else
			System.out.println("TicketVO nulo!");
		
		return ticket;
	}
	
	private UsuarioVO usuarioEntityToVO(Usuario user){
		UsuarioVO userVo = null;
		
		if(user!=null){
			userVo = new UsuarioVO();
			userVo.setId(user.getId());
			userVo.setEmail(user.getEmail());
			userVo.setNombre(user.getNombres());
			userVo.setRol(user.getRole());
		}
		
		return userVo;
	}
	
	private EstadoVO estadoEntityToVO(Estado estado){
		EstadoVO estadoVo = null;
		
		if(estado!=null){
			estadoVo = new EstadoVO();
			estadoVo.setId(estado.getId());
			estadoVo.setNombre(estado.getNombre());
		}
		
		return estadoVo;
	}
	
	private SistemaVO sistemaEntityToVO(Sistema sistema){
		SistemaVO sistemaVO = null;
		
		if(sistema!=null){
			sistemaVO = new SistemaVO();
			sistemaVO.setId(sistema.getId());
			sistemaVO.setNombre(sistema.getNombre());
			sistemaVO.setDescripcion(sistema.getDescripcion());
			sistemaVO.setVersion(null);
		}
		
		return sistemaVO;
	}
	
}
