package utn.gallino.msstock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.gallino.msstock.Dominio.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Integer> {
}
