import { TipoEquipo } from "../tipoequipo/TipoEquipo";

export interface Tarea{
    id: number;
    nombre: string;
    orden: number;
    tiempo: number;
    tipoEquipo: TipoEquipo;
}