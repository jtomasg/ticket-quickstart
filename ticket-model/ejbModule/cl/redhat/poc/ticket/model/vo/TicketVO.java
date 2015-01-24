package cl.redhat.poc.ticket.model.vo;

import java.io.Serializable;
import java.util.Date;

public class TicketVO implements Serializable{

	private static final long serialVersionUID = -4598568898513696901L;
	
	private Long id;
	private String asunto;
	private String descripcion;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private UsuarioVO owner;
    private UsuarioVO supportOwner;
    private SistemaVO sistema;
    private Long prioridad;
    private EstadoVO estado;
    private Long processID;
    
    public TicketVO() {
		
	}
    
	public TicketVO(UsuarioVO owner, UsuarioVO supportOwner, Long processID) {
		super();
		this.owner = owner;
		this.supportOwner = supportOwner;
		this.processID = processID;
	}
	
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
	public Long getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}
	public UsuarioVO getOwner() {
		return owner;
	}
	public void setOwner(UsuarioVO owner) {
		this.owner = owner;
	}
	public UsuarioVO getSupportOwner() {
		return supportOwner;
	}
	public void setSupportOwner(UsuarioVO supportOwner) {
		this.supportOwner = supportOwner;
	}
	public SistemaVO getSistema() {
		return sistema;
	}
	public void setSistema(SistemaVO sistema) {
		this.sistema = sistema;
	}
	public EstadoVO getEstado() {
		return estado;
	}
	public void setEstado(EstadoVO estado) {
		this.estado = estado;
	}
	public Long getProcessID() {
		return processID;
	}
	public void setProcessID(Long processID) {
		this.processID = processID;
	}
    
}
