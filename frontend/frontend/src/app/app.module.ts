import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from "@angular/common/http";

import { AppComponent } from './app.component';
import { ListComponent } from './components/listings/person/list/list.component';
import { ListItemComponent } from './components/listings/person/listitem/list-item.component';
import { GroupComponent } from './components/listings/group/group-list-item/group.component';
import { GroupListComponent } from './components/listings/group/group-list/group-list.component';

@NgModule({
  declarations: [
    AppComponent,
    ListComponent,
    ListItemComponent,
    GroupComponent,
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
