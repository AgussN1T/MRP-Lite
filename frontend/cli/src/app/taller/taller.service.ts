import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { Taller } from './taller';

@Injectable({
  providedIn: 'root'
})
export class TallerService {

  private talleresUrl = "rest/talleres";
  constructor(private httpClient: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.talleresUrl}/search/${searchTerm}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.talleresUrl}/page?page=${page - 1}&size=${size}`);
  }

  all(): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(this.talleresUrl);
  }

  save(Taller: Taller): Observable<DataPackage>{
      return Taller.id ? this.httpClient.put<DataPackage>(`${this.talleresUrl}`, Taller) :
        this.httpClient.post<DataPackage>(`${this.talleresUrl}`, Taller);
  }

  get(id: number): Observable<DataPackage> {
    return this.httpClient.get<DataPackage>(`${this.talleresUrl}/id/${id}`);
  }

  remove(id: number){
    return this.httpClient.delete<DataPackage>(`${this.talleresUrl}/${id}`);
  }


}
