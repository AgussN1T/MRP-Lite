import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { TipoEquipo } from './TipoEquipo';

@Injectable({
  providedIn: 'root'
})
export class TipoEquipoService {

  private tipoEquiposUrl= "rest/tipoequipos";
  constructor(private httpClient: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tipoEquiposUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tipoEquiposUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.tipoEquiposUrl);
  }

  save(tipoEquipo: TipoEquipo): Observable<DataPackage>{
      return tipoEquipo.id ? this.httpClient.put<DataPackage>(`${this.tipoEquiposUrl}`, tipoEquipo) :
        this.httpClient.post<DataPackage>(`${this.tipoEquiposUrl}`, tipoEquipo);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tipoEquiposUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.tipoEquiposUrl}/${id}`);
  }


}
