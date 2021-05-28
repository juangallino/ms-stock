package utn.gallino.msstock.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Dominio.Provision;
import utn.gallino.msstock.Repository.ProvisionRepository;
import utn.gallino.msstock.Service.ProvisionService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProvisionServiceImpl implements ProvisionService {

    @Autowired
    ProvisionRepository provisionRepository;


    @Override
    public void crearProvision(Provision p) throws Exception {

        try {
            guardarProvision(p);
        }catch (Exception e ){throw new Exception("not found");}


    }

    public void guardarProvision(Provision provision) throws Exception {


        try {
            provisionRepository.save(provision);
        }catch (Exception e ){throw new Exception("not found");}




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
