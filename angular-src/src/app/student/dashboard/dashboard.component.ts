import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/models/student.model';
import { ServiceStudentService } from '../service-student.service';

@Component({
  selector: 'app-dashboard-student',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {
  student:Student;

  constructor(private studentService:ServiceStudentService) { 
    this.student=new Student();
  }

  ngOnInit() {
    this.student = this.studentService.student;
  }

}
