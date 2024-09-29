import { Component } from '@angular/core';
import { ResultsPage } from '../results-page';
import { ClienteService } from './cliente.service';
import { ModalService } from '../modal/modal.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { RobotCommunicationService } from '../guia/robot-communication.service';
import { NotificacionComponent } from '../notificacion/notificacion.component';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent,NotificacionComponent],
  template: `
    <h2>Clientes</h2>
    <app-notificacion></app-notificacion>
    <div>
      <a routerLink="/clientes/new" class="btn btn-success">Nuevo Cliente</a>
    </div>
    <div style="padding: 10px; margin-top: 10px;">
    <div class="table-responsive">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>Razón Social</th>
            <th>CUIT</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let cliente of resultsPage.content; index as i">
            <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
            <td>{{cliente.razonSocial}}</td>
            <td>{{cliente.cuit}}</td>
            <td>
              <a routerLink="/clientes/{{cliente.id}}"><i class="fa fa-pencil text-success"></i></a>
              <span style="margin-left: 5px;"></span>
              <a (click)="remove(cliente.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
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
    </div>
  `,
  styles: ``
})
export class ClienteComponent {
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 7;
  constructor(
    private clienteService: ClienteService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService
  ) { }

  getClientes(): void {
    this.clienteService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getClientes();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Clientes");
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getClientes();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar cliente", "¿Está seguro de que desea eliminar éste cliente?", "Si elimina el cliente no lo podrá utilizar luego")
      .then(
        function () {
          that.clienteService.remove(id).subscribe(dataPackage => that.getClientes());
        }
      )
  }

}
