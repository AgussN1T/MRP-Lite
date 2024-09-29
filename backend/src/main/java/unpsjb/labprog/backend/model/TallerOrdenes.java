package unpsjb.labprog.backend.model;



import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TallerOrdenes {
    
    private Taller taller;
    
    private Collection<OrdenTrabajo> ordenes;
}
