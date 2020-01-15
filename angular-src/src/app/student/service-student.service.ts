import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Transaction } from '../models/transaction.model';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { CustomPopularityObject } from '../models/CustomPopularityObject.model';
import { Constants } from '../models/constant.model';


@Injectable({
  providedIn: 'root'
})
export class ServiceStudentService {
  student:Student;
  successfullyBorrowed:boolean=false;
  successfullyReturned:boolean=false;
  currentTransaction:Transaction;
  isDashboardActive:boolean=false;
  isSearchBookActive:boolean=false;

  constructor(private http:HttpClient) { 
    this.student= new Student();
    this.currentTransaction= new Transaction();
  }

  registerStudent(newStudent:Student){
    return this.http.post(Constants.HOME_URL + "/register", newStudent);
  }

  searchForABook(something:string){
    return this.http.get<Book[]>(Constants.HOME_URL + "/search?something=" + something).pipe(retry(1), catchError(this.errorHandler));
  }

  borrowABook(bookId:number):Observable<Transaction>{
    return this.http.get<Transaction>(Constants.HOME_URL + "/borrow?bookId=" + bookId +
     "&studentId=" + this.student.studentId ).pipe(retry(1), catchError(this.errorHandler));
  }

  returnABook(studentId: number, bookId: number, returnDate: string){
    return this.http.get<Transaction>(Constants.HOME_URL + "/return?studentId=" + studentId + "&bookId=" + bookId +  "&returnDate=" + returnDate);
  }

  findBooksBasedOnInterest(interest:string){
    return this.http.get<Book[]>(Constants.HOME_URL + "/getBooksByInterests?interest=" + interest);
  }

  studentsTakingThisBook(bookId:number){
    return this.http.get<Student[]>(Constants.HOME_URL + "/getListOfStudents?bookId="+ +bookId);
  }

  whichBooksTakenByMe(){
    return this.http.get<Transaction[]>(Constants.HOME_URL + "/getListOfBooks?studentId=" + this.student.studentId);
  }

  viewWhichBookIsPopular(){
    return this.http.get<CustomPopularityObject[]>(Constants.HOME_URL + "/viewPopularity");
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
