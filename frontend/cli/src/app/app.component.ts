import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { RobotIconComponent } from "./guia/robot-icon.component";

@Component({
    selector: 'app-root',
    standalone: true,
    template: `
  <div class="background-image"></div>
  <div [class.loading]="loading">
    <div *ngIf="loading" class="loading-overlay">
      <div class="spinner-border text-primary" role="status">
        <span class="sr-only">Loading...</span>
      </div>
    </div>
  </div>
  <header class="barra bg-dark py-3">
  <div class="container">
    <div class="d-flex flex-column flex-md-row align-items-center">
      <a href="" style="text-decoration: none;">
        <h3 class="my-0 mr-md-3 font-weight-bold text-white">MRP-Lite</h3>
      </a>
      <span style="margin-left: 10px;"></span>
      <div ngbDropdown class="btn-group">
        <button class="btn btn-secondary bg-dark" id="dropdownMenuButton" ngbDropdownToggle>
          Cliente
        </button>
        <div ngbDropdownMenu aria-labelledby="dropdownMenuButton">
        <a class="dropdown-item disabled">Clientes</a>  
        <button ngbDropdownItem routerLink="/clientes">Listar</button>
          <button ngbDropdownItem routerLink="/clientes/new">Nuevo</button>
        </div>
      </div>
      <span style="margin-left: 5px;"></span>
      <div ngbDropdown class="btn-group">
        <button class="btn btn-secondary bg-dark" id="dropdownMenuButton" ngbDropdownToggle>
          Equipos
        </button>
        <div ngbDropdownMenu aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item disabled">Equipos</a>
          <button ngbDropdownItem routerLink="/equipos">Equipos</button>
          <button ngbDropdownItem routerLink="/equipos/new">Nuevo</button>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item disabled">Tipos de Equipo</a>
          <button ngbDropdownItem routerLink="/tipoequipos">Tipos de Equipo</button>
          <button ngbDropdownItem routerLink="/tipoequipos/new">Nuevo</button>
        </div>
      </div>
      <span style="margin-left: 5px;"></span>
      <div ngbDropdown class="btn-group">
        <button class="btn btn-secondary bg-dark" id="dropdownMenuButton" ngbDropdownToggle>
          Talleres
        </button>
        <div ngbDropdownMenu aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item disabled">Talleres</a>
          <button ngbDropdownItem routerLink="/talleres">Listar</button>
          <button ngbDropdownItem routerLink="/talleres/new">Nuevo</button>
          <button ngbDropdownItem routerLink="/gantt">Gantt</button>
        </div>
      </div>
      <span style="margin-left: 5px;"></span>
      <div ngbDropdown class="btn-group">
        <button class="btn btn-secondary bg-dark" id="dropdownMenuButton" ngbDropdownToggle>
          Productos
        </button>
        <div ngbDropdownMenu aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item disabled">Productos</a>
          <button ngbDropdownItem routerLink="/productos">Listar</button>
          <button ngbDropdownItem routerLink="/productos/new">Nuevo</button>
        </div>
      </div>
      <span style="margin-left: 5px;"></span>
      <div ngbDropdown class="btn-group">
        <button class="btn btn-secondary bg-dark" id="dropdownMenuButton" ngbDropdownToggle>
          Pedidos
        </button>
        <div ngbDropdownMenu aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item disabled">Pedidos</a>
          <button ngbDropdownItem routerLink="/pedidos">Listar</button>
          <button ngbDropdownItem routerLink="/pedidos/new">Nuevo</button>
        </div>
      </div>
    </div>
  </div>
</header>
<app-robot-icon></app-robot-icon>
<div class="container white-div">
    <router-outlet></router-outlet>
</div> 
  `,
    styleUrls: ['./app.component.css'],
    imports: [RouterOutlet, CommonModule, AppComponent, NgbDropdownModule, RouterModule, RobotIconComponent]
})
export class AppComponent {
  constructor(private router: Router) { }
  loading: boolean = false;

  setLoading(isLoading: boolean) {
    this.loading = isLoading;
  }

}
