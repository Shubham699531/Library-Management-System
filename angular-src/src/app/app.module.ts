import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { StudentRegisterComponent } from './student/register/register.component';
import { StudentDashboardComponent } from './student/dashboard/dashboard.component';
import { SearchBookComponent } from './student/search-book/search-book.component';
import { SuccessPageComponent } from './student/success-page/success-page.component';
import { ListBooksComponent } from './librarian/list-books/list-books.component';
import { TransactionsComponent } from './librarian/transactions/transactions.component';
import { NaviRoutingModule } from './navi/navi-routing.module';
import { LibrarianRegisterComponent } from './librarian/register/register.component';
import { LibrarianDashboardComponent } from './librarian/dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    StudentRegisterComponent,
    LibrarianRegisterComponent,
    LibrarianDashboardComponent,
    StudentDashboardComponent,
    SearchBookComponent,
    SuccessPageComponent,
    ListBooksComponent,
    TransactionsComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpClientModule, NaviRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
