import { Equipo } from "../equipo/equipo";
import { Planificacion } from "../planificacion/Planificacion";

export interface OrdenTrabajo{
    id: number;
    
    numero: number;
    
    planificacion: Planificacion;

    equipo: Equipo;
}