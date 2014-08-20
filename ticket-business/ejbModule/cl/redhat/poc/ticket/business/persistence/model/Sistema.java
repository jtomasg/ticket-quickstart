package cl.redhat.poc.ticket.business.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name = "Sistema")
@SequenceGenerator(name="id_sistema",sequenceName="sistema_id_seq")
public class Sistema implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator="id_sistema")
    @Column(name="id")
    private Long id;

    @Column(nullable=false, unique=true)
    private String nombre;
    
    @Column(nullable=true)
    private String descripcion;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="sistema")
    private List<VersionSistema> versionSistemas;

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

	public List<VersionSistema> getVersionSistemas() {
		return versionSistemas;
	}

	public void setVersionSistemas(List<VersionSistema> versionSistemas) {
		this.versionSistemas = versionSistemas;
	}

    
    
}
