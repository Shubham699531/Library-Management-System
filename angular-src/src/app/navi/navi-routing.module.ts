import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { StudentDashboardComponent } from '../student/dashboard/dashboard.component';
import { LibrarianDashboardComponent } from '../librarian/dashboard/dashboard.component';
import { CommonModule } from '@angular/common';

const routes: Routes = [
  {path:"login", component:LoginComponent},
  {path:"student-dashboard", component:StudentDashboardComponent},
  {path:"librarian-dashboard", component:LibrarianDashboardComponent},
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
