
import { GanttObjectPlanificacion } from "./GanttObjectPlanificacion";

export interface GanttObject{

    cantDias: number;
    planificaciones: GanttObjectPlanificacion[];
}