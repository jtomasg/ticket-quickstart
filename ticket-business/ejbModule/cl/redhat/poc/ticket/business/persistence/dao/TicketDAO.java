package cl.redhat.poc.ticket.business.persistence.dao;

import java.util.List;

import javax.ejb.Local;

import cl.redhat.poc.ticket.business.vo.TicketVO;

@Local
public interface TicketDAO {
	
	/**
	 * Metodo que permite obtener un listado de ticket filtrado por multiples parametros
	 * @param filtro
	 * @return List<TicketVO>
	 */
	public List<TicketVO> getTicketsByParameters(TicketVO filtro);
	
}
