import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../dashboard.service';
import { Observable, of }   from "rxjs";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  nrAway: number;
  nrEmployed: number;

  constructor(
      private dashboardService: DashboardService
  ) { }

  ngOnInit() {
      this.dashboardService.getNrAway().subscribe(response => this.nrAway = response.nrOf);
      this.dashboardService.getNrEmployeed().subscribe(response => this.nrEmployed = response.nrOf);
  }
}
