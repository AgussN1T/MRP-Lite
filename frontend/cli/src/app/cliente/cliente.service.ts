import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { Cliente } from './cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private clientesUrl= "rest/clientes";
  constructor(private httpClient: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.clientesUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.clientesUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.clientesUrl);
  }

  save(cliente: Cliente): Observable<DataPackage>{
      return cliente.id ? this.httpClient.put<DataPackage>(`${this.clientesUrl}`, cliente) :
        this.httpClient.post<DataPackage>(`${this.clientesUrl}`, cliente);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.clientesUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.clientesUrl}/${id}`);
  }

}
