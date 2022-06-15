import { Component, OnInit } from '@angular/core';
import {PersonInterface} from "../../../interfaces/person-interface";
import {Observable} from "rxjs";
import {GlobalStateService} from "../../../services/global-state.service";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  public personList!: Observable<Array<PersonInterface>>;

  constructor(private globalStateService: GlobalStateService) { }

  ngOnInit(): void {
    this.personList = this.globalStateService.currentData;
  }

}
