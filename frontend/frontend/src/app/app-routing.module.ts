import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {EditComponent} from "./components/edit/edit.component";
import {PersonListComponent} from "./components/listings/person/person-list/person-list.component";

const routes: Routes = [
  { path: "edit", component: EditComponent},
  { path: "**", component: PersonListComponent}
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
