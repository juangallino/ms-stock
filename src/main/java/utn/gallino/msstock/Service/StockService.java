package utn.gallino.msstock.Service;

import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Dominio.MovimientosStock;

import java.util.List;

public interface StockService {


    public MovimientosStock crearMovimientoStockProvision(Material Material);

    public MovimientosStock buscarMov(Integer id);

    public List<MovimientosStock> listarMovimientos();

    MovimientosStock actualizarMov(MovimientosStock movNuevo, Integer id);

    void bajaMovimiento(Integer id);
    public Boolean crearMovimientoStockDetPed(Integer id_dp) ;
}
