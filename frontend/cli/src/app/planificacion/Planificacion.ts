import { Equipo } from "../equipo/equipo";
import { Tarea } from "../tarea/tarea";

export interface Planificacion{
    id: number;
    tarea: Tarea;
    inicio: Date;
    fin: Date;
    equipo: Equipo
}