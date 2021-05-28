package utn.gallino.msstock.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.DetallePedido;
import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Repository.DetallePedidoRepository;
import utn.gallino.msstock.Repository.StockRepository;
import utn.gallino.msstock.Service.StockService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {



    @Autowired
    StockRepository stockRepository;
    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Override
    public Boolean crearMovimientoStockDetPed(Integer id_dp)  {

        //El movimiento de stock puede ser tanto por un PEDIDO,detalle_pedido(flujo salida)
        // o por una Provision,Detalle_provision(flujo entrante)

        DetallePedido dpAux =  detallePedidoRepository.findById(id_dp).get();
         MovimientosStock movStock = new MovimientosStock();
         movStock.setDetallePedido(dpAux);
         movStock.setCantidadSalida(dpAux.getCantidad());
         movStock.setMaterial(dpAux.getMaterial());
         movStock.setFecha(Instant.now());

        try {
            guardarMovimientosStock(movStock);
        }catch (Exception e){
            System.out.println("no se puedo guardar el mov stock de"+ movStock.getDetallePedido().toString());
        return false;}
        return true;
    }

    @Override
    public MovimientosStock crearMovimientoStock(MovimientosStock nuevo) {
        return null;
    }

    @Override
    public MovimientosStock buscarMov(Integer id) {
         return stockRepository.findById(id).get();
    }

    @Override
    public List<MovimientosStock> listarMovimientos() {

        List<MovimientosStock> result = new ArrayList<>();
        stockRepository.findAll().forEach(mov -> result.add(mov));
        return result;

    }

    public MovimientosStock guardarMovimientosStock(MovimientosStock ms){
        stockRepository.save(ms);
        return ms;
    }

    @Override
    public MovimientosStock actualizarMov(MovimientosStock movNuevo, Integer id) {
        movNuevo.setId(id);
        stockRepository.save(movNuevo);
        return movNuevo;
    }

    @Override
    public void bajaMovimiento(Integer id) {

        stockRepository.deleteById(id);

    }
}
