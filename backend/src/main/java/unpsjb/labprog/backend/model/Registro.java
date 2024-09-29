package unpsjb.labprog.backend.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Registro {
    
    public Registro(String mensaje){
        this.fechaRegistro = new Timestamp(System.currentTimeMillis());
        this.mensaje = mensaje;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Timestamp fechaRegistro;

    private String mensaje;

}

