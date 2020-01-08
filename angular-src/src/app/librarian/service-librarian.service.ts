import { Injectable } from '@angular/core';
import { Librarian } from '../models/librarian.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ServiceLibrarianService {
  librarian:Librarian;

  constructor(private http:HttpClient) { 
    this.librarian = new Librarian();
  }

  registerLibrarian(newlibrarian:Librarian){
    return this.http.post("http://localhost:8880/front/registerLib", newlibrarian);
  }
}
