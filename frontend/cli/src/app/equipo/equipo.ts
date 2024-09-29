import { Planificacion } from "../planificacion/Planificacion";
import { TipoEquipo } from "../tipoequipo/TipoEquipo";

export interface Equipo{
    id: number;
    codigo: string;
    capacidad: number;
    tipoEquipo: TipoEquipo;
    planificaciones: Planificacion[];
}