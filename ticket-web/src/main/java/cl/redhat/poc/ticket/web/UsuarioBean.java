package cl.redhat.poc.ticket.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import cl.redhat.poc.ticket.web.util.RestClientCallUtil;
import cl.redhat.poc.ticket.web.vo.UsuarioVO;

@SuppressWarnings("serial")
@SessionScoped
@ManagedBean(name = "usuarioBean")
public class UsuarioBean implements Serializable {

	private UsuarioVO usuario;

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
			String userEmail = context.getUserPrincipal().getName();

			String endpointURL = "http://localhost:8180/ticket-esb/http/getUsuario/email/"
					+ userEmail;

			String strJson = new RestClientCallUtil()
					.callJsonRemoteRest(endpointURL);

			ObjectMapper mapper = new ObjectMapper();
			usuario = mapper.readValue(strJson, UsuarioVO.class);

			System.out.println("getUsuario");
			
		} catch (Exception e) {

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

			System.out.println("Logout!");

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
