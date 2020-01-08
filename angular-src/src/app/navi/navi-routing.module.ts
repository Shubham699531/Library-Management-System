import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { StudentDashboardComponent } from '../student/dashboard/dashboard.component';
import { LibrarianDashboardComponent } from '../librarian/dashboard/dashboard.component';
import { CommonModule } from '@angular/common';
import { LibrarianRegisterComponent } from '../librarian/register/register.component';
import { StudentRegisterComponent } from '../student/register/register.component';

const routes: Routes = [
  {path:"login", component:LoginComponent},
  {path:"student-dashboard", component:StudentDashboardComponent},
  {path:"student-register", component:StudentRegisterComponent},
  {path:"librarian-dashboard", component:LibrarianDashboardComponent},
  {path:"librarian-register", component:LibrarianRegisterComponent},
  // {path:"past-trips", component:PastTripsComponent},
  // {path:"logout", component:LogoutComponent},
  // {path:"about", component:AboutComponent},
  {path: "", redirectTo:"/login", pathMatch:"full"},
  {path:"**", redirectTo:"/login", pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule]
})
export class NaviRoutingModule { }
