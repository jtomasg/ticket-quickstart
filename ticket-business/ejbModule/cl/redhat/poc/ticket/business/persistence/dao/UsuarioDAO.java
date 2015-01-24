package cl.redhat.poc.ticket.business.persistence.dao;

import javax.ejb.Local;

import cl.redhat.poc.ticket.model.vo.UsuarioVO;

@Local
public interface UsuarioDAO {

	public UsuarioVO getUsuarioByUserName(String uname);
	
}
