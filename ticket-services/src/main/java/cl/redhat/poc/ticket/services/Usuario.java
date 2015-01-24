package cl.redhat.poc.ticket.services;

import cl.redhat.poc.ticket.model.vo.UsuarioVO;

public interface Usuario {

	public UsuarioVO findUsuarioByNombre(String nombre);
	
}
