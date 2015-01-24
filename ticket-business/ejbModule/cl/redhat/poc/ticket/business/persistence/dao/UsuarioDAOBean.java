package cl.redhat.poc.ticket.business.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import cl.redhat.poc.ticket.business.persistence.model.Usuario;
import cl.redhat.poc.ticket.model.vo.UsuarioVO;

@Stateless
public class UsuarioDAOBean implements UsuarioDAO{

	Logger logger = Logger.getLogger(UsuarioDAOBean.class);
	
	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;

	public UsuarioVO getUsuarioByUserName(String uname) {
		
		UsuarioVO usuarioVO = null;
		
		try{
			
			String strQuery = "SELECT u FROM Usuario u WHERE u.username = ?";
			
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, uname);
			
			Usuario usuario = (Usuario) query.getSingleResult();
			
			if(usuario!=null){
				usuarioVO = usuarioEntityToVO(usuario);
			}
			
		}catch(NoResultException e){
			logger.warn("No existe el usuario '"+uname+"' en el sistema!");;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		logger.trace("Usuario: "+usuarioVO.getNombre());
		
		return usuarioVO;
	}
	
	private UsuarioVO usuarioEntityToVO(Usuario u){
		UsuarioVO usuarioVO = null;
		if(u!=null){
			usuarioVO = new UsuarioVO();
			usuarioVO.setId(u.getId());
			usuarioVO.setNombre(u.getNombres());
			usuarioVO.setEmail(u.getEmail());
			usuarioVO.setUserName(u.getUsername());
			usuarioVO.setRol(u.getRole());
		}
		return usuarioVO;
	}
	
}
