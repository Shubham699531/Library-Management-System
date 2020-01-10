import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';
import { ServiceLibrarianService } from 'src/app/librarian/service-librarian.service';

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit {
  books:Book[]=[];
  searchTerm:string="";
  message:string;
  book:Book;
  transaction:Transaction;

  constructor(private studentService:ServiceStudentService, private router:Router) {
    this.book = new Book();
   }

  ngOnInit() {
  }

  onButtonClick(event,b){
    this.book=b;
    console.log(this.book.bookName);
    this.studentService.borrowABook(+this.book.bookId).subscribe(data=>{this.transaction=data;
      if(this.transaction!=null){
        this.studentService.successfullyBorrowed=true;
        this.router.navigate(['success-page']);
      }  
    });
  }

  search(){
    if(this.searchTerm.length==0 || this.searchTerm.length>=3){
      this.studentService.searchForABook(this.searchTerm).subscribe(data=>{this.books=data;
      })
    }
    else{
      this.message="Please enter atleast three letters to customize your search."
    }
    
  }

}
