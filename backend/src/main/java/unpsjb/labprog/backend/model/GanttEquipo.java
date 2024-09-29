package unpsjb.labprog.backend.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GanttEquipo {

    public GanttEquipo(Equipo equipo) {
        this.id = equipo.getId();
        this.codigo = equipo.getCodigo();
        this.capacidad = equipo.getCapacidad();
        this.tipoEquipo = equipo.getTipoEquipo();
        this.planificaciones = new ArrayList<GanttPlanificacion>();
    }

    private Timestamp primeraPlanificacion;

    private Timestamp ultimaPlanificacion;
    
    private int id;

    private String codigo;

    private double capacidad;

    private TipoEquipo tipoEquipo;

    private Collection<GanttPlanificacion> planificaciones;

}
