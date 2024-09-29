import { CommonModule, Location, UpperCasePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbCalendar, NgbDateStruct, NgbDatepickerModule, NgbTypeaheadModule, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from './pedido';
import { ClienteService } from '../cliente/cliente.service';
import { ProductoService } from '../producto/producto.service';
import { ActivatedRoute } from '@angular/router';
import { ModalService } from '../modal/modal.service';
import { PedidoService } from './pedido.service';
import { Observable, catchError, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { Cliente } from '../cliente/cliente';
import { Producto } from '../producto/producto';
import { GanttModule } from '@syncfusion/ej2-angular-gantt';
import { Estado } from './Estado';
import { Planificacion } from '../planificacion/Planificacion';

@Component({
  selector: 'app-pedido-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, UpperCasePipe, NgbTypeaheadModule, NgbDatepickerModule],
  template: `
        <div *ngIf="pedido">
        <h2>{{ pedido && pedido.id ? "Pedido N° " + pedido.id : "Nuevo Pedido" }}</h2>
        <form #form="ngForm">

        <h3>Cliente</h3>
        <div style="display: flex;">
          <div class="form-group">   
              <label for="cliente">Razón Social: </label>
                <br/>
                <input 
                [(ngModel)]="pedido.cliente"
                name="cliente"
                placeholder="RazónSocial"
                class="form-control"
                style="width: 90%; display: inline-block;"
                required
                [ngbTypeahead]="searchCliente"
                [editable]=false
                [resultFormatter]="resultFormat"
                [inputFormatter]="inputFormat"
                (selectItem)="onClienteSelect($event)"
                #cliente="ngModel"
                >
                <div *ngIf="cliente.invalid && cliente.touched" class="alert alert-danger">
                  <div *ngIf="cliente.errors?.['required']">
                    El producto es requerido.
                  </div>
                </div>
            </div>
          <div class="form-group"> 
            <label for="cuit">CUIT:</label>
            <br>
            <input name="cuit" 
            placeholder="CUIT" 
            class="form-control" 
            style="width: 90%;" 
            type="number"
            [(ngModel)]="pedido.cliente.cuit" 
            required 
            #cuit="ngModel" 
            readonly 
            inputmode="numeric">
          </div>
        </div>

        <label for="fechaPedido">Fecha Pedido:</label>
        <br>
        <form class="row row-cols-sm-auto">
        <div class="col-12">
          <div class="input-group">
            <input
              class="form-control"
              style="display: inline-block;"
              placeholder="yyyy-mm-dd"
              name="fpp"
              [(ngModel)]="pedidoFechaPedido"
              ngbDatepicker
              #fpp="ngbDatepicker"
            />
              <button class="btn btn-outline-secondary fa fa-calendar" (click)="fpp.toggle()" type="button"></button>
            </div>
          </div>
        </form>

        <label for="fechaEntrega">Fecha Entrega:</label>
        <br>
        <form class="row row-cols-sm-auto">
        <div class="col-12">
          <div class="input-group">
            <input
              class="form-control"
              style="display: inline-block;"
              placeholder="yyyy-mm-dd"
              name="fpe"
              [(ngModel)]="pedidoFechaEntrega"
              ngbDatepicker [minDate]="pedidoFechaPedido"
              #fpe="ngbDatepicker"
              />
              <button class="btn btn-outline-secondary fa fa-calendar" (click)="fpe.toggle()" type="button"></button>
            </div>
          </div>
        </form>
        
        <h3>Producto</h3>
        <div style="display: flex;">
          <div class="form-group">   
            <label for="producto">Producto: </label>
            <br>  
            <input 
              [(ngModel)]="pedido.producto"
              name="producto"
              placeholder="Nombre"
              class="form-control"
              style="width: 110%; display: inline-block;"
              required
              [ngbTypeahead]="searchProducto"
              [editable]=false
              [resultFormatter]="resultFormatProducto"
              [inputFormatter]="inputFormatProducto"
              #producto="ngModel"
              >
              <div *ngIf="producto.invalid && producto.touched" class="alert alert-danger">
                <div *ngIf="producto.errors?.['required']">
                  El producto es requerido.
                </div>
              </div>
          </div>

          <span style="margin-right: 50px;"></span>  
            
          <div class="form-group">
              <label for="cantidad">Cantidad:</label>
              <input 
              name="cantidad" 
              placeholder="Cantidad" 
              class="form-control"  
              type="number"
              [(ngModel)]="pedido.cantidad" 
              style="width: 55%;" 
              min="1" 
              required 
              #cantidad="ngModel" 
              inputmode="numeric"
              >
              <div *ngIf="cantidad.invalid && (cantidad.dirty || cantidad.touched)" class="alert alert-danger">
                  <div *ngIf="cantidad.errors?.['required']">
                    La cantidad es requerida.
                  </div>
                </div>
            </div>
          </div>
          <br>
          <button (click)="goBack()" class="btn btn-danger">Atrás</button>
          <span style="margin-left: 10px;"></span>
          <button (click)="save()" class="btn btn-success" [disabled]="form.invalid">Guardar</button>
        </form>          
        
          
  `,    
  styles: ``
})
export class PedidoDetailComponent {
  pedido!: Pedido;
  searching: boolean = false;
  searchFailed: boolean = false;
  pedidoFechaPedido!: NgbDateStruct;
  pedidoFechaEntrega!: NgbDateStruct;
  showDatepicker: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private pedidoService: PedidoService,
    private productoService: ProductoService,
    private clienteService: ClienteService,
    private modalService: ModalService,
    private location: Location,
    private calendar: NgbCalendar
  ){}

  ngOnInit() {
    this.get();
  }

  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.pedido = <Pedido>{
        cliente:{},
        producto:{},
        estado: Estado.pendiente
      };
    }
    else {
      this.pedidoService.get(parseInt(id!)).subscribe(dataPackage => {
        this.pedido = <Pedido>dataPackage.data;
        const pedidoFechaPedidoAux = new Date(this.pedido.fechaPedido);
        const pedidoFechaEntregaAux = new Date(this.pedido.fechaEntrega);
        this.pedidoFechaPedido = {
          year: pedidoFechaPedidoAux.getFullYear(),
          month: pedidoFechaPedidoAux.getMonth() + 1,
          day: pedidoFechaPedidoAux.getDate()
        };
        this.pedidoFechaEntrega = {
          year: pedidoFechaEntregaAux.getFullYear(),
          month: pedidoFechaEntregaAux.getMonth() + 1,
          day: pedidoFechaEntregaAux.getDate()
        };
      });
    }
}

  goBack(): void {
    this.location.back();
  }

  searchCliente = (text$: Observable<string>): Observable<any[]> =>
  text$.pipe(
    debounceTime(300),
    distinctUntilChanged(),filter(term => term.length >= 3),
    tap(()=> (this.searching= true)) ,
    switchMap((term) =>
    this.clienteService
    .search(term)
    .pipe(
      map((response)=>
      {
        let clientes=<Cliente[]> response.data;
        return clientes;
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

  save(): void {
    this.pedido.fechaPedido = new Date(
      this.pedidoFechaPedido.year,
      this.pedidoFechaPedido.month - 1,
      this.pedidoFechaPedido.day
    );
    this.pedido.fechaEntrega = new Date(
      this.pedidoFechaEntrega.year,
      this.pedidoFechaEntrega.month - 1,
      this.pedidoFechaEntrega.day
    );

    this.pedidoService.save(this.pedido).subscribe(dataPackage => { this.pedido = <Pedido>dataPackage.data; this.goBack(); });
  }

  resultFormat(value:any){
    return value.razonSocial;
  }

  inputFormat(value:any){
    return value ? value.razonSocial : null;
  }

  onClienteSelect(cliente: any) {
  this.pedido.cliente = cliente;
  }

  searchProducto = (text$: Observable<string>): Observable<any[]> =>
  text$.pipe(
    debounceTime(300),
    distinctUntilChanged(),filter(term => term.length >= 3),
    tap(()=> (this.searching= true)) ,
    switchMap((term) =>
    this.productoService
    .search(term)
    .pipe(
      map((response)=>
      {
        let productos=<Producto[]> response.data;
        return productos;
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


  resultFormatProducto(value:any){
    return value.nombre;
  }

  inputFormatProducto(value:any){
    return value ? value.nombre : null;
  }


    calcularMinutos(planificacion: Planificacion){
      return new Date(planificacion.fin).getMilliseconds() - new Date(planificacion.inicio).getMilliseconds() / 60000;
    }


}




