package cl.redhat.poc.ticket.business.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cl.redhat.poc.ticket.business.persistence.model.Ticket;
import cl.redhat.poc.ticket.business.vo.TicketVO;

@Stateless
public class TicketDAOBean implements TicketDAO {

	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;

	public List<TicketVO> getTicketsByParameters(TicketVO filtro) {
		
		List<TicketVO> lsTicketVOs = null;
		
		try{
			
			String strQuery = "SELECT t FROM Ticket t WHERE 1=1 "
					+ (filtro.getAsunto()!=null?" AND t.asunto LIKE '%"+filtro.getAsunto()+"%'":"")
					+ (filtro.getDescripcion()!=null?" AND t.descripcion LIKE '%"+filtro.getDescripcion()+"%'":"")
					+ (filtro.getFechaModificacion()!=null?" AND t.fechaModificacion >= :fecha1 ":"")
					+ (filtro.getFechaCreacion()!=null?" AND t.fechaCreacion <= :fecha2 ":"")
					+ (filtro.getOwnerID()!=null?" AND t.owner.id = "+filtro.getOwnerID():"");
			
			Query query = entityManager.createQuery(strQuery);
			
			if(filtro.getFechaModificacion()!=null)
				query.setParameter("fecha1", filtro.getFechaModificacion());
			if(filtro.getFechaCreacion()!=null)
				query.setParameter("fecha2", filtro.getFechaCreacion());
			
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
		}
		
		return ticketVO;
	}
	
}
