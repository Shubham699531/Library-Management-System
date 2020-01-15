import { Injectable } from '@angular/core';
import { Librarian } from '../models/librarian.model';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Student } from '../models/student.model';
import { Transaction } from '../models/transaction.model';
import { catchError, retry } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceLibrarianService {
  librarian:Librarian;
  // isLibrarianLoggedIn:boolean=false;
  isdashboardActive:boolean=false;
  isaddNewBookActive:boolean=false;
  isviewBooksActive:boolean=false;
  isviewTransactionsActive:boolean=false;

  constructor(private http:HttpClient) { 
    this.librarian = new Librarian();
  }

  registerLibrarian(newlibrarian:Librarian){
    return this.http.post("http://localhost:8880/front/registerLib", newlibrarian);
  }

  viewListOfBooks(){
    return this.http.get<Book[]>("http://localhost:8880/front/search?something=" + "");
  }

  studentsTakingThisBook(bookId:number){
    return this.http.get<Transaction[]>("http://localhost:8880/front/getListOfStudents?bookId="+ +bookId);
  }

  listAllTransactions(){
    return this.http.get<Transaction[]>("http://localhost:8880/front/listAllTransactions");
  }

  addBook(book:Book){
    return this.http.post("http://localhost:8880/front/add", book);
  }

  deleteBook(bookId:number){
    return this.http.get<boolean>("http://localhost:8880/front/delete?bookId=" + bookId).pipe(retry(1),catchError(this.errorHandler));
  }
 
  errorHandler(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) { //client side error
      errorMessage = `Error: ${error.error.message}`;
    }
    else { //server side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.error}`;
    }
    window.alert(errorMessage);
    return throwError(error.error)
  }
}
