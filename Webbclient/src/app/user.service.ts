import { Injectable }               from '@angular/core';
import { User }                     from "./user";
import { Observable, of }           from "rxjs";
import { HttpClient, HttpHeaders }  from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(
      private http: HttpClient
  ) {}

  authenticateUser(username: string, password: string) : Observable<User> {
      const passingBody = {
          username: username,
          password: password
      };

      return this.http.post<User>("http://localhost:8080/absentreportbackend/api/authenticate",
       passingBody, httpOptions);


  }
}
