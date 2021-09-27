package utn.gallino.msstock.Dominio;


import javax.persistence.*;

@Entity
@Table(name = "STK_DETALLE_PROVISION", schema = "MS-STK")
public class DetalleProvision {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="ID_MATERIAL")
	private Material material;
	@Column
	private Integer cantidad;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PROVISION")
	private Provision provision;

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
	public Provision getProvision() {
		return provision;
	}
	public void setProvision(Provision provision) {
		this.provision = provision;
	}
}
