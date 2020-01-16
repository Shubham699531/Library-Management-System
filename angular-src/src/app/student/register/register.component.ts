import { Component, OnInit } from '@angular/core';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';
import { Student } from 'src/app/models/student.model';

@Component({
  selector: 'app-register-student',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class StudentRegisterComponent implements OnInit {
  student:Student;
  confirmPassword:string;
  correct:boolean=false;
  maxDate: string;
  minDate: string;

  constructor(private studentService:ServiceStudentService, private router:Router) {
    this.student = new Student();
   }

  ngOnInit() {
    //Student should be atleast 5 years old and at most 60 years old
    this.maxDate = new Date("2015-01-01").toISOString().split('T')[0];
    this.minDate = new Date("1960-01-01").toISOString().split('T')[0];
  }

  addStudent(){
    if(this.student.password==this.confirmPassword){
      this.studentService.registerStudent(this.student).subscribe();
      this.router.navigate(['/login']);
    }
    else{
      // this.librarian = new Librarian();
      this.correct=true;
    }
  }

  goBackToLogin(){
    this.router.navigate(['login']);
  }

}
