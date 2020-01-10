import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/models/student.model';
import { ServiceStudentService } from '../service-student.service';
import { Book } from 'src/app/models/book.model';

@Component({
  selector: 'app-dashboard-student',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {
  student:Student;
  books:Book[]=[];
  noBooksFound:boolean=true;

  constructor(private studentService:ServiceStudentService) { 
    this.student=new Student();
  }

  ngOnInit() {
    this.student = this.studentService.student;
    this.studentService.findBooksBasedOnInterest(this.student.interests).subscribe(data=>{this.books=data;
      if(this.books.length!=0){
        this.noBooksFound=false;
      }
      });
  }

}
