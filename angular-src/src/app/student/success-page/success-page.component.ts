import { Component, OnInit } from '@angular/core';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent implements OnInit {
  message:string;
  successfulTransaction: Transaction;
  bookBorrowedOrReturned:boolean=false;

  constructor(private studentService:ServiceStudentService, private router:Router) {
    this.successfulTransaction=new Transaction();
   }

  ngOnInit() {
    if(this.studentService.student.userName!=undefined){
      if(this.studentService.successfullyBorrowed==true){
        this.bookBorrowedOrReturned=true;
        this.successfulTransaction=this.studentService.currentTransaction;
        this.message = "You have successfully borrowed the book.";
      }
      else if(this.studentService.successfullyReturned==true){
        this.bookBorrowedOrReturned=true;
        this.successfulTransaction=this.studentService.currentTransaction;
        this.message = "You have successfully returned the book."
      }
      else{
        alert("Something unexpected happened. Try again.");
      }
    }
    else{
      this.router.navigate(['login']);
    }
    
  }

  goBack(){
    this.router.navigate(['student-dashboard']);
  }

}
