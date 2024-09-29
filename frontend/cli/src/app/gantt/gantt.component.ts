import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { ChartType, GoogleChartsModule } from 'angular-google-charts';
import { Taller } from '../taller/taller';
import { GanttService } from './gantt.service';
import { TallerService } from '../taller/taller.service';
import { ModalService } from '../modal/modal.service';

import { Observable } from 'rxjs/internal/Observable';
import { catchError, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { GanttObject } from './GanttObject';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-gantt',
  standalone: true,
  imports: [CommonModule, FormsModule, GoogleChartsModule, NgbTypeaheadModule],
  template: `
<div class="my-2">
  <h2 class="my-4">Visualización de planificación</h2>
  <label> Seleccione un taller </label>
  <input style="width: 30%;" [(ngModel)]="taller" placeholder="taller..." class="form-control" required
    [ngbTypeahead]="searchWorkshop" [editable]=false [resultFormatter]="resultFormat" [inputFormatter]="inputFormat">
</div>

<div style="margin-top: 2%; margin-bottom: 2%;">
  <label class="mx-3" for="start">Fecha desde:</label>
  <input type="date" [(ngModel)]="from">
  <label class="mx-3" for="end">Fecha hasta:</label>
  <input type="date" [(ngModel)]="to">
</div>

<div class="my-2">
  <button (click)="getGanttObjects()" class="btn btn-success">Mostrar planificación</button>
</div>

<div *ngIf="show" class="scrollable-container">
  <google-chart class="gantt-chart" [type]="chart.type" [data]="chart.data" [options]="chart.options"
    [style.width]="width"></google-chart>
</div>

  `,
 styles: [`
 .equipment-code {
   width: 220px; 
   height: 40px; 
   background-color: #2c2c2c;
   color: white; 
   border-radius: 5px;
   border: 2px solid #424242;
   display: flex;
   justify-content: center;
   align-items: center;
   text-align: center;
 }
 .scrollable-container {
   overflow-x: auto; /* Permite el desplazamiento horizontal */
   width: 100%; /* Ocupa todo el ancho disponible */
   white-space: nowrap; /* Previene que el contenido se envuelva */
 }

 .gantt-chart {
   display: inline-block; /* Hace que el gráfico sea un bloque en línea */
   height: 300px; /* Ajusta la altura según sea necesario */
 }
`]
})
export class GanttComponent {
  
  from!: Date;
  to!: Date;
  pixelsPerDay: number = 25000;
  width!: string;

  ganttObject!: GanttObject;
  taller!: Taller;
  searching: boolean = false;
  searchFailed: boolean = false;
  show: boolean = false;
  chart = {
    title: "Planificaciones",
    type: ChartType.Timeline,
    data: [] as [string, string, Date, Date][],
    options: {
      hAxis: {
        format: 'dd MMM yyyy - HH:mm',
        gridlines: {
          unit: 'minute',
          count: -1 // Autocompute the gridline count
        }
      }
    }
  };

  constructor(
    private service: GanttService,
    private tallerService: TallerService,
    private modalService: ModalService,
    private appComponent: AppComponent
  ) { }

  calculateChartWidth(): string {
    let width = this.ganttObject.cantDias * this.pixelsPerDay;
    console.info(width);
    if(width<5000) width = 2000;
    
    return `${width}px`;
  }

  getGanttObjects(): void {
    this.appComponent.setLoading(true); 
    this.service.getGanttObjects(this.taller.id, this.from, this.to).subscribe(
      (dataPackage) => {
        if (dataPackage.status != 200) {
          this.show = false;
          this.modalService.error(
            "Error",
            "Error al buscar el taller.",
            dataPackage.message
          );
        } else {

          this.show = true;
          this.ganttObject = <GanttObject>dataPackage.data;
          this.width = this.calculateChartWidth();
          this.updateChart();

        }
        this.appComponent.setLoading(false);
      }
    );
  }

  updateChart(): void {
    console.log(this.ganttObject);
    this.chart.data = this.ganttObject.planificaciones.map(gantt => [
      gantt.equipo.codigo,
      gantt.pedido.producto.nombre,
      new Date(gantt.inicio),
      new Date(gantt.fin)
    ]);
  }

  searchWorkshop = (text$: Observable<string>): Observable<any[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      filter(term => term.length >= 2),
      switchMap((term) =>
        this.tallerService
          .search(term)
          .pipe(
            map((response) => {
              let equipment = <Taller[]>response.data;
              return equipment;
            })
          ).pipe(
            tap(() => (this.searchFailed = false)),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            })
          )
      ),
      tap(() => (this.searching = false))
    );

  resultFormat(value: any) {
    return value.nombre;
  }

  inputFormat(value: any) {
    return value ? value.codigo : null;
  }


}
