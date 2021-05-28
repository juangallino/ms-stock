package utn.gallino.msstock.Service;

import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Dominio.Provision;

import java.util.List;

public interface ProvisionService {
    void crearProvision(Provision p) throws Exception;

    Provision buscarProvisionPorId(Integer id) throws Exception;

    List<Provision> listarProvisiones();

    Provision actualizarProvision(Provision provNueva, Integer id) throws Exception;

    void bajaProvision(Integer id) throws Exception;
}
