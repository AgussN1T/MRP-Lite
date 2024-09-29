import { Component } from '@angular/core';
import { TallerService } from './taller.service';
import { ActivatedRoute } from '@angular/router';
import { Taller } from './taller';
import { CommonModule, Location, UpperCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EquipoService } from '../equipo/equipo.service';
import { NgbTypeaheadModule, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Equipo } from '../equipo/equipo';
import { Observable, catchError, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { ModalService } from '../modal/modal.service';
import { TipoEquipoService } from '../tipoequipo/tipo-equipo.service';
import { TipoEquipo } from '../tipoequipo/TipoEquipo';

@Component({
  selector: 'app-taller-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, UpperCasePipe, NgbTypeaheadModule],
  template: `
        <div *ngIf="taller">
      <h2>{{ taller && taller.id ? "Taller  " + taller.codigo : "Nuevo Taller"  }}</h2>
      <form #form="ngForm">
      <div class="form-group">
          <label for="codigo">Código:</label>
          <input 
          name="codigo" 
          placeholder="Código" 
          class="form-control" 
          style="width: 40%;"
          [(ngModel)]="taller.codigo" 
          required #code="ngModel">
          <div *ngIf="code.invalid && (code.dirty || code.touched)" class="alert alert-danger">
            <div *ngIf="code.errors?.['required']">
              EL Código del taller es requerido.
            </div>
          </div>
        </div>
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input 
          name="nombre" 
          placeholder="Nombre" 
          class="form-control" 
          style="width: 40%;"
          [(ngModel)]="taller.nombre" 
          required #name="ngModel">
          <div *ngIf="name.invalid && (name.dirty || name.touched)" class="alert alert-danger">
            <div *ngIf="name.errors?.['required']">
              EL Nombre del taller es requerido.
            </div>
          </div>
        </div>
        <h2>Equipos</h2>
        <div class="table-responsive">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th>#</th>
            <th>Equipo</th>
            <th>Capacidad</th>
            <th>Tipo de Equipo</th>
            <th>
              <button (click)="addEquipo()" class="btn btn-success">
              Agregar
              </button>
            </th>
          </tr>
        </thead>
        <tbody>
            <tr *ngFor="let equipo of taller.equipos; let i = index">
              <td>{{ i + 1 }}</td>
              <td><input name="codigo{{i}}" placeholder="código" [(ngModel)]="equipo.codigo" class="form-control" required></td>
              <td><input name="capacidad{{i}}" [(ngModel)]="equipo.capacidad" class="form-control" type="number" min="0" required></td>
              <td><input 
                  [(ngModel)]="equipo.tipoEquipo"
                  name="tipo{{i}}"
                  placeholder="Tipo"
                  class="form-control"
                  required
                  [ngbTypeahead]="searchTipoEquipo"
                  [editable]=false
                  [resultFormatter]="resultFormat"
                  [inputFormatter]="inputFormat"
                  ></td>
              <td>
                <button (click)="removeEquipo(equipo)" class="btn btn-default"><i class="fa fa-remove"></i></button>
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
export class TallerDetailComponent {
  taller!: Taller;
  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private tallerService: TallerService,
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
      this.taller = <Taller>{
        equipos: <Equipo[]>[]
      };
    }
    else {
      this.tallerService.get(parseInt(id!)).subscribe(dataPackage => this.taller = <Taller>dataPackage.data);
    }

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    console.log(this.taller);
    this.tallerService.save(this.taller).subscribe(dataPackage => { this.taller = <Taller>dataPackage.data; this.goBack(); });
  }

  searchTipoEquipo = (text$: Observable<string>): Observable<any[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(), filter(term => term.length >= 3),
      tap(() => (this.searching = true)),
      switchMap((term) =>
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

  addEquipo() {
    this.taller.equipos.push(<Equipo>{});
  }

  removeEquipo(equipo: Equipo) {
    this.modalService.confirm("Eliminar Equipo", "¿Está seguro de borrar este Equipo?", "El cambio no se confirmará hasta que no guarde el Taller").then(
      (_) => {
        const index = this.taller.equipos.indexOf(equipo);
        if (index !== -1) {
          this.taller.equipos.splice(index, 1);
        }
      }
    );
  }

  onSelectEquipo(event: NgbTypeaheadSelectItemEvent<any>, index: number) {
    const equipo: Equipo = event.item;
    console.log(equipo);
    this.taller.equipos[index] = equipo;

  }


}
