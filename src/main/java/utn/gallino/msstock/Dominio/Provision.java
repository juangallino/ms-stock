package utn.gallino.msstock.Dominio;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="STK_PROVISION", schema = "MS-STK")
public class Provision {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TIME")
	private Instant fechaProvision;

	@OneToMany(mappedBy = "provision")
	private List<DetalleProvision> detalle;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Instant getFechaProvision() {
		return fechaProvision;
	}
	public void setFechaProvision(Instant fechaProvision) {
		this.fechaProvision = fechaProvision;
	}
	public List<DetalleProvision> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetalleProvision> detalle) {
		this.detalle = detalle;
	}

	
}
