import { Equipo } from "../equipo/equipo";
import { Pedido } from "../pedido/pedido";

export interface GanttObjectPlanificacion{
    equipo: Equipo;
    pedido: Pedido;
    inicio: Date;
    fin: Date;
}