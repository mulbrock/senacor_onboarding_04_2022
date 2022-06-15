import { Injectable } from '@angular/core';
import {PersonInterface} from "../interfaces/person-interface";
import {BehaviorSubject, Observable, switchMap} from "rxjs";
import {DataService} from "./data.service";
import {GroupInterface} from "../interfaces/group-interface";

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  private initialItem: PersonInterface = {
    id: "",
    firstName: "",
    lastName: "",
    age: -1,
    groups: []
  }

  private person: BehaviorSubject<PersonInterface> = new BehaviorSubject<PersonInterface>(this.initialItem);
  currentPerson = this.person.asObservable();

  private refreshCurrentPersonData = new BehaviorSubject(null);
  currentPersonData: Observable<Array<PersonInterface>> = this.refreshCurrentPersonData
    .asObservable()
    .pipe(switchMap(() => this.dataService.getAllPersons()));

  private refreshCurrentGroupData = new BehaviorSubject(null);
  currentGroupData: Observable<Array<GroupInterface>> = this.refreshCurrentGroupData
    .asObservable()
    .pipe(switchMap(() => this.dataService.getAllGroups()));

  constructor(private dataService: DataService) { }
}
