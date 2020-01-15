import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Router } from '@angular/router';
import { Book } from 'src/app/models/book.model';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {
  book:Book;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { 
    this.book = new Book();
  }

  ngOnInit() {
    this.librarianService.isaddNewBookActive=true;
    if(this.librarianService.librarian.userName==undefined){
      this.router.navigate(['login']);
    }
  }

  ngOnDestroy(){
    this.librarianService.isaddNewBookActive=false;
  }

  addBook(){
    this.librarianService.addBook(this.book).subscribe();
    this.router.navigate(['list-books']);
  }

}
