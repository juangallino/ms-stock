package utn.gallino.msstock.Dominio;

import org.springframework.beans.factory.annotation.Autowired;
import utn.gallino.msstock.Repository.DetallePedidoRepository;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.time.Instant;

@Entity
@Table(name = "STK_MOVIMIENTO_STOCK", schema = "MS-STK")
public class MovimientosStock {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "ID_DETALLE_PEDIDO")
	private DetallePedido detallePedido;
	@OneToOne
	@JoinColumn(name = "ID_DETALLE_PROVISION")
	private DetalleProvision detalleProvision;
	@ManyToOne
	@JoinColumn(name = "ID_MATERIAL")
	private Material material;
	@Column
	private Integer cantidadEntrada;
	@Column
	private Integer cantidadSalida;
	@Column(columnDefinition = "TIMESTAMP")
	private Instant fecha;





	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public DetallePedido getDetallePedido() {
		return detallePedido;
	}
	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}
	public DetalleProvision getDetalleProvision() {
		return detalleProvision;
	}
	public void setDetalleProvision(DetalleProvision detalleProvision) {
		this.detalleProvision = detalleProvision;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Integer getCantidadEntrada() {
		return cantidadEntrada;
	}
	public void setCantidadEntrada(Integer cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}
	public Integer getCantidadSalida() {
		return cantidadSalida;
	}
	public void setCantidadSalida(Integer cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}
	public Instant getFecha() {
		return fecha;
	}
	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}
	
	
}
