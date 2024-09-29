package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.TipoEquipoService;
import unpsjb.labprog.backend.model.TipoEquipo;

@RestController
@RequestMapping("tipoequipos")
public class TipoEquipoPresenter {
    
    @Autowired
    TipoEquipoService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>findAll(){
        return Response.ok(service.findAll());
    }

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
            return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value="/id/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id){
        TipoEquipo aTipoEquipoOrNull = service.findById(id);
        return (aTipoEquipoOrNull != null)?
            Response.ok(aTipoEquipoOrNull):
            Response.notFound("Tipo de Equipo id " + id + " no encontrado");
    }

    @RequestMapping(value="/nombre/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<Object> findByName(@PathVariable("nombre") String nombre){
        TipoEquipo aTipoEquipoOrNull = service.findByName(nombre);
        return (aTipoEquipoOrNull != null)?
            Response.ok(aTipoEquipoOrNull):
            Response.notFound("Tipo de Equipo nombre " + nombre + " no encontrado");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term){
        return Response.ok(service.search(term));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody TipoEquipo aTipoEquipo){
        if ( aTipoEquipo.getId() != 0){
            return Response.error(aTipoEquipo,"Está intentando crear un Tipo de Equipo, éste no puede tener un id indefinido.");
        }
        return Response.ok(service.save(aTipoEquipo),"Tipo de Equipo "+ aTipoEquipo.getNombre() + " registrado correctamente");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody TipoEquipo aTipoEquipo){
        if ( aTipoEquipo.getId() <= 0){
            return Response.error(aTipoEquipo,"debe especificar un id válido para poder modificar el Tipo de Equipo");
        }
        return Response.ok(service.save(aTipoEquipo));
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok("TipoEquipo " + id + " borrado con éxito.");
    }


}
