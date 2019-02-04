import { Injectable } from '@angular/core';
import { Employee } from './employee';
import { Observable, of }           from "rxjs";
import { HttpClient, HttpHeaders }  from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AwayService {

  constructor(
      private http: HttpClient
  ) { }

  getAway(): Observable<Employee[]> {
      return this.http.get<Employee[]>("http://localhost:8080/absentreportbackend/api/away");
  }


}
