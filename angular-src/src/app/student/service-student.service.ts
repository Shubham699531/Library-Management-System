import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Transaction } from '../models/transaction.model';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { CustomPopularityObject } from '../models/CustomPopularityObject.model';


@Injectable({
  providedIn: 'root'
})
export class ServiceStudentService {
  student:Student;
  successfullyBorrowed:boolean=false;
  successfullyReturned:boolean=false;
  currentTransaction:Transaction;
  isStudentLoggedIn:boolean=false;

  constructor(private http:HttpClient) { 
    this.student= new Student();
    this.currentTransaction= new Transaction();
  }

  registerStudent(newStudent:Student){
    return this.http.post("http://localhost:8880/front/register", newStudent);
  }

  searchForABook(something:string){
    return this.http.get<Book[]>("http://localhost:8880/front/search?something=" + something).pipe(retry(1), catchError(this.errorHandler));
  }

  borrowABook(bookId:number):Observable<Transaction>{
    return this.http.get<Transaction>("http://localhost:8880/front/borrow?bookId=" + bookId +
     "&studentId=" + this.student.studentId ).pipe(retry(1), catchError(this.errorHandler));
  }

  returnABook(studentId: number, bookId: number, returnDate: string){
    return this.http.get<Transaction>("http://localhost:8880/front/return?studentId=" + studentId + "&bookId=" + bookId +  "&returnDate=" + returnDate);
  }

  findBooksBasedOnInterest(interest:string){
    return this.http.get<Book[]>("http://localhost:8880/front/getBooksByInterests?interest=" + interest);
  }

  studentsTakingThisBook(bookId:number){
    return this.http.get<Student[]>("http://localhost:8880/front/getListOfStudents?bookId="+ +bookId);
  }

  whichBooksTakenByMe(){
    return this.http.get<Transaction[]>("http://localhost:8880/front/getListOfBooks?studentId=" + this.student.studentId);
  }

  viewWhichBookIsPopular(){
    return this.http.get<CustomPopularityObject[]>("http://localhost:8880/front/viewPopularity");
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
