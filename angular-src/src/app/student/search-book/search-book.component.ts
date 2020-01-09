import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit {
  books:Book[]=[];
  searchTerm:string;
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
    this.studentService.borrowABook(+this.book.bookId).subscribe(data=>{this.transaction=data;
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
