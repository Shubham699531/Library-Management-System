import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Book } from 'src/app/models/book.model';
import { Student } from 'src/app/models/student.model';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.css']
})
export class ListBooksComponent implements OnInit {
  books:Book[]=[];
  book:Book;
  // students:Student[]=[];
  transactions:Transaction[]=[];
  itemClicked:boolean=false;
  noStudentHasTakenThisBook:boolean=false;
  message:string;
  bookDeleted:boolean;
  errorMsg: string;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { 
    this.book=new Book();
  }

  ngOnInit() {
    this.librarianService.isviewBooksActive=true;
    if(this.librarianService.librarian.userName!=undefined){ 
      this.librarianService.viewListOfBooks().subscribe(data=>{this.books=data;
        if(this.books.length==0){
          alert("No books yet!");
        }
      })
    }
    else{
      this.router.navigate(['login']);
    }
    
  }

  ngOnDestroy(){
    this.librarianService.isviewBooksActive=false;
  }

  viewWhoHasTakenThisBook(event, b){
    //this.itemClicked=true;
    this.book=b;
    this.librarianService.studentsTakingThisBook(+this.book.bookId).subscribe(data=>{this.transactions=data;
      if(this.transactions.length==0){
        this.noStudentHasTakenThisBook=true;
        this.message="No Student Has Taken This Book Yet!"

      }
      else{
        this.itemClicked=true;
      }
    })
  }

  deleteButtonClicked(event, b){
    this.book=b;
    if(window.confirm("Are you sure you want to remove this book?")){
      this.librarianService.deleteBook(+this.book.bookId).subscribe(data=>{this.bookDeleted=data;
      if(this.bookDeleted){
        window.alert("Book successfully removed.");
      }}, error=>{this.errorMsg=error})
    }
  }

  goBack(){
    this.router.navigate(['librarian-dashboard']);
  }

}
