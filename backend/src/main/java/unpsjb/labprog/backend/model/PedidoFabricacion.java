package unpsjb.labprog.backend.model;

import java.sql.Timestamp;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PedidoFabricacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Timestamp fechaPedido;
    
    private Timestamp fechaEntrega;

    private int cantidad;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Cliente cliente;

    @Enumerated
    private Estado estado;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Planificacion> planificaciones;

}
