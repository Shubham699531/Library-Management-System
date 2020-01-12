import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Transaction } from 'src/app/models/transaction.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions:Transaction[]=[];

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { }

  ngOnInit() {
    if(this.librarianService.librarian.userName!=undefined){
      // console.log(this.librarianService.librarian);
      this.librarianService.listAllTransactions().subscribe(data=>{
        this.transactions=data;
      });
    }
    else{
      this.router.navigate(['login']);
    }
    
  }

  goBack(){
    this.router.navigate(['librarian-dashboard']);
  }

}
