import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-librarian-header',
  templateUrl: './librarian-header.component.html',
  styleUrls: ['./librarian-header.component.css']
})
export class LibrarianHeaderComponent implements OnInit {
  isdashboardActive:boolean;
  isaddNewBookActive:boolean;
  isviewBooksActive:boolean;
  isviewTransactionsActive:boolean;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { }

  ngOnInit() {
    this.isdashboardActive=this.librarianService.isdashboardActive;
    this.isaddNewBookActive=this.librarianService.isaddNewBookActive;
    this.isviewBooksActive=this.librarianService.isviewBooksActive;
    this.isviewTransactionsActive=this.librarianService.isviewTransactionsActive;
  }

  logout(){
     if(this.librarianService.librarian.userName!=undefined){
      // this.studentHasLoggedIn=true;
      this.librarianService.librarian.userName=undefined;
      this.router.navigate(['login']);
    }
  }

}
