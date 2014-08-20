package cl.redhat.poc.ticket.business.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@Table(name = "Ticket")
@SequenceGenerator(name="id_ticket",sequenceName="ticket_id_seq")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator="id_ticket")
    private Long caseId;

    @Column(nullable=false)
    private String asunto;

    @Column(nullable=false)
    private String descripcion;

    @Column(nullable=false, name="fecha_creacion")
    private Date fechaCreacion;
    
    @Column(nullable=true,name="fecha_modificacion")
    private Date fechaModificacion;
    
    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName="id",insertable = false, updatable = false)
    private Usuario owner;
    
    @ManyToOne
    @JoinColumn(name = "support_owner_id",referencedColumnName="id",insertable = false, updatable = false)
    private Usuario suportOwner;
    
    @ManyToOne
    @JoinColumn(name = "estado_id",referencedColumnName="id",insertable = false, updatable = false)
    private Estado estado;
    
    @ManyToOne
    @JoinColumn(name = "prioridad_id",referencedColumnName="id", insertable = false, updatable = false)
    private Prioridad prioridad;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="ticket")
    private List<Comentario> comentarios;

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
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

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public Usuario getSuportOwner() {
		return suportOwner;
	}

	public void setSuportOwner(Usuario suportOwner) {
		this.suportOwner = suportOwner;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
    

}
