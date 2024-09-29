import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { EquipoService } from './equipo.service';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { TipoEquipoService } from '../tipoequipo/tipo-equipo.service';
import { RobotCommunicationService } from '../guia/robot-communication.service';

@Component({
  selector: 'app-equipo',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
<h2>Equipos</h2>&nbsp;<a routerLink="/equipos/new" class="btn btn-success">Nuevo Equipo</a>
    <div style=" padding: 10px; margin-top: 10px;">
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Código</th>
              <th>Tipo de Equipo</th>
              <th>Capacidad</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let equipo of resultsPage.content; index as i">
              <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
              <td>{{equipo.codigo}}</td>
              <td>{{equipo.tipoEquipo.nombre}}</td>
              <td>{{equipo.capacidad}}</td>
              <td>
                <a routerLink="/equipos/{{equipo.id}}"><i class="fa fa-pencil text-success"></i></a>
                <span style="margin-left: 5px;"></span>
                <a (click)="remove(equipo.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
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
export class EquipoComponent {
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 6;
  constructor(
    private equipoService: EquipoService,
    private tipoEquipoService: TipoEquipoService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService
  ) { }

  getEquipos(): void {
    this.equipoService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getEquipos();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Equipos");
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getEquipos();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar equipo", "¿Está seguro de que desea eliminar éste equipo?", "Si elimina el equipo no lo podrá utilizar luego")
      .then(
        function () {
          that.equipoService.remove(id).subscribe(dataPackage => that.getEquipos());
        }
      )
  }


}
