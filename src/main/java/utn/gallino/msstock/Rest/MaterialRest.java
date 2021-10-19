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
import utn.gallino.msstock.Service.MaterialService;

import java.util.List;


@RestController
@RequestMapping("/api/material")
@Api(value = "Material Rest", description = "Permite gestionar los Materiales y el stock de los mismos de la empresa")
public class MaterialRest {






        @Autowired
        MaterialService materialService;


    @CrossOrigin(maxAge = 86400)
        @PostMapping
        @ApiOperation(value = "Crear un material ")
        public ResponseEntity<String> crear(@RequestBody Material nuevo)  {
            try{
                materialService.crearMaterial(nuevo);                                                                                   // si quiero retorna la entidad al crearla ResponseEntity.ok(nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body("OK");
            }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}
        }


    @CrossOrigin(maxAge = 86400)
        @GetMapping(path = "/{id}")
        @ApiOperation(value = "Busca un material por id")
        public ResponseEntity<Material> materialPorId(@PathVariable Integer id){


            Material mt = materialService.buscarMaterial(id);
            return ResponseEntity.ok(mt);
        }

    @CrossOrigin(maxAge = 86400)
        @GetMapping(path = "/precio/{precio}")
        @ApiOperation(value = "Busca materiales precio")
        public ResponseEntity <List<Material>> materialPorPrecio(@PathVariable Double precio){
            List<Material> aux=  materialService.buscarMaterialPorPrecio(precio);
            return ResponseEntity.ok(aux);
        }
    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/stock_disponible/{id}")
    @ApiOperation(value = "Busca el stock disponible de un material")
    public ResponseEntity <Integer> stockMaterial(@PathVariable Integer id){
         Integer aux =materialService.buscarMaterial(id).getStockActual();
        return ResponseEntity.ok(aux);
    }



    @CrossOrigin(maxAge = 86400)
        @GetMapping(path = "/{StockMin}/{StockMax}")
        @ApiOperation(value = "Busca materiales por rango de stock")
        public ResponseEntity <List<Material>> materialPorStockEntre(@PathVariable Integer StockMin,@PathVariable Integer StockMax){
            List<Material> aux=  materialService.buscarMaterialPorStockEntre(StockMin,StockMax);
            return ResponseEntity.ok(aux);
        }

    @CrossOrigin(maxAge = 86400)
        @GetMapping(path = "qry")
        @ApiOperation(value = "Busca un Material por nombre utilizano qry")
        public ResponseEntity<Material> materialPorNombre(@RequestParam(required = false, value = "name") String name){

            try{
                return ResponseEntity.ok(materialService.buscarMaterialPorNombre(name));
            }catch (Exception e){
                return ResponseEntity.notFound().build();}
        }


    @CrossOrigin(maxAge = 86400)
        @GetMapping
        @ApiOperation(value = "Busca todos los Materiales")
        public ResponseEntity<List<Material>> todos(){

            return ResponseEntity.ok(materialService.listarMateriales());
        }
    @CrossOrigin(maxAge = 86400)
        @PutMapping(path = "/{id}")
        @ApiOperation(value = "Actualiza un Material")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Actualizado correctamente"),
                @ApiResponse(code = 401, message = "No autorizado"),
                @ApiResponse(code = 403, message = "Prohibido"),
                @ApiResponse(code = 404, message = "El ID no existe")})
        public ResponseEntity<Material> actualizar(@RequestBody Material matNuevo,  @PathVariable Integer id) {
            try {
                materialService.actualizarMaterial(matNuevo, id);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } catch (Exception e) {
                return ResponseEntity.notFound().build();

            }
        }

    @CrossOrigin(maxAge = 86400)
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un Material x id")
    public ResponseEntity<String> borrar(@PathVariable Integer id)  {


        try {
            materialService.bajaMaterial(id);
            String respuesta = "ok "+id;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(respuesta );

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
