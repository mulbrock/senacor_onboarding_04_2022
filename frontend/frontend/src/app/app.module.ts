import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from "@angular/common/http";

import { AppComponent } from './app.component';
import { PersonListComponent } from './components/listings/person/person-list/person-list.component';
import { PersonListItemComponent } from './components/listings/person/listitem/person-list-item.component';
import { GroupListItemComponent } from './components/listings/group/group-list-item/group-list-item.component';
import { GroupListComponent } from './components/listings/group/group-list/group-list.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonListComponent,
    PersonListItemComponent,
    GroupListItemComponent,
    GroupListComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
