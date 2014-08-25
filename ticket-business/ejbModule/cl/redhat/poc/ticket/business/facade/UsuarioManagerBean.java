package cl.redhat.poc.ticket.business.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cl.redhat.poc.ticket.business.persistence.dao.UsuarioDAO;
import cl.redhat.poc.ticket.business.vo.UsuarioVO;

@Stateless
public class UsuarioManagerBean implements UsuarioManager{
	
	@EJB
	UsuarioDAO usuarioDAO;
	
	public UsuarioVO getUsuarioPorEmail(String email){
		
		UsuarioVO usuarioVO = null;
		
		try{
			usuarioVO = usuarioDAO.getUsuarioByEmail(email);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return usuarioVO;
	}
	
}
