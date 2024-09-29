import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
  providedIn: 'root'
})
export class GanttService {

  private ganttUrl= "rest/gantt";
  constructor(private httpClient: HttpClient) { }


  getGanttObjects(id: number,from: Date, to: Date):Observable<DataPackage>{
    return this.httpClient.get<DataPackage>(`${this.ganttUrl}/object/${id}/${from}/${to}`);
  }

}
