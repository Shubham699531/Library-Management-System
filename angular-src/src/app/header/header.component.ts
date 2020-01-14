import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceStudentService } from '../student/service-student.service';
import { ServiceLibrarianService } from '../librarian/service-librarian.service';
import { LoginService } from '../login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedIn:boolean;

  constructor(private loginservice:LoginService, private studentService:ServiceStudentService, private librarianService:ServiceLibrarianService,
    private router:Router) { }

  ngOnInit() {
    this.loginservice.isLoggedIn=this.loggedIn;
  }

//   logout(){
//     if(this.studentService.student.userName!=undefined){
//       // this.studentHasLoggedIn=true;
//       this.studentService.student.userName=undefined;
//       this.router.navigate(['login']);
//     }
//     else if(this.librarianService.librarian.userName!=undefined){
//       // this.studentHasLoggedIn=true;
//       this.librarianService.librarian.userName=undefined;
//       this.router.navigate(['login']);
//     }
//   }

}
