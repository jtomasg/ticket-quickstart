package cl.redhat.poc.ticket.web.auth;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class AuthorizationListener implements PhaseListener {

	public void afterPhase(PhaseEvent event) {

		System.out.println("Listener JSF ");
		
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();

		boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);

		if (session == null) {
			NavigationHandler nh = facesContext.getApplication()
					.getNavigationHandler();
			nh.handleNavigation(facesContext, null, "login");
		}

		else {
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String userEmail = context.getUserPrincipal().getName();

			if (!isLoginPage && (userEmail == null || userEmail.equals("")) ) {
				NavigationHandler nh = facesContext.getApplication()
						.getNavigationHandler();
				nh.handleNavigation(facesContext, null, "login");
			}
		}
	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
