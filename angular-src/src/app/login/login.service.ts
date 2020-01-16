import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { CustomLoginObject } from '../models/CustomObjectForLogin.model';
import { throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Constants } from '../models/constant.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  isLoggedIn:boolean;

  constructor(private http:HttpClient) { }

  verifyLogin(user:User){
    return this.http.get<CustomLoginObject>(Constants.HOME_URL + "/validateLogin?userName=" 
    +user.userName + "&password=" + user.password).pipe(retry(1), catchError(this.errorHandler));
  }

  errorHandler(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) { //client side error
      errorMessage = `Error: ${error.error.message}`;
    }
    else { //server side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.error}`;
    }
    window.alert(errorMessage);
    return throwError(error.error)
  }
}
