package cl.redhat.poc.ticket.web.vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = -8132079593738293961L;
	
	private Long id;
	private String nombre;
	private String email;
	private String rol;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	

}
