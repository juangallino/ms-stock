package utn.gallino.msstock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.gallino.msstock.Dominio.Unidad;

public interface UnidadRepository extends JpaRepository<Unidad,Integer> {
}
