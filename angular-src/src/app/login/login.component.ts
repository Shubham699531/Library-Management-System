import { Component, OnInit } from '@angular/core';
import { LoginService } from './login.service';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { CustomLoginObject } from '../models/CustomObjectForLogin.model';
import { Student } from '../models/student.model';
import { Librarian } from '../models/librarian.model';
import { ServiceStudentService } from '../student/service-student.service';
import { ServiceLibrarianService } from '../librarian/service-librarian.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user:User;
  login:CustomLoginObject;

  constructor(private service:LoginService, private router:Router, private studentService:ServiceStudentService
    , private librarianService:ServiceLibrarianService) { 
    this.user = new User();
    this.login= new CustomLoginObject();
  }

  ngOnInit() {
  }

  verifyLogin(){
    this.router.navigate(['/student-dashboard']);
    //this.service.verifyLogin(this.user).subscribe(data=>{this.login=data;
      // if(this.login.librarian==null){
      //   this.studentService.student = this.login.student;
      //   this.router.navigate(['app-dashboard-student']);
      // }
      // else{
      //   this.librarianService.librarian = this.login.librarian;
      //   this.router.navigate(['app-dashboard-librarian']);
      // }
      
   // })
  }

}
