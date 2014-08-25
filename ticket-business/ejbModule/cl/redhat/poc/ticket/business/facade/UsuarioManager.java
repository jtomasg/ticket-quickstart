package cl.redhat.poc.ticket.business.facade;

import javax.ejb.Remote;

import cl.redhat.poc.ticket.business.vo.UsuarioVO;

@Remote
public interface UsuarioManager {

	public UsuarioVO getUsuarioPorEmail(String email);
	
}
