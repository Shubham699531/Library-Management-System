import { Injectable } from '@angular/core';
import { Librarian } from '../models/librarian.model';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Student } from '../models/student.model';
import { Transaction } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceLibrarianService {
  librarian:Librarian;

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
    return this.http.get<Student[]>("http://localhost:8880/front/getListOfStudents?bookId="+ +bookId);
  }

  listAllTransactions(){
    return this.http.get<Transaction[]>("http://localhost:8880/front/listAllTransactions");
  }
}
