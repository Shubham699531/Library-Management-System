import { Injectable } from '@angular/core';
import { Librarian } from '../models/librarian.model';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Transaction } from '../models/transaction.model';
import { catchError, retry } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Constants } from '../models/constant.model';

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
    return this.http.post(Constants.HOME_URL + "/registerLib", newlibrarian);
  }

  viewListOfBooks(){
    return this.http.get<Book[]>(Constants.HOME_URL + "/search?something=" + "");
  }

  studentsTakingThisBook(bookId:number){
    return this.http.get<Transaction[]>(Constants.HOME_URL + "/getListOfStudents?bookId="+ +bookId);
  }

  listAllTransactions(){
    return this.http.get<Transaction[]>(Constants.HOME_URL + "/listAllTransactions");
  }

  addBook(book:Book){
    return this.http.post(Constants.HOME_URL + "/add", book);
  }

  deleteBook(bookId:number){
    return this.http.get<boolean>(Constants.HOME_URL + "/delete?bookId=" + bookId).pipe(retry(1),catchError(this.errorHandler));
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
