package utn.gallino.msstock.Rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.gallino.msstock.Dominio.Material;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Service.MaterialService;
import utn.gallino.msstock.Service.StockService;

import java.util.List;


@RestController
@RequestMapping("/api/stock")
@Api(value = "Stock-Rest", description = "Permite gestionar el stock de los materiales de la empresa, entrada por provision y salida por pedido")
public class StockRest {

    @Autowired
    StockService stockService;


    @CrossOrigin(maxAge = 86400)
    @PostMapping
    @ApiOperation(value = "Crear un Movimiento de stock ")
    public ResponseEntity<String> crear(@RequestBody MovimientosStock nuevo)  {
        try{
           // stockService.crearMovimientoStock(nuevo);                                                                                   // si quiero retorna la entidad al crearla ResponseEntity.ok(nuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}
    }


    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un Movimiento por id")
    public ResponseEntity<MovimientosStock> movStockPorId(@PathVariable Integer id){

            try{
                                                                                                // si quiero retorna la entidad al crearla ResponseEntity.ok(nuevo);
                MovimientosStock ms= stockService.buscarMov(id);
                return ResponseEntity.ok(ms);
            }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}


    }

    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/")
    @ApiOperation(value = "Busca todos los movimientos de stock")
    public ResponseEntity<List<MovimientosStock>> listarMovStock(){
        try{
            return ResponseEntity.ok(stockService.listarMovimientos());
        }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}


    }



    @CrossOrigin(maxAge = 86400)
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un Movimiento de stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")})
    public ResponseEntity<MovimientosStock> actualizar(@RequestBody MovimientosStock movNuevo,  @PathVariable Integer id) {
        try {
            MovimientosStock ms= stockService.actualizarMov(movNuevo, id);
            return ResponseEntity.ok(ms);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }


    @CrossOrigin(maxAge = 86400)
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un Movimiento x id")
    public ResponseEntity<String> borrar(@PathVariable Integer id)  {


        try {
            stockService.bajaMovimiento(id);
            String respuesta = "ok "+id;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(respuesta );

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    //***********************************************************************************************************************

    @CrossOrigin(maxAge = 86400)
    @PostMapping(path = "/pedido/actualizarStockPorPedido/")
    @ApiOperation(value = "Servicio actualizacion de stock al crear un pedido")
    public ResponseEntity<HttpStatus> actualizarStockPedido(@RequestParam(value="listaId_dp") List<Integer> listaId_dp) {
        System.out.println("Lista de id detallePedido : "+listaId_dp.toString());


        try {
           Boolean respuesta=  listaId_dp.stream().allMatch(dp -> stockService.crearMovimientoStockDetPed(dp));
           System.out.println("Respuesta de allMatch: "+respuesta);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HttpStatus.CREATED);

    }


    }
