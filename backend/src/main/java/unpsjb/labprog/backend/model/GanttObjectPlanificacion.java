package unpsjb.labprog.backend.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GanttObjectPlanificacion {

    private Equipo equipo;
    private PedidoFabricacion pedido;
    private Timestamp inicio;
    private Timestamp fin;

    public GanttObjectPlanificacion (Equipo equipo, PedidoFabricacion pedido, Timestamp inicio, Timestamp fin){
        this.equipo = equipo;
        this.pedido = pedido;
        this.inicio = inicio;
        this.fin = fin;
    }
}
