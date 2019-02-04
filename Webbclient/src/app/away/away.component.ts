import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { Observable, of } from "rxjs";
import { AwayService } from '../away.service';

@Component({
  selector: 'app-away',
  templateUrl: './away.component.html',
  styleUrls: ['./away.component.css']
})
export class AwayComponent implements OnInit {

  employeesAway: Employee[];

  constructor(
      private awayService: AwayService
  ) { }

  ngOnInit() {
      this.awayService.getAway().subscribe(response => this.employeesAway = (response as Employee[]));
  }

}
