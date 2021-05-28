package utn.gallino.msstock.Dominio;

import javax.persistence.*;

@Entity
@Table(name = "STK_UNIDAD", schema = "MS-STK")
public class Unidad {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String descripcion;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
