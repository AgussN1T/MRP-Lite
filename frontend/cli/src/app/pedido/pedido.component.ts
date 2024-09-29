import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ResultsPage } from '../results-page';
import { PedidoService } from './pedido.service';
import { ModalService } from '../modal/modal.service';
import { RobotCommunicationService } from '../guia/robot-communication.service';
import { PlanificadorService } from './planificador.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-pedido',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
    <h2>Pedidos</h2>
    <div>
      <a routerLink="/pedidos/new" class="btn btn-success">Nuevo Pedido</a>
      <span style="margin-left: 10px;"></span>
      <button (click)="planificarTodo()" class="btn btn-secondary">Planificar</button>
      
    </div>
    <br>

    <div class="table-responsive">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>N°</th>
            <th>Cliente</th>
            <th>Producto</th>
            <th>Unidades</th>
            <th>Estado</th>
            <th>Fecha de Pedido</th>
            <th>Fecha de Entrega</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let pedido of resultsPage.content; index as i">
            <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
            <td>{{pedido.id}}</td>
            <td>{{pedido.cliente.razonSocial}}</td>
            <td>{{pedido.producto.nombre}}</td>
            <td>{{pedido.cantidad}}</td>
            <td>{{pedido.estado}}</td>
            <td>{{pedido.fechaPedido | date: 'dd/MM/yyyy'}}</td>
            <td>{{pedido.fechaEntrega | date: 'dd/MM/yyyy'}}</td>
            <td>
            
            <div> 
            
            </div>
            
            <span style="margin-left: 5px;"></span>
            <a routerLink="/pedidos/{{pedido.id}}"><i class="fa fa-pencil text-success"></i></a>
              <span style="margin-left: 5px;"></span>
              <a (click)="remove(pedido.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
            </td>
          </tr>
        </tbody>
      </table>
      <div>
        <app-pagination
        [totalPages]="resultsPage.totalPages"
        [currentPage]="currentPage"
        (pageChangedRequested)="onPageChangeRequested($event)"
        [number]="resultsPage.number"
        [hidden]="resultsPage.numberOfElements < 1"
        ></app-pagination>
        </div>
    </div>
  `,
  styles: ``
})
export class PedidoComponent {


  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 7;
  constructor(
    private pedidoService: PedidoService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService,
    private planificadorService: PlanificadorService,
    private appComponent: AppComponent
  ) { }

  getPedidos(): void {
    this.pedidoService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getPedidos();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Pedidos");
  }



  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getPedidos();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar pedido", "¿Está seguro de que desea eliminar éste pedido?", "Si elimina el pedido no lo podrá utilizar luego")
      .then(
        function () {
          that.pedidoService.remove(id).subscribe(dataPackage => that.getPedidos());
        }
      )
  }

  planificarTodo() {
    this.appComponent.setLoading(true); 
    this.planificadorService.planificarTodo().subscribe(dataPackage => {
      this.appComponent.setLoading(false);
      this.getPedidos()
    });
     
  }


}
