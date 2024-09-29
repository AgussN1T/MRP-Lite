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
import unpsjb.labprog.backend.business.TareaService;
import unpsjb.labprog.backend.model.Tarea;

@RestController
@RequestMapping("tareas")
public class TareaPresenter {
    
    @Autowired
    TareaService service;

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
        Tarea aTareaOrNull = service.findById(id);
        return (aTareaOrNull != null)?
            Response.ok(aTareaOrNull):
            Response.notFound("Tarea id " + id + " no encontrado");
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Tarea aTarea){
        if ( aTarea.getId() != 0){
            return Response.error(aTarea,"Está intentando crear un Tarea, éste no puede tener un id indefinido.");
        }
        return Response.ok(service.save(aTarea),"Tarea "+ aTarea.getNombre() + " ingresado correctamente");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Tarea aTarea){
        if ( aTarea.getId() <= 0){
            return Response.error(aTarea,"debe especificar un id válido para poder modificar el Tarea");
        }
        return Response.ok(service.save(aTarea),"Tarea " + aTarea.getNombre() + " actualizado correctamente");
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok("Tarea " + id + " borrado con éxito.");
    }

}
