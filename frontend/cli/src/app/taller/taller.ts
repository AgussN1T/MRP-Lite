import { Equipo } from "../equipo/equipo";

export interface Taller{
    id: number;
    codigo: string;
    nombre: string;
    equipos: Equipo[];
}