package unpsjb.labprog.backend.presenter;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.GanttService;
import unpsjb.labprog.backend.business.TallerService;
import unpsjb.labprog.backend.model.GanttObject;
import unpsjb.labprog.backend.model.Taller;

@RestController
@RequestMapping("gantt")
public class GanttPresenter {
    
    @Autowired
    GanttService ganttService;

    @Autowired
    TallerService tallerService; 


@RequestMapping(value = "/object/{id}/{from}/{to}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGanttObject(
        @PathVariable("id") int idTaller,
        @PathVariable("from") String fromStr,
        @PathVariable("to") String toStr) {

        Timestamp from = new Timestamp(Date.valueOf(fromStr).getTime());
        Timestamp to = new Timestamp(Date.valueOf(toStr).getTime());
        if(from == null || to == null) return Response.error("El taller no tiene planificaciones");
        Taller taller = tallerService.findById(idTaller);
        if (taller == null)
            return Response.notFound("No se encontro el equipo id " + idTaller);

        GanttObject result = null;
        try {
            result = ganttService.getGanttObject(idTaller, from, to);
        } catch (Exception e) {
            return Response.error(idTaller, e.getMessage());
        }

        return Response.ok(result, "Gantt obtenido correctamente");
    }


}
