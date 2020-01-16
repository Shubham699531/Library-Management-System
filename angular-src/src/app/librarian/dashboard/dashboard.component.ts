import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Librarian } from 'src/app/models/librarian.model';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MessageService } from 'src/app/message.service';

@Component({
  selector: 'app-dashboard-librarian',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class LibrarianDashboardComponent implements OnInit {
  librarian:Librarian;
  // message: any = {};
  // subscription: Subscription;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) {
      this.librarian = new Librarian();
   }

  ngOnInit() {
    this.librarianService.isdashboardActive=true; //true
    if(this.librarianService.librarian.userName!=undefined){
      this.librarian= this.librarianService.librarian;
      
    }
    else{
      this.router.navigate(['login']);
    }
    
  }
  ngOnDestroy(){
      // unsubscribe to ensure no memory leaks
    // this.subscription.unsubscribe();
    this.librarianService.isdashboardActive=false;
  }

  // routeToAddBook(){
  //   this.router.navigate(['add-book']);
  // }

  // routeToListBooks(){
  //   this.router.navigate(['list-books']);
  // }

  // viewAllTransactions(){
  //   this.router.navigate(['view-transactions']);
  // }

}
