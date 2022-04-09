import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {PersonListComponent} from './components/person-list/person-list.component';
import {PersonDetailsComponent} from './components/person-details/person-details.component';
import {PersonCreateComponent} from './components/person-create/person-create.component';
import {GroupListComponent} from "./components/group-list/group-list.component";
import {GroupDetailsComponent} from "./components/group-details/group-details.component";

const routes: Routes = [
  {path: '', redirectTo: 'persons', pathMatch: 'full'},

  {path: 'persons', component: PersonListComponent},
  {path: 'persons/:id', component: PersonDetailsComponent},
  {path: 'person-create', component: PersonCreateComponent},

  {path: 'groups', component: GroupListComponent},
  {path: 'groups/:id', component: GroupDetailsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
