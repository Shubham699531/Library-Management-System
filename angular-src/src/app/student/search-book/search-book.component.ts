import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';
import { CustomPopularityObject } from 'src/app/models/CustomPopularityObject.model';

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit {
  books: Book[] = [];
  searchTerm: string="";
  message: string;
  book: Book;
  transaction: Transaction;
  isAStudent: boolean = false;
  errorMsg: string;
  popularityObjects:CustomPopularityObject[]=[];

  constructor(private studentService: ServiceStudentService, private router: Router) {
    this.book = new Book();
  }

  ngOnInit() {
    this.studentService.isSearchBookActive=true;
    this.studentService.viewWhichBookIsPopular().subscribe(data=>{this.popularityObjects=data});
    this.studentService.searchForABook("").subscribe(data=>{this.books=data});
   }

   ngOnDestroy(){
     this.studentService.isSearchBookActive=false;
   }

  onButtonClick(event, b) {
    if (this.studentService.student.userName!=undefined) {
      this.book = b;
      this.studentService.borrowABook(+this.book.bookId).subscribe(data => {
      this.transaction = data;
        if (this.transaction != null) {
          this.studentService.currentTransaction = this.transaction;
          this.studentService.successfullyBorrowed = true;
          this.router.navigate(['success-page']);
        }
        else{
          this.router.navigate(['success-page']);
        }
      }, error=>{this.errorMsg=error;});
    }
    else {
      this.router.navigate(['login']);
    }

  }

  search() {
    if (this.searchTerm=="" || this.searchTerm.length >= 3) {
      this.message="";
      this.studentService.searchForABook(this.searchTerm).subscribe(data => {
        if(this.studentService.student.userName!=undefined){
          this.isAStudent = true;
        }  
      this.books = data;
      })
    }
    else {
      this.message = "Please enter atleast three letters to customize your search."
    }

  }
}
