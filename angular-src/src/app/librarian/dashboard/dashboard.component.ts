import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Librarian } from 'src/app/models/librarian.model';

@Component({
  selector: 'app-dashboard-librarian',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class LibrarianDashboardComponent implements OnInit {
  librarian:Librarian;

  constructor(private librarianService:ServiceLibrarianService) {
      this.librarian = new Librarian();
   }

  ngOnInit() {
    this.librarian= this.librarianService.librarian;
  }


}
