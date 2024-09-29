import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ClienteComponent } from './cliente/cliente.component';
import { ClienteDetailComponent } from './cliente/cliente-detail.component';
import { TipoEquipoComponent } from './tipoequipo/tipo-equipo.component';
import { TipoEquipoDetailComponent } from './tipoequipo/tipo-equipo-detail.component';
import { TallerComponent } from './taller/taller.component';
import { TallerDetailComponent } from './taller/taller-detail.component';
import { EquipoComponent } from './equipo/equipo.component';
import { EquipoDetailComponent } from './equipo/equipo-detail.component';
import { ProductoDetailComponent } from './producto/producto-detail.component';
import { ProductoComponent } from './producto/producto.component';
import { TareaComponent } from './tarea/tarea.component';
import { TareaDetailComponent } from './tarea/tarea-detail.component';
import { PedidoComponent } from './pedido/pedido.component';
import { PedidoDetailComponent } from './pedido/pedido-detail.component';
import { PlanificacionComponent } from './planificacion/planificacion.component';
import { GanttComponent } from './gantt/gantt.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'clientes', component: ClienteComponent},
    {path: 'clientes/:id', component: ClienteDetailComponent},
    {path: 'tipoequipos', component: TipoEquipoComponent},
    {path: 'tipoequipos/:id', component: TipoEquipoDetailComponent},
    {path: 'talleres', component: TallerComponent},
    {path: 'talleres/:id', component: TallerDetailComponent},
    {path: 'equipos', component: EquipoComponent},
    {path: 'equipos/:id', component: EquipoDetailComponent},
    {path: 'productos', component: ProductoComponent},
    {path: 'productos/:id', component: ProductoDetailComponent},
    {path: 'tareas', component: TareaComponent},
    {path: 'tareas/:id', component: TareaDetailComponent},
    {path: 'pedidos', component: PedidoComponent},
    {path: 'pedidos/:id', component: PedidoDetailComponent},
    {path: 'gantt', component: GanttComponent}
];
