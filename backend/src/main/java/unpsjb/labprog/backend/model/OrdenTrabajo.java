package unpsjb.labprog.backend.model;

import java.util.Collection;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class OrdenTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int numero;

    @OneToMany
    private Collection<Planificacion> planificaciones;

    @ManyToOne
    private Equipo equipo;

}
