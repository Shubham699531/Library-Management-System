import { Component, OnInit } from '@angular/core';
import { ServiceLibrarianService } from '../service-librarian.service';
import { Router } from '@angular/router';
import { Librarian } from 'src/app/models/librarian.model';

@Component({
  selector: 'app-register-librarian',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class LibrarianRegisterComponent implements OnInit {
  librarian:Librarian;
  confirmPassword:string;
  correct:boolean=false;

  constructor(private librarianService:ServiceLibrarianService, private router:Router) { 
    this.librarian = new Librarian();
  }

  ngOnInit() {
  }

  addLibrarian(){
    if(this.librarian.password==this.confirmPassword){
      this.librarianService.registerLibrarian(this.librarian).subscribe();
      this.router.navigate(['/login']);
    }
    else{
      this.librarian = new Librarian();
      this.correct=true;
    }
  }

}
