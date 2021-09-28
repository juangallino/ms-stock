package utn.gallino.msstock.Dominio;

import javax.persistence.*;
import java.util.jar.Attributes;

@Entity
@Table(name = "PED_DETALLE_PEDIDO", schema = "MS-PED")
public class DetallePedido {
	@Id
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_MATERIAL")
	private Material material;
	@Column
	private Integer cantidad;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	
	
}
