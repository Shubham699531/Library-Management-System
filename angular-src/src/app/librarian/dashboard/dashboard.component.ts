import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Librarian } from 'src/app/models/librarian.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-librarian',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class LibrarianDashboardComponent implements OnInit {
  librarian:Librarian;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) {
      this.librarian = new Librarian();
   }

  ngOnInit() {
    if(this.librarianService.librarian.userName!=undefined){
      this.librarian= this.librarianService.librarian;
    }
    else{
      this.router.navigate(['login']);
    }
    
  }


}
