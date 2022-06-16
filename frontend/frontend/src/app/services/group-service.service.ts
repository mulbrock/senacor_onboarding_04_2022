import { Injectable } from '@angular/core';
import {GlobalStateService} from "./global-state.service";
import {Observable, of} from "rxjs";
import {ReadGroupInterface} from "../interfaces/read-group-interface";

@Injectable({
  providedIn: 'root'
})
export class GroupServiceService {

  currentGroups: Observable<Array<ReadGroupInterface>> = this.globalStateService.currentGroupData;

  constructor(private globalStateService: GlobalStateService) { }

  public getMatchingGroupsObservableForPerson(personId: string): Array<Observable<ReadGroupInterface>>{
    let personGroups = new Array<Observable<ReadGroupInterface>>()
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

  public getMatchingGroupsForPerson(personId: string): Set<ReadGroupInterface>{
    let personGroups = new Set<ReadGroupInterface>()
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
