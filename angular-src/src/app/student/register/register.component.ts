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

  constructor(private studentService:ServiceStudentService, private router:Router) {
    this.student = new Student();
   }

  ngOnInit() {
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

}
