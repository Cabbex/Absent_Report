import { Injectable }               from '@angular/core';
import { Observable, of }           from "rxjs";
import { HttpClient, HttpHeaders }  from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(
      private http: HttpClient
  ) { }

  getNrAway(): Observable<number> {
      return this.http.get<number>("http://localhost:8080/absentreportbackend/api/nrAway");
  }

  getNrEmployeed(): Observable<number> {
      return this.http.get<number>("http://localhost:8080/absentreportbackend/api/nrEmployed");
  }
}
