package utn.gallino.msstock.Rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.gallino.msstock.Dominio.MovimientosStock;
import utn.gallino.msstock.Dominio.Provision;
import utn.gallino.msstock.Service.Impl.ProvisionServiceImpl;
import utn.gallino.msstock.Service.ProvisionService;

import java.util.List;


@RestController
@RequestMapping("/api/provision")
@Api(value = "Provision Rest", description = "Permite gestionar las Provisiones de los materiales empresa")
public class ProvisionRest {


@Autowired
ProvisionService provisionService;
    @CrossOrigin(maxAge = 86400)
    @PostMapping
    @ApiOperation(value = "Crear una Provision")
    public ResponseEntity<String> crear(@RequestBody Provision p)  {
        try{
            provisionService.crearProvision(p);                                                                                   // si quiero retorna la entidad al crearla ResponseEntity.ok(nuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}
    }


    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca una Provision por id")
    public ResponseEntity<Provision> provisionPorId(@PathVariable Integer id){

        try{
            // si quiero retorna la entidad al crearla ResponseEntity.ok(nuevo);
            Provision prov= provisionService.buscarProvisionPorId(id);
            return ResponseEntity.ok(prov);
        }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}


    }


    @CrossOrigin(maxAge = 86400)
    @GetMapping(path = "/")
    @ApiOperation(value = "Busca todos las Provisiones")
    public ResponseEntity<List<Provision>> listarProvisiones(){
        try{
            return ResponseEntity.ok(provisionService.listarProvisiones());
        }catch (Exception e){return ResponseEntity.status(HttpStatus.CONFLICT).build();}


    }



    @CrossOrigin(maxAge = 86400)
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza una Provision")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")})
    public ResponseEntity<Provision> actualizar(@RequestBody Provision provNueva,  @PathVariable Integer id) {
        try {
            Provision ms= provisionService.actualizarProvision(provNueva, id);
            return ResponseEntity.ok(ms);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }


    @CrossOrigin(maxAge = 86400)
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina una Provision x id")
    public ResponseEntity<String> borrar(@PathVariable Integer id)  {


        try {
            provisionService.bajaProvision(id);
            String respuesta = "ok "+id;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(respuesta );

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }





}
