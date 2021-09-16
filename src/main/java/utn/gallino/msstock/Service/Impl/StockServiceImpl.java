package utn.gallino.msstock.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.DetallePedido;
import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Repository.DetallePedidoRepository;
import utn.gallino.msstock.Repository.MaterialRepository;
import utn.gallino.msstock.Repository.StockRepository;
import utn.gallino.msstock.Service.StockService;

import javax.jms.Message;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {



    @Autowired
    StockRepository stockRepository;
    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    JmsTemplate jms;

    @Autowired
    MaterialRepository materialRepository;



    @JmsListener(destination = "COLA_PEDIDOS")
    public void handle(Message msg) throws JmsException {

         
        System.out.println("msg: "+msg);
        System.out.println("msg to string: "+msg.toString());
        List<String> listaId_Dp = Arrays.asList(msg.toString().split(",",-1));

        try {
            Boolean respuesta=  listaId_Dp.stream().allMatch(dp -> crearMovimientoStockDetPed(Integer.parseInt(dp)));
            System.out.println("Respuesta de allMatch: "+respuesta);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public Boolean crearMovimientoStockDetPed(Integer id_dp)  {

        //El movimiento de stock puede ser tanto por un PEDIDO,detalle_pedido(flujo salida)
        // o por una Provision,Detalle_provision(flujo entrante)
        System.out.println("crear movimiento stock con id_dp: "+ id_dp );
         DetallePedido dpAux =  detallePedidoRepository.findById(id_dp).get();
         MovimientosStock movStock = new MovimientosStock();
         movStock.setDetallePedido(dpAux);
         movStock.setCantidadSalida(dpAux.getCantidad());
         movStock.setMaterial(dpAux.getMaterial());
         movStock.setFecha(Instant.now());
                                             //llamo al metodo con -(MENOS) cantidad pra que reste
         actualizarStockMaterial(dpAux.getMaterial().getId(),-dpAux.getCantidad());
        try {
            guardarMovimientosStock(movStock);
        }catch (Exception e){
            System.out.println("no se puedo guardar el mov stock de"+ movStock.getDetallePedido().toString());
            e.printStackTrace();
                return false;
        }
        return true;
    }

    private void actualizarStockMaterial(Integer id_material, Integer cantidad) {
       Material matAux = materialRepository.findById(id_material).get();
       matAux.setStockActual( matAux.getStockActual()+cantidad);
       materialRepository.save(matAux);



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
