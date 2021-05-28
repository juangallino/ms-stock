package utn.gallino.msstock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.gallino.msstock.Dominio.Provision;

public interface ProvisionRepository extends JpaRepository<Provision,Integer> {
}
