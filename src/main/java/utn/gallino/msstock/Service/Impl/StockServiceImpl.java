package utn.gallino.msstock.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import utn.gallino.msstock.Dominio.*;
import utn.gallino.msstock.Repository.DetallePedidoRepository;
import utn.gallino.msstock.Repository.MaterialRepository;
import utn.gallino.msstock.Repository.StockRepository;
import utn.gallino.msstock.Service.ProvisionService;
import utn.gallino.msstock.Service.StockService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Autowired
    ProvisionService provisionService;


    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @JmsListener(destination = "COLA_PEDIDOS")
    public void handle(Message msg) throws JmsException, InterruptedException, JMSException {
        String chain= "";
        System.out.println("Llego msj, hacemos sleep");
        Thread.sleep(8000);
        if(msg instanceof TextMessage){
            TextMessage ms = (TextMessage) msg;
          chain =  ms.getText();
        }
        logger.info("Llego mensaje 1 {}", chain);
        List<String> listaId_Dp = Arrays.asList(chain.split(",",-1));

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

         actualizarStockMaterial(dpAux.getMaterial().getId(),dpAux.getCantidad());
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
       Boolean stockMinimoSuperado =matAux.getStockActual()-cantidad<matAux.getStockMinimo();
        if(stockMinimoSuperado){
            crearMovimientoStockProvision(matAux);
        }
       matAux.setStockActual( matAux.getStockActual()-cantidad);
       materialRepository.save(matAux);



    }
    private void actualizarStockProvision(Integer id_material, Integer cantidad) {
        Material matAux = materialRepository.findById(id_material).get();
        matAux.setStockActual( matAux.getStockActual()+cantidad);
        materialRepository.save(matAux);
    }

    @Override
    public MovimientosStock crearMovimientoStockProvision(Material material) {

        Provision provision = new Provision();
       /* provision.setFechaProvision(Instant.now());
        DetalleProvision detalleProvision = new DetalleProvision();

        detalleProvision.setMaterial(material);
        detalleProvision.setCantidad((int) (material.getStockMinimo()*1.8)); //Se llama a una reposisicon por el dobl del stock minimo
        detalleProvision.setProvision(provision);
        List<DetalleProvision> aux = new ArrayList<>();
        aux.add(detalleProvision);
        provision.setDetalle(aux);*/
        try {
          provision =  provisionService.crearProvision(material);
        }catch (Exception e){
            logger.trace("Se rompio la provision jeeeeeee"+e.getMessage());
        }

        MovimientosStock movimientosStock = new MovimientosStock();
        movimientosStock.setFecha(Instant.now().plusSeconds(120));//  .plus(5, ChronoUnit.DAYS));
        movimientosStock.setCantidadEntrada(provision.getDetalle().get(0).getCantidad());
        movimientosStock.setDetalleProvision(provision.getDetalle().get(0));
        movimientosStock.setMaterial(material);

        try {
            guardarMovimientosStock(movimientosStock);
            actualizarStockProvision(material.getId(),movimientosStock.getCantidadEntrada());
        }catch (Exception e){
            System.out.println("no se puedo guardar el mov stock de"+ movimientosStock.getDetalleProvision().toString());
            e.printStackTrace();

        }

        return movimientosStock;

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
