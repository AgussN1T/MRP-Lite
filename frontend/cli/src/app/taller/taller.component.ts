import { Component } from '@angular/core';
import { TallerService } from './taller.service';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { RobotCommunicationService } from '../guia/robot-communication.service';

@Component({
  selector: 'app-taller',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
        <h2>Talleres</h2>&nbsp;<a routerLink="/talleres/new" class="btn btn-success">Nuevo Taller</a>
            <div style="padding: 10px; margin-top: 10px;">
            <div class="table-responsive">
              <table class="table table-striped table-sm">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>id</th>
                    <th>Codigo</th>
                    <th>Nombre</th>
                    <th>Cantidad de Equipos</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let taller of resultsPage.content; index as i">
                    <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
                    <td>{{taller.id}}</td>
                    <td>{{taller.codigo}}</td>
                    <td>{{taller.nombre}}</td>
                    <td>{{taller.equipos.length}}</td>
                    <td>
                      <span style="margin-left: 5px;"></span>
                      <a routerLink="/talleres/{{taller.id}}"><i class="fa fa-pencil text-success"></i></a>
                      <span style="margin-left: 5px;"></span>
                      <a (click)="remove(taller.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
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
export class TallerComponent {
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 6;

  constructor(
    private tallerService: TallerService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService
  ) { }

  getTallers(): void {
    this.tallerService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getTallers();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Talleres");
  }  

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getTallers();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar Taller", "¿Está seguro de que desea eliminar éste Taller?", "Si elimina el Taller no lo podrá utilizar luego")
      .then(
        function () {
          that.tallerService.remove(id).subscribe(dataPackage => that.getTallers());
        }
      )
  }
}
