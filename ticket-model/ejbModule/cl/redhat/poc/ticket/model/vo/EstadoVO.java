package cl.redhat.poc.ticket.model.vo;

import java.io.Serializable;

public class EstadoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4127322263687693966L;
	
	private Long id;
	private String nombre;
	
	public EstadoVO(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public EstadoVO() {
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
	
}
