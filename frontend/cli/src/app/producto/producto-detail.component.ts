import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from './producto.service';
import { TipoEquipoService } from '../tipoequipo/tipo-equipo.service';
import { ModalService } from '../modal/modal.service';
import { Producto } from './producto';
import { Tarea } from '../tarea/tarea';
import { CommonModule, Location, UpperCasePipe } from '@angular/common';
import { Observable, catchError, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { TipoEquipo } from '../tipoequipo/TipoEquipo';
import { FormsModule } from '@angular/forms';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-producto-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, UpperCasePipe, NgbTypeaheadModule],
  template: `
        <div *ngIf="producto">
        <h2>
    {{ producto && producto.id ? "Producto N° " + producto.id : "Nuevo Producto" }}
  </h2>
      <form #form="ngForm">
      <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input name="nombre" placeholder="Nombre" class="form-control" [(ngModel)]="producto.nombre" required #name="ngModel">
          <div *ngIf="name.invalid && (name.dirty || name.touched)" class="alert alert-danger">
            <div *ngIf="name.errors?.['required']">
              EL Nombre del producto es requerido.
            </div>
          </div>
        </div>
        <h2>Tareas</h2>
        <div class="table-responsive">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>Tarea</th>
            <th>orden</th>
            <th>tiempo</th>
            <th>tipo de equipo</th>
            <th>
              <button (click)="addTarea()" class="btn btn-success">
              Agregar
              </button>
            </th>
          </tr>
        </thead>
        <tbody>
            <tr *ngFor="let tarea of producto.tareas; let i = index">
              <td>{{ i + 1 }}</td>
              <td><input name="nombre{{i}}" placeholder="nombre" [(ngModel)]="tarea.nombre" class="form-control" required></td>
              <td><input name="orden{{i}}" [(ngModel)]="tarea.orden" class="form-control" type="number" min="0" required></td>
              <td><input name="tiempo{{i}}" [(ngModel)]="tarea.tiempo" class="form-control" type="number" min="0" required></td>
              <td><input 
                [(ngModel)]="tarea.tipoEquipo"
                name="tipo{{i}}"
                placeholder="Tipo"
                class="form-control"
                required
                [ngbTypeahead]="searchTipoEquipo"
                [editable]="false"
                [resultFormatter]="resultFormat"
                [inputFormatter]="inputFormat"
                ></td>
              <td>
                <button (click)="removeTarea(tarea)" class="btn btn-default"><i class="fa fa-remove"></i></button>
              </td>
            </tr>
          </tbody>
      </table>
    </div>
        <button (click)="goBack()" class="btn btn-danger">Atrás</button>
        <span style="margin-left: 10px;"></span>
        <button (click)="save()" class="btn btn-success" [disabled]="form.invalid">Guardar</button>
      </form>
    </div>
  `,
  styles: ``
})
export class ProductoDetailComponent {
  producto!: Producto;
  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private tipoEquipoService: TipoEquipoService,
    private modalService: ModalService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.get();
  }

  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.producto = <Producto>{
        tareas: <Tarea[]>[]
      };
    }
    else {
      this.productoService.get(parseInt(id!)).subscribe(dataPackage => this.producto = <Producto>dataPackage.data);
    }

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    console.log(this.producto);
    this.productoService.save(this.producto).subscribe(dataPackage => { this.producto = <Producto>dataPackage.data; this.goBack(); });
  }

  searchTipoEquipo = (text$: Observable<string>): Observable<any[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(), filter(term => term.length >= 3),
      tap(() => (this.searching = true)),
      switchMap((term: string) =>
        this.tipoEquipoService
          .search(term)
          .pipe(
            map((response) => {
              let tipoEquipos = <TipoEquipo[]>response.data;
              return tipoEquipos;
            })
          )
          .pipe(
            tap(() => (this.searchFailed = false)),
            catchError(() => {
              this.searchFailed = true;
              return of([])
            })
          )
      ),
      tap(() => (this.searching = false))
    );

    resultFormat(value: any) {
      return value.nombre;
    }
    inputFormat(value: any) {
      return value ? value.nombre : null;
    }
  
    addTarea() {
      this.producto.tareas.push(<Tarea>{});
    }
    removeTarea(tarea: Tarea) {
      this.modalService.confirm("Eliminar Tarea", "¿Está seguro de borrar esta Tarea?", "El cambio no se confirmará hasta que no guarde el Producto").then(
        (_) => {
          const index = this.producto.tareas.indexOf(tarea);
          if (index !== -1) {
            this.producto.tareas.splice(index, 1);
          }
        }
      );
    }

}
