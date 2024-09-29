import { Component } from '@angular/core';
import { Equipo } from './equipo';
import { CommonModule, Location, UpperCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EquipoService } from './equipo.service';
import { TipoEquipoService } from '../tipoequipo/tipo-equipo.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, catchError, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { TipoEquipo } from '../tipoequipo/TipoEquipo';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-equipo-detail',
  standalone: true,
  imports: [CommonModule, FormsModule,UpperCasePipe,NgbTypeaheadModule],
  template: `
    <div *ngIf="equipo">
            <h2>{{ equipo.codigo | uppercase }}</h2>
            <form #form="ngForm" (ngSubmit)="save()">
                <div class="form-group">
                    <label for="codigo">Código:</label>
                    <input name="codigo" placeholder="Código" class="form-control" [(ngModel)]="equipo.codigo" required #codigo="ngModel">
                    <div *ngIf="codigo.invalid && (codigo.dirty || codigo.touched)" class="alert alert-danger">
                        <div *ngIf="codigo.errors?.['required']">
                            El código del equipo es requerido.
                        </div>
                    </div>
                </div>
                <div class="form-group">
                  <label for="tipoEquipo">Tipo: </label>
                  <br/>
                  <input 
                  [(ngModel)]="equipo.tipoEquipo"
                  name="tipo"
                  placeholder="Tipo"
                  class="form-control"
                  required
                  [ngbTypeahead]="searchTipoEquipo"
                  [editable]=false
                  [resultFormatter]="resultFormat"
                  [inputFormatter]="inputFormat"
                  >
                </div>
                <div class="form-group">
                <label for="capacidad">Capacidad:</label>
                <input name="capacidad" placeholder="Capacidad" class="form-control" type="number" min= "0" [(ngModel)]="equipo.capacidad" required #capacidad="ngModel">
                <div *ngIf="capacidad.invalid && (capacidad.dirty || capacidad.touched)" class="alert alert-danger">
                    <div *ngIf="capacidad.errors?.['required']">
                      La capacidad del equipo es requerida.
                    </div>
                  </div>
                </div>
                <button type="button" (click)="goBack()" class="btn btn-danger">Atrás</button>
                <span style="margin-left: 10px;"></span>
                <button type="button" (click)="save()" class="btn btn-success" [disabled]="form.invalid">Guardar</button>
            </form>
        </div>
  `,
  styles: ``
})
export class EquipoDetailComponent {
  equipo!: Equipo;
  searching: boolean= false;
  searchFailed: boolean =false;


  constructor(
    private route: ActivatedRoute,
    private equipoService: EquipoService,
    private tipoEquipoService: TipoEquipoService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.get();
  }

  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.equipo = <Equipo>{ codigo: "" };
    }
    else {
      this.equipoService.get(parseInt(id!)).subscribe(dataPackage => this.equipo = <Equipo>dataPackage.data);
    }

  }

  searchTipoEquipo = (text$: Observable<string>): Observable<any[]> =>
  text$.pipe(
    debounceTime(300),
    distinctUntilChanged(),filter(term => term.length >= 3),
    tap(()=> (this.searching= true)) ,
    switchMap((term) =>
    this.tipoEquipoService
    .search(term)
    .pipe(
      map((response)=>
      {
        let tipoEquipos=<TipoEquipo[]> response.data;
        return tipoEquipos;
      })
    )
    .pipe(
      tap(()=> (this.searchFailed =false)),
      catchError(()=>{
        this.searchFailed=true;
        return of([])
      })
    )
  ),
  tap(()=> (this.searching= false))
  );  

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.equipoService.save(this.equipo).subscribe(dataPackage => { this.equipo = <Equipo>dataPackage.data; this.goBack(); });
  }

  resultFormat(value:any){
    return value.nombre;
  }
  inputFormat(value:any){
    return value ? value.nombre : null;
  }



}
