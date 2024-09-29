import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from './producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private productosUrl= "rest/productos";
  constructor(private httpClient: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.productosUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.productosUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.productosUrl);
  }

  save(producto: Producto): Observable<DataPackage>{
      return producto.id ? this.httpClient.put<DataPackage>(`${this.productosUrl}`, producto) :
        this.httpClient.post<DataPackage>(`${this.productosUrl}`, producto);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.productosUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.productosUrl}/${id}`);
  }
}
