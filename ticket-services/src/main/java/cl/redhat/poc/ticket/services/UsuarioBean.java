package cl.redhat.poc.ticket.services;

import org.switchyard.component.bean.Service;

import cl.redhat.poc.ticket.business.facade.UsuarioManager;
import cl.redhat.poc.ticket.model.vo.UsuarioVO;
import cl.redhat.poc.ticket.service.locator.ServiceLocator;

@Service(Usuario.class)
public class UsuarioBean implements Usuario {

	private UsuarioManager usuarioManagerEjb;
	
	@Override
	public UsuarioVO findUsuarioByNombre(String uname) {
		UsuarioVO usuarioVO = null;
		try{
			
			usuarioManagerEjb = (UsuarioManager) ServiceLocator.getInstance()
					.doRemoteLookUp("", "ticket-business", "", "UsuarioManagerBean",
							UsuarioManager.class.getName());
			
			usuarioVO = usuarioManagerEjb.getUsuarioPorUserName(uname);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuarioVO;
	}

}
