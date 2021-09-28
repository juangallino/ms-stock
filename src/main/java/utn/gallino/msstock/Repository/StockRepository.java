package utn.gallino.msstock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.gallino.msstock.Dominio.MovimientosStock;

public interface StockRepository extends JpaRepository<MovimientosStock,Integer> {
}
