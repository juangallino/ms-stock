package dan.tp2021.productos.domain;

import java.time.Instant;
import java.util.List;

public class Provision {

	private Integer id;
	private Instant fechaProvision;
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
