package cl.redhat.poc.ticket.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;

import cl.redhat.poc.ticket.model.vo.UsuarioVO;
import cl.redhat.poc.ticket.web.util.RestClientCallUtil;

@SuppressWarnings("serial")
@SessionScoped
@ManagedBean(name = "usuarioBean")
public class UsuarioBean extends BaseSystemBean implements Serializable {

	Logger logger = Logger.getLogger(UsuarioBean.class);
	
	private UsuarioVO usuario;

	@PostConstruct
	public void postContruct(){
		super.loadDatosBase();
	}
	
	public UsuarioVO getUsuario() {
		if(usuario==null)
			getUsuarioRest();
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public UsuarioVO getUsuarioRest() {
		try {

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			String uname = context.getUserPrincipal().getName();

			String endpointURL = "http://"+REST_HOSTNAME+":"+REST_PORT+"/rest/usuarios/nombre/"+ uname;

			String strJson = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL);

			
			if(!strJson.equals("ERROR")){
				ObjectMapper mapper = new ObjectMapper();
				usuario = mapper.readValue(strJson, UsuarioVO.class);
				logger.info("Datos de usuario cargados");
			}else
				logger.info("Error al cargar datos de usuario!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public boolean isUserAdmin() {
		return getRequest().isUserInRole("SYSTEM");
	}

	public String logOut() {
		try {
			// getRequest().getSession().invalidate();

			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.invalidate();

			logger.info("Logout!");

			// ExternalContext ec = FacesContext.getCurrentInstance()
			// .getExternalContext();
			// ec.invalidateSession();
			// ec.redirect("login?faces-redirect=true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login.xhtml/?faces-redirect=true";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

}
