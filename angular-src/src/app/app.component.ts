import { Component } from '@angular/core';
import { ServiceStudentService } from './student/service-student.service';
import { ServiceLibrarianService } from './librarian/service-librarian.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'lms';
  // studentHasLoggedIn:boolean=false;

  constructor(private studentService:ServiceStudentService, private librarianService:ServiceLibrarianService,
    private router:Router){

  }
  logout(){
    if(this.studentService.student!=null){
      // this.studentHasLoggedIn=true;
      this.studentService.student=null;
      this.router.navigate(['login']);
    }
    else{
      // this.studentHasLoggedIn=true;
      this.librarianService.librarian=null;
      this.router.navigate(['login']);
    }
  }
}
