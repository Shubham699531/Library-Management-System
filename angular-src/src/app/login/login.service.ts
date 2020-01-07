import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { CustomLoginObject } from '../models/CustomObjectForLogin.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  verifyLogin(user:User){
    return this.http.get<CustomLoginObject>("http://localhost:8880/front/validateLogin?userName=" +user.userName + "&password=" + user.password);
  }
}
