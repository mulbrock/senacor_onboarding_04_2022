import { Injectable } from '@angular/core';
import {GlobalStateService} from "./global-state.service";
import {Observable, of} from "rxjs";
import {GroupInterface} from "../interfaces/group-interface";

@Injectable({
  providedIn: 'root'
})
export class GroupServiceService {

  currentGroups: Observable<Array<GroupInterface>> = this.globalStateService.currentGroupData;

  constructor(private globalStateService: GlobalStateService) { }

  public getMatchingGroupsObservableForPerson(personId: string): Array<Observable<GroupInterface>>{
    let personGroups = new Array<Observable<GroupInterface>>()
    this.currentGroups.subscribe(item => {
      item.forEach(group => {
        group.groupMembers.forEach(personInGroup => {
          if (personInGroup.id == personId){
            personGroups.push(of(group));
          }
        })
      })
    })
    return personGroups;
  }

  public getMatchingGroupsForPerson(personId: string): Set<GroupInterface>{
    let personGroups = new Set<GroupInterface>()
    this.currentGroups.subscribe(item => {
      item.forEach(group => {
        group.groupMembers.forEach(personInGroup => {
          if (personInGroup.id == personId){
            personGroups.add(group);
          }
        })
      })
    })
    return personGroups;
  }
}
