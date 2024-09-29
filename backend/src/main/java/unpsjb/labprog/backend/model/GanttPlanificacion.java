package unpsjb.labprog.backend.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GanttPlanificacion {
    
    public GanttPlanificacion(Planificacion planificacion, boolean esHueco){
        
        this.esHueco = esHueco;
        
        this.id = planificacion.getId();
        this.inicio = planificacion.getInicio();
        this.fin = planificacion.getFin();
        this.tarea = planificacion.getTarea();
        this.equipo = planificacion.getEquipo().getCodigo();
    }

    private boolean esHueco;

    private int id;
    
    private Timestamp inicio;

    private Timestamp fin;
    
    private Tarea tarea;

    private String equipo;

}
