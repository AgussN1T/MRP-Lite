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
public class GanttTaller {
    
    public GanttTaller(Taller taller){
        this.id = taller.getId();
        this.codigo = taller.getCodigo();
        this.nombre = taller.getNombre();
        this.equipos = new ArrayList<GanttEquipo>();
    }

    private int id;
    
    private String codigo;
    
    private String nombre;

    private Timestamp primeraFechaPlanificacion;
    
    private Timestamp ultimaFechaPlanificacion;

    private Collection<GanttEquipo> equipos;


}
