import { Component, OnInit } from '@angular/core';
import { ServiceStudentService } from '../service-student.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-header',
  templateUrl: './student-header.component.html',
  styleUrls: ['./student-header.component.css']
})
export class StudentHeaderComponent implements OnInit {

  constructor(private studentService:ServiceStudentService, private router:Router) { }

  ngOnInit() {
  }

  logout(){
    if(this.studentService.student.userName!=undefined){
      // this.studentHasLoggedIn=true;
      this.studentService.student.userName=undefined;
      this.router.navigate(['login']);
    }
    // else if(this.librarianService.librarian.userName!=undefined){
    //   // this.studentHasLoggedIn=true;
    //   this.librarianService.librarian.userName=undefined;
    //   this.router.navigate(['login']);
    // }
  }
}
