import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from "@angular/common/http";
import { ReactiveFormsModule } from "@angular/forms";
import { AppRoutingModule} from './app-routing.module';

import { AppComponent } from './app.component';
import { PersonListComponent } from './components/listings/person/person-list/person-list.component';
import { PersonListItemComponent } from './components/listings/person/listitem/person-list-item.component';
import { GroupListItemComponent } from './components/listings/group/group-list-item/group-list-item.component';
import { GroupListComponent } from './components/listings/group/group-list/group-list.component';
import { EditComponent } from './components/edit/edit.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonListComponent,
    PersonListItemComponent,
    GroupListItemComponent,
    GroupListComponent,
    EditComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
