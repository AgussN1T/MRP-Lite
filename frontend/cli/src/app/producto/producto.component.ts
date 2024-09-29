import { Component } from '@angular/core';
import { ProductoService } from './producto.service';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { RobotCommunicationService } from '../guia/robot-communication.service';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
    <h2>Producto</h2>&nbsp;<a routerLink="/productos/new" class="btn btn-success">Nuevo Producto</a>
    <div style="padding: 10px; margin-top: 10px;">
    <div class="table-responsive">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Cantidad de Tareas</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let producto of resultsPage.content; index as i">
            <td>{{ i + 1 + (resultsPage.number * this.numeroPaginado)}}</td>
            <td>{{producto.nombre}}</td>
            <td>{{producto.tareas.length}}</td>
            <td>
              <a routerLink="/productos/{{producto.id}}"><i class="fa fa-pencil text-success"></i></a>
              <span style="margin-left: 5px;"></span>
              <a (click)="remove(producto.id)" [routerLink]="" ><i class="fa fa-remove"></i></a>
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
export class ProductoComponent {
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  numeroPaginado: number = 7;
  constructor(
    private productoService: ProductoService,
    private modalService: ModalService,
    private robotCommunication: RobotCommunicationService
  ) { }

  getProductos(): void {
    this.productoService.byPage(this.currentPage, this.numeroPaginado).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
    }
    );
  }

  ngOnInit() {
    this.getProductos();
    this.sendMessageToRobot();
  }

  sendMessageToRobot() {
    this.robotCommunication.sendMessage("Estás en la pantalla de Productos");
  }  

  
  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getProductos();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar producto", "¿Está seguro de que desea eliminar éste producto?", "Si elimina el producto no lo podrá utilizar luego")
      .then(
        function () {
          that.productoService.remove(id).subscribe(dataPackage => that.getProductos());
        }
      )
  }
}
