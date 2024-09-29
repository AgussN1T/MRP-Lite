import { Cliente } from "../cliente/cliente";
import { Planificacion } from "../planificacion/Planificacion";
import { Producto } from "../producto/producto";
import { Estado } from "./Estado";

export interface Pedido{
    id: number;

    fechaPedido: Date;

    fechaEntrega: Date;

    cantidad: number;

    producto: Producto;

    cliente: Cliente;

    estado: Estado;

    planificaciones: Planificacion[];
}