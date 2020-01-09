import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions:Transaction[]=[];

  constructor(private librarianService:ServiceLibrarianService) { }

  ngOnInit() {
    this.librarianService.listAllTransactions().subscribe(data=>{
      this.transactions=data;
    });
  }

}
