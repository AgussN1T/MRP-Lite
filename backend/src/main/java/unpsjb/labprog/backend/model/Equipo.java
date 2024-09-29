package unpsjb.labprog.backend.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String codigo;

    @Column(nullable = false)
    private double capacidad;

    @ManyToOne
    private TipoEquipo tipoEquipo;

    @OneToMany(mappedBy = "equipo",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Planificacion> planificaciones;
}
