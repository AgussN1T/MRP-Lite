
import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PlanificadorService {

  private planificadorUrl= "rest/planificador";
  constructor(private httpClient: HttpClient) { }

  planificarTodo(): Observable<DataPackage>{
    return this.httpClient.get<DataPackage>(`${this.planificadorUrl}/pedidos/planificarTodoTardia`);
  }

  planificarTardia(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.planificadorUrl}/pedidos/planificarPedidoTardia/${id}`);
  }

  planificarPronta(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.planificadorUrl}/pedidos/planificarPedidoPronta/${id}`);
  }
}
