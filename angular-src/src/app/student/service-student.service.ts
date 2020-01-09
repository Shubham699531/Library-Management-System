import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Transaction } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceStudentService {
  student:Student;

  constructor(private http:HttpClient) { 
    this.student= new Student();
  }

  registerStudent(newStudent:Student){
    return this.http.post("http://localhost:8880/front/registerLib", newStudent);
  }

  // viewListOfBooks(){
  //   return this.http.get<Book[]>("http://localhost:8880/front/search?something=" + "");
  // }

  searchForABook(something:string){
    return this.http.get<Book[]>("http://localhost:8880/front/search?something=" + something);
  }

  studentsTakingThisBook(bookId:number){
    return this.http.get<Student[]>("http://localhost:8880/front/getListOfStudents?bookId="+ +bookId);
  }

  whichBooksTakenByMe(studentId:number){
    return this.http.get<Book[]>("http://localhost:8880/front/search?something=" + studentId);
  }

  // listOfMyPreviousTransactions(){
  //   return this.http.get<Transaction[]>("http://localhost:8880/front/listAllTransactions");
  // }
}
