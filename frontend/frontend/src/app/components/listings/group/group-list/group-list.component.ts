import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {ReadGroupInterface} from "../../../../interfaces/read-group-interface";
import {GlobalStateService} from "../../../../services/global-state.service";
import {DataService} from "../../../../services/data.service";

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  public groupList!: Observable<Array<ReadGroupInterface>>;

  constructor(private globalStateService: GlobalStateService,
              private dataService: DataService) { }

  ngOnInit(): void {
    this.groupList = this.globalStateService.currentGroupData;
  }


  addGroupButtonClicked(): void {
    this.dataService.createRandomGroup().subscribe(() => this.globalStateService.fetchGroupData());
  }


}
