package cl.redhat.poc.ticket.model.vo;

import java.io.Serializable;

public class SistemaVO implements Serializable{

	private static final long serialVersionUID = -8185383741066682956L;
	
	private Long id;
	private String nombre;
	private String descripcion;
	private String version;
	
	public SistemaVO() {
	}
	
	public SistemaVO(Long id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
}
