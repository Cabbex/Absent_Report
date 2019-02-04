import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {
  user: User;


  constructor(
      private userService: UserService,
      private router: Router) { }

  ngOnInit() {
      this.user = {
          userID: 1,
          username: "Casper",
          isAuthenticated: false
      }
  }

  authenticateUser(username: string, password: string): void {
    this.userService.authenticateUser(username, password)
    .subscribe(response => this.user = {userID: response.userID,
         username: username,
         isAuthenticated: response.userID > 0 ? true : false });

    (async () => {
     // Do something before delay
     console.log('delay started')

     await this.delay(1000);
     this.loginUser();
    })();


  }

  loginUser(): void {
      if(this.user.isAuthenticated){
          this.router.navigate(["/dashboard"]);
      }
  }

   delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }
}
