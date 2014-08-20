package cl.redhat.poc.ticket.business.vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = -8132079593738293961L;
	
	private Long id;
	private String nombre;
	
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

}
