import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { Pedido } from './pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  private pedidosUrl= "rest/pedidos";
  constructor(private httpClient: HttpClient) { }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.pedidosUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.pedidosUrl);
  }

  save(pedido: Pedido): Observable<DataPackage>{
      return pedido.id ? this.httpClient.put<DataPackage>(`${this.pedidosUrl}`, pedido) :
        this.httpClient.post<DataPackage>(`${this.pedidosUrl}`, pedido);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.pedidosUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.pedidosUrl}/${id}`);
  }

}
