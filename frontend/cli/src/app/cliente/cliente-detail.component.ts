import { Component } from '@angular/core';
import { ClienteService } from './cliente.service';
import { ActivatedRoute } from '@angular/router';
import { Cliente } from './cliente';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationService } from '../notificacion/notificacion.service';
import { ModalService } from '../modal/modal.service';

@Component({
  selector: 'app-cliente-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
      <div *ngIf="cliente">
        <h2>{{ cliente.razonSocial | uppercase }}</h2>
        <form #form="ngForm" (ngSubmit)="save()">
            <div class="form-group">
                <label for="razonSocial">Razón Social:</label>
                <input name="razonSocial" 
                placeholder="Razon Social" 
                class="form-control" 
                [(ngModel)]="cliente.razonSocial" 
                required 
                #razonSocial="ngModel">
                <div *ngIf="razonSocial.invalid && (razonSocial.dirty || razonSocial.touched)" class="alert alert-danger">
                    <div *ngIf="razonSocial.errors?.['required']">
                        La razon social del cliente es requerida.
                    </div>
                </div>
            </div>
            <div class="form-group">
            <label for="cuit">CUIT:</label>
            <input name="cuit" 
            placeholder="CUIT" 
            class="form-control" 
            maxlength="11" 
            minlength="11" 
            [(ngModel)]="cliente.cuit" 
            required 
            #cuit="ngModel" 
            inputmode="numeric">
            <div *ngIf="cuit.invalid && (cuit.dirty || cuit.touched)" class="alert alert-danger">
                <div *ngIf="cuit.errors?.['required']">
                  EL CUIT del cliente es requerido.
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
export class ClienteDetailComponent {
  cliente!: Cliente;

  constructor(
    private route: ActivatedRoute,
    private clienteService: ClienteService,
    private location: Location,
    private notificationService: NotificationService,
    private modalService: ModalService
    ) {
  }

  ngOnInit() {
    this.get();
  }

  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.cliente = <Cliente>{ razonSocial: "" };
    }
    else {
      this.clienteService.get(parseInt(id!)).subscribe(dataPackage => this.cliente = <Cliente>dataPackage.data);
    }

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.clienteService.save(this.cliente).subscribe(dataPackage => { 
      if (dataPackage.status != 200) {
        this.modalService.error(
          "Error",
          "Error al guardar el cliente.",
          dataPackage.message
        )}
        else{
          this.cliente = <Cliente>dataPackage.data;
          this.notificationService.showNotification(`Cliente ${this.cliente.razonSocial} guardado correctamente`);
          
          this.goBack();
        } 
      });
  }


}
