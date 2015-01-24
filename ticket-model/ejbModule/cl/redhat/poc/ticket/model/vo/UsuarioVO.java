package cl.redhat.poc.ticket.model.vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = -8132079593738293961L;
	
	private Long id;
	private String nombres;
	private String userName;
	private String email;
	private String rol;
	
	public UsuarioVO() {
	}
	
	public UsuarioVO(Long id, String nombre, String email, String rol, String userName) {
		super();
		this.id = id;
		this.nombres = nombre;
		this.email = email;
		this.rol = rol;
		this.userName = userName;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombres;
	}
	public void setNombre(String nombre) {
		this.nombres = nombre;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
