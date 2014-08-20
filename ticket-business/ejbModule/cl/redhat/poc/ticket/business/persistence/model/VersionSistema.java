package cl.redhat.poc.ticket.business.persistence.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name = "Version_Sistema")
@SequenceGenerator(name="id_ver_sistema",sequenceName="ver_sistema_id_seq")
public class VersionSistema implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator="id_ver_sistema")
    @Column(name="id")
    private Long id;

    @Column(nullable=false)
    private String version;
    
    @Basic
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "sistema_id",referencedColumnName="id", insertable = false, updatable = false)
    private Sistema sistema;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
    
}
