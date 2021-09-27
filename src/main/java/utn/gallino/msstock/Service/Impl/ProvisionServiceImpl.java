package utn.gallino.msstock.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.DetalleProvision;
import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Dominio.Provision;
import utn.gallino.msstock.Repository.ProvisionRepository;
import utn.gallino.msstock.Service.ProvisionService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProvisionServiceImpl implements ProvisionService {

    @Autowired
    ProvisionRepository provisionRepository;


    @Override
    public Provision crearProvision(Material material) throws Exception {

        Provision provision = new Provision();

        try {


            provision.setFechaProvision(Instant.now());
            DetalleProvision detalleProvision = new DetalleProvision();

            detalleProvision.setMaterial(material);
            detalleProvision.setCantidad((int) (material.getStockMinimo()*1.8)); //Se llama a una reposisicon por el dobl del stock minimo
            detalleProvision.setProvision(provision);
            List<DetalleProvision> aux = new ArrayList<>();
            aux.add(detalleProvision);
            provision.setDetalle(aux);




        }catch (Exception e ){throw new Exception("not found");}

        return  provision;

    }

    public void guardarProvision(Provision provision) throws Exception {


        try {
            provisionRepository.save(provision);
        }catch (Exception e ){throw new Exception("not found");}




    }


    @Override
    public void crearProvision(Provision p) throws Exception {

    }

    @Override
    public Provision buscarProvisionPorId(Integer id) throws Exception {
        try {
            return provisionRepository.findById(id).get();
        }catch (Exception e ){throw new Exception("not found");}


    }

    @Override
    public List<Provision> listarProvisiones() {
        List<Provision> result = new ArrayList<>();
        provisionRepository.findAll().forEach(prov -> result.add(prov));
        return result;
    }

    @Override
    public Provision actualizarProvision(Provision provNueva, Integer id) throws Exception {
     provNueva.setId(id);
     guardarProvision(provNueva);
     return provNueva;

    }

    @Override
    public void bajaProvision(Integer id) throws Exception {

        try {
            provisionRepository.deleteById(id);
        }catch (Exception e ){throw new Exception("not found");}

    }
}
