package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PlanificacionService;
import unpsjb.labprog.backend.model.Planificacion;

@RestController
@RequestMapping("planificaciones")
public class PlanificacionPresenter {
    
    
    @Autowired
    PlanificacionService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>findAll(){
        return Response.ok(service.findAll());
    }
    
    @RequestMapping(value="planificacionEquipos/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object>planEquipos(@PathVariable("id") int id){
        
        List<Planificacion> somePlanificaciones = service.findByEquipoId(id);

        return (!somePlanificaciones.isEmpty())?
            Response.ok(somePlanificaciones):
            Response.notFound("El equipo con id " + id + " no tiene ninguna planificacion");
    }
}
