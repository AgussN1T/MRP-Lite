import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TipoEquipoService } from './tipo-equipo.service';
import { ActivatedRoute } from '@angular/router';
import { TipoEquipo } from './TipoEquipo';

@Component({
  selector: 'app-tipo-equipo-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
  <div *ngIf="tipoEquipo">
        <h2>{{tipoEquipo && tipoEquipo.id ? "" + tipoEquipo.nombre : "Nuevo Tipo de Equipo" }}</h2>
        <form #form="ngForm" (ngSubmit)="save()">
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input name="nombre" placeholder="Nombre" class="form-control" style="width: 40%;" [(ngModel)]="tipoEquipo.nombre" required #nombre="ngModel">
                <div *ngIf="nombre.invalid && (nombre.dirty || nombre.touched)" class="alert alert-danger">
                    <div *ngIf="nombre.errors?.['required']">
                        El nombre del Tipo de Equipo es requerido.
                    </div>
                </div>
            </div>
            <br>
            <button type="button" (click)="goBack()" class="btn btn-danger">Atrás</button>
            <span style="margin-left: 10px;"></span>
            <button type="button" (click)="save()" class="btn btn-success" [disabled]="form.invalid">Guardar</button>
        </form>
    </div>
  `,
  styles: ``
})
export class TipoEquipoDetailComponent {
  tipoEquipo!: TipoEquipo;

  constructor(
    private route: ActivatedRoute,
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
      this.tipoEquipo = <TipoEquipo>{ nombre: "" };
    }
    else {
      this.tipoEquipoService.get(parseInt(id!)).subscribe(dataPackage => this.tipoEquipo = <TipoEquipo>dataPackage.data);
    }

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.tipoEquipoService.save(this.tipoEquipo).subscribe(
      dataPackage => { this.tipoEquipo = <TipoEquipo>dataPackage.data; this.goBack(); });
  }


}
