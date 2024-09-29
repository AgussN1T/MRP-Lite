import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
  providedIn: 'root'
})
export class PlanificacionService {

  private pedidosUrl= "rest/planificador";
  constructor(private httpClient: HttpClient) { }


  planificar(nombre: string): Observable<DataPackage> {
    console.log(nombre);
    return this.httpClient.get<DataPackage>(`${this.pedidosUrl}/productos/planificarProducto/${nombre}`);
  }
}
