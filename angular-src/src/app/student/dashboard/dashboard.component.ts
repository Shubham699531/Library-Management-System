import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/models/student.model';
import { ServiceStudentService } from '../service-student.service';
import { Book } from 'src/app/models/book.model';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-dashboard-student',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {
  student:Student;
  books:Book[]=[];
  transactions: Transaction[] = [];
  noBooksFound:boolean=true;
  message: string;
  book: Book;
  transaction: Transaction;
  bookAlreadyReturned:boolean=false;
  // returnButtonClicked:boolean=false;
  returnDate:string;
  i:number;
  // maxDate: string;
  // minDate: string;

  constructor(private studentService:ServiceStudentService, private router:Router) { 
    this.student=new Student();
  }

  ngOnInit() {

  //   if(performance.navigation.type == 2){
  //     location.reload(true);
  //  }

    // this.maxDate = new Date((new Date().getTime()+(15*24*3600*1000))).toISOString().split('T')[0];
    // this.minDate = new Date().toISOString().split('T')[0];

    if(this.studentService.student.userName!=undefined){
       this.student = this.studentService.student;
      this.studentService.whichBooksTakenByMe().subscribe(data=>{this.transactions=data;
      if(this.transactions.length!=0){
        this.noBooksFound=false;
      }
      })
    }
    else{
      this.router.navigate(['login']);
    }
    
  }

  onButtonClick(event, b) {
    if (this.studentService.student.userName!=undefined) {
      this.book = b;
      // this.returnButtonClicked=true;
        this.returnDate = "12-02-2020";
      //  console.log(this.returnDate);
      this.studentService.returnABook(+this.student.studentId, this.book.bookId, this.returnDate).subscribe(data => {
      this.transaction = data;
        if (this.transaction != null) {
          this.studentService.currentTransaction=this.transaction;
          this.studentService.successfullyReturned = true;
          this.router.navigate(['success-page']);
        }
        else{
          this.router.navigate(['success-page']);
        }
      });
    }
    else {
      this.router.navigate(['login']);
    }

  }

}
