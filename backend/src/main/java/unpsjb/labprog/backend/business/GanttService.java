package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.GanttObject;
import unpsjb.labprog.backend.model.GanttObjectPlanificacion;
import unpsjb.labprog.backend.model.Taller;

@Service
public class GanttService {

    @Autowired
    PlanificacionService planificacionService;

    @Autowired
    TallerService tallerService;

    @Autowired
    PedidoFabricacionService pedidoService;

    @Autowired
    GanttRepository ganttRepository;

   
    
    private List<GanttObjectPlanificacion> getPlanifications(Taller workshop, Timestamp from, Timestamp to) {
        if (!ganttRepository.getPlanificatedWorkshops().contains(workshop)) {
            return null;
        }

        List<GanttObjectPlanificacion> result = new ArrayList<GanttObjectPlanificacion>();
        workshop.getEquipos().stream()
                .forEach(equipment -> result.addAll(ganttRepository.getGanttPlanifications(workshop, equipment, from, to)));
        return result;
    }

    public GanttObject getGanttObject(int idTaller, Timestamp from, Timestamp to) {
        Taller taller = tallerService.findById(idTaller);

        GanttObject result = new GanttObject();
        result.setPlanificaciones(getPlanifications(taller, from, to));

        Timestamp minStart = ganttRepository.getMinStart(taller, from, to);
        Timestamp maxFinish = ganttRepository.getMaxFinish(taller, from, to);

        result.setCantDias(convertirDeMilisegunddosADias(maxFinish.getTime() - minStart.getTime()));
        return result;

    }

    private Long convertirDeMilisegunddosADias(Long milis){
        return milis / (1000 * 3600 * 24);
    }



}
