package unpsjb.labprog.backend.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Hueco {

    public Hueco(Timestamp inicioHueco, Timestamp finHueco, Equipo equipo) {
        this.inicioHueco = inicioHueco;
        this.finHueco = finHueco;
        this.equipo = equipo;
    }

    private Timestamp inicioHueco;

    private Timestamp finHueco;

    private Equipo equipo;

    public int tiempoHueco() {

        return (int) (((finHueco.getTime() - inicioHueco.getTime()) / (60000)) * equipo.getCapacidad());
    }

    
    
}
