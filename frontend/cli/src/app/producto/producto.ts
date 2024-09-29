import { Tarea } from "../tarea/tarea";

export interface Producto{
    id: number;
    nombre: string;
    tareas: Tarea[];
}