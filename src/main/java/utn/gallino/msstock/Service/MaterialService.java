package utn.gallino.msstock.Service;

import utn.gallino.msstock.Dominio.Material;

import java.util.List;

public interface MaterialService {

    public void crearMaterial(Material material);

    public Material buscarMaterial(Integer id);




    public List<Material> listarMateriales();



    public List<Material> buscarMaterialPorStockEntre(Integer min, Integer max);

    public Material buscarMaterialPorNombre(String name);

    public List<Material> buscarMaterialPorPrecio(Double precio);

    public void actualizarMaterial(Material mat, Integer id);

    void bajaMaterial(Integer id);
}
