package cl.redhat.poc.ticket.business.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cl.redhat.poc.ticket.business.persistence.dao.UsuarioDAO;
import cl.redhat.poc.ticket.model.vo.UsuarioVO;

@Stateless
public class UsuarioManagerBean implements UsuarioManager{
	
	@EJB
	UsuarioDAO usuarioDAO;
	
	public UsuarioVO getUsuarioPorUserName(String uname){
		
		UsuarioVO usuarioVO = null;
		
		try{
			usuarioVO = usuarioDAO.getUsuarioByUserName(uname);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return usuarioVO;
	}
	
}
