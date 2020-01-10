import { Component, OnInit } from '@angular/core';
import { ServiceStudentService } from '../service-student.service';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent implements OnInit {
  message:string;

  constructor(private studentService:ServiceStudentService) { }

  ngOnInit() {
    if(this.studentService.successfullyBorrowed==true){
      this.message = "You have successfully borrowed the book.";
    }
    else{
      alert("Something unexpected happened. Try again.");
    }
  }

}
