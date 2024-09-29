import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ResultsPage } from '../results-page';
import { TipoEquipoService } from './tipo-equipo.service';
import { ModalService } from '../modal/modal.service';
import { RobotCommunicationService } from '../guia/robot-communication.service';

@Component({
  selector: 'app-tipo-equipo',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
  <h2>Tipos de Equipo</h2>
  <a routerLink="/tipoequipos/new" class="btn btn-success">Nuevo Tipo Equipo</a>
  <div style="padding: 10px; margin-top: 10px;">
  <div class="table-responsive">
    <table class="table table-striped table-sm">
      <thead>
        <tr>
          <th>#</th>
          <th>Nombre</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let tipoequipo of resultsPage.content; index as i">
          <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
          <td>{{tipoequipo.nombre}}</td>
          <td>
            <a routerLink="/tipoequipos/{{tipoequipo.id}}"><i class="fa fa-pencil text-success"></i></a>
            <span style="margin-left: 5px;"></span>
            <a (click)="remove(tipoequipo.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
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
export class TipoEquipoComponent {

  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 6;
  constructor(
    private tipoEquipoService: TipoEquipoService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService
  ) { }

  getTipoEquipos(): void {
    this.tipoEquipoService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getTipoEquipos();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Tipos de Equipo");
  }  

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getTipoEquipos();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar tipo de equipo", "¿Está seguro de que desea eliminar éste Tipo de Equipo?", "Si elimina el tipo no lo podrá utilizar luego")
      .then(
        function () {
          that.tipoEquipoService.remove(id).subscribe(dataPackage => that.getTipoEquipos());
        }
      )
  }





}
