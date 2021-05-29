package utn.gallino.msstock.Service.Impl;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Repository.MaterialRepository;
import utn.gallino.msstock.Repository.UnidadRepository;
import utn.gallino.msstock.Service.MaterialService;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {


    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    UnidadRepository unidadRepository;
    @Override
    public List<Material> buscarMaterialPorPrecio(Double precio) {
      return materialRepository.findByPrecio(precio);

    }

    @Override
    public void actualizarMaterial(Material mat, Integer id) {
         mat.setId(id);
        materialRepository.save(mat);

    }

    @Override
    public void bajaMaterial(Integer id) {
        materialRepository.deleteById(id);

    }


    public List<Material> buscarMaterialPorStockEntre(Integer min, Integer max) {
        return materialRepository.findByStockActualBetween(min, max);

    }

    @Override

    public void crearMaterial(Material material) {


        //obra.setTipo(tipoObraRepository.getOne(obra.getTipo().getId()));

        material.setUnidad(unidadRepository.getOne(material.getUnidad().getId()));
        materialRepository.save(material);
    }

    @Override
    public Material buscarMaterial(Integer id) {
        return materialRepository.findById(id).get();
    }

    @Override
    public List<Material> listarMateriales() {
        return materialRepository.findAll();
    }
    @Override
    public Material buscarMaterialPorNombre(String name) {
        return materialRepository.findByNombreIsLike(name);
    }
}
