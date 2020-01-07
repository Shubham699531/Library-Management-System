import { Injectable } from '@angular/core';
import { Librarian } from '../models/librarian.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceLibrarianService {
  librarian:Librarian;

  constructor() { 
    this.librarian = new Librarian();
  }
}
