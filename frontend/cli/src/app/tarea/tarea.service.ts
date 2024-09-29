import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Tarea } from './tarea';

@Injectable({
  providedIn: 'root'
})
export class TareaService {

  private tareasUrl = "rest/tareas";
  constructor(private httpClient: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tareasUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tareasUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.tareasUrl);
  }

  save(Tarea: Tarea): Observable<DataPackage>{
      return Tarea.id ? this.httpClient.put<DataPackage>(`${this.tareasUrl}`, Tarea) :
        this.httpClient.post<DataPackage>(`${this.tareasUrl}`, Tarea);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.tareasUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.tareasUrl}/${id}`);
  }

}
