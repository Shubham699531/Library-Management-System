import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceStudentService {
  student:Student;

  constructor() { 
    this.student= new Student();
  }
}
