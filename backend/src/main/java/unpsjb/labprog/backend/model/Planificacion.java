package unpsjb.labprog.backend.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Planificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private Timestamp inicio;

    private Timestamp fin;

    @ManyToOne
    private Tarea tarea;

    @ManyToOne(cascade = CascadeType.ALL)
    private Equipo equipo;
    
    @JsonIgnore
    @OneToOne
    private PedidoFabricacion pedido;
}
