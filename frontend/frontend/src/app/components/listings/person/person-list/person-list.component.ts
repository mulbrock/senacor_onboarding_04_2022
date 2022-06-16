import { Component, OnInit } from '@angular/core';
import {ReadPersonInterface} from "../../../../interfaces/read-person-interface";
import {Observable} from "rxjs";
import {GlobalStateService} from "../../../../services/global-state.service";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  public personList!: Observable<Array<ReadPersonInterface>>;

  constructor(private globalStateService: GlobalStateService) { }

  ngOnInit(): void {
    this.personList = this.globalStateService.currentPersonData;
  }

}
