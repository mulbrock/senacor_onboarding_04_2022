import { Injectable } from '@angular/core';
import {ReadPersonInterface} from "../interfaces/read-person-interface";
import {BehaviorSubject, Observable, switchMap} from "rxjs";
import {DataService} from "./data.service";
import {ReadGroupInterface} from "../interfaces/read-group-interface";

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  private initialItem: ReadPersonInterface = {
    id: "",
    firstName: "",
    lastName: "",
    age: -1,
    groups: []
  }

  private person: BehaviorSubject<ReadPersonInterface> = new BehaviorSubject<ReadPersonInterface>(this.initialItem);
  currentPerson = this.person.asObservable();

  private refreshCurrentPersonData = new BehaviorSubject(null);
  currentPersonData: Observable<Array<ReadPersonInterface>> = this.refreshCurrentPersonData
    .asObservable()
    .pipe(switchMap(() => this.dataService.getAllPersons()));

  private refreshCurrentGroupData = new BehaviorSubject(null);
  currentGroupData: Observable<Array<ReadGroupInterface>> = this.refreshCurrentGroupData
    .asObservable()
    .pipe(switchMap(() => this.dataService.getAllGroups()));

  public fetchGroupData(){
    this.refreshCurrentGroupData.next(null);
  }

  public fetchPersonData(){
    this.refreshCurrentPersonData.next(null);
  }

  constructor(private dataService: DataService) { }
}
