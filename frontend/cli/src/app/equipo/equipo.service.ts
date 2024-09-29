import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Equipo } from './equipo';

@Injectable({
  providedIn: 'root'
})
export class EquipoService {

  private equiposUrl= "rest/equipos";
  constructor(private httpClient: HttpClient) { }


  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.equiposUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.equiposUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.equiposUrl);
  }

  save(equipo: Equipo): Observable<DataPackage>{
      return equipo.id ? this.httpClient.put<DataPackage>(`${this.equiposUrl}`, equipo) :
        this.httpClient.post<DataPackage>(`${this.equiposUrl}`, equipo);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.equiposUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.equiposUrl}/${id}`);
  }
}
