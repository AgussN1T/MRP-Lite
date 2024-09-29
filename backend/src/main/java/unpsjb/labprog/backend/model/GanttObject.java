package unpsjb.labprog.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GanttObject {
    
    private long cantDias;
    private List<GanttObjectPlanificacion> planificaciones;

}
