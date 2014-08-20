package cl.redhat.poc.ticket.business.vo;

import java.io.Serializable;
import java.util.Date;

public class TicketVO implements Serializable{

	
	private static final long serialVersionUID = -4598568898513696901L;
	private Long id;
	private String asunto;
	private String descripcion;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private Long ownerID;
    private Long suportOwnerID;
    private SistemaVO sistema;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public SistemaVO getSistema() {
		return sistema;
	}
	public void setSistema(SistemaVO sistema) {
		this.sistema = sistema;
	}
	public Long getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(Long ownerID) {
		this.ownerID = ownerID;
	}
	public Long getSuportOwnerID() {
		return suportOwnerID;
	}
	public void setSuportOwnerID(Long suportOwnerID) {
		this.suportOwnerID = suportOwnerID;
	}
    
    
    
}
