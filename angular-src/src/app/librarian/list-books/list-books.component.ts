import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Book } from 'src/app/models/book.model';
import { Student } from 'src/app/models/student.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.css']
})
export class ListBooksComponent implements OnInit {
  books:Book[]=[];
  book:Book;
  students:Student[]=[];
  itemClicked:boolean=false;
  noStudentHasTakenThisBook:boolean=false;
  message:string;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { 
    this.book=new Book();
  }

  ngOnInit() {
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

  onItemClick(event, b){
    //this.itemClicked=true;
    this.book=b;
    this.librarianService.studentsTakingThisBook(+this.book.bookId).subscribe(data=>{this.students=data;
      if(this.students.length==0){
        this.noStudentHasTakenThisBook=true;
        this.message="No Student Has Taken This Book Yet!"

      }
      else{
        this.itemClicked=true;
      }
    })
  }

}
