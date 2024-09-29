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
import unpsjb.labprog.backend.business.TallerService;
import unpsjb.labprog.backend.model.Taller;

@RestController
@RequestMapping("talleres")
public class TallerPresenter {
    
    @Autowired
    TallerService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>findAll(){
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term){
        return Response.ok(service.search(term));
    }    

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
            return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value="/codigo/{codigo}", method=RequestMethod.GET)
    public ResponseEntity<Object> findByCode(@PathVariable("codigo") String codigo){
        Taller aTallerOrNull = service.findByCode(codigo);
        return (aTallerOrNull != null)?
            Response.ok(aTallerOrNull):
            Response.notFound("Taller código " + codigo + " no encontrado");
    }
    
    @RequestMapping(value="/id/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id){
        Taller aTallerOrNull = service.findById(id);
        return (aTallerOrNull != null)?
            Response.ok(aTallerOrNull):
            Response.notFound("Taller id " + id + " no encontrado");
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Taller aTaller){
        if ( aTaller.getId() != 0){
            return Response.error(aTaller,"Está intentando crear un Taller, éste no puede tener un id indefinido.");
        }
        return Response.ok(service.save(aTaller),"Taller "+ aTaller.getCodigo() + " ingresado correctamente");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Taller aTaller){
        if ( aTaller.getId() <= 0){
            return Response.error(aTaller,"debe especificar un id válido para poder modificar el Taller");
        }
        return Response.ok(service.save(aTaller),"Taller " + aTaller.getCodigo() + " actualizado correctamente");
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok("Taller " + id + " borrado con éxito.");
    }

}
