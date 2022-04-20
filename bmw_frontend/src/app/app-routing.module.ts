import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Persons } from "./person-list/persons.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import {PersonDetailComponent} from "./person-detail/person-detail.component";

const routes: Routes = [
  {
    path: '', redirectTo: '/dashboard', pathMatch: 'full'
  },
  {
    path: 'dashboard', component: DashboardComponent
  },
  {
    path: 'people', component: Persons
  },
  {
    path: 'detail/:id', component: PersonDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
