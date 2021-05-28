package utn.gallino.msstock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.gallino.msstock.Dominio.Material;

import java.util.List;

@Repository

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    public List<Material> findByPrecioBetween(Double minimo, Double maximo);

    public List<Material> findByPrecio(Double precio);

    public List<Material> findByStockActualBetween(Integer minimo, Integer maximo);
     public Material findByNombreIsLike(String nombre);

}
