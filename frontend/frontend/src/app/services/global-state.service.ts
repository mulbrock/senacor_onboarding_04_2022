import { Injectable } from '@angular/core';
import {PersonInterface} from "../interfaces/person-interface";
import {BehaviorSubject, Observable, switchMap} from "rxjs";
import {DataService} from "./data.service";

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  private initialItem: PersonInterface = {
    _id: "",
    firstName: "",
    lastName: "",
    age: -1,
    groups: []
  }

  private person: BehaviorSubject<PersonInterface> = new BehaviorSubject<PersonInterface>(this.initialItem);
  currentPerson = this.person.asObservable();

  private refreshCurrentData = new BehaviorSubject(null);
  currentData: Observable<Array<PersonInterface>> = this.refreshCurrentData
    .asObservable()
    .pipe(switchMap(() => this.dataService.getAllPersons()));

  constructor(private dataService: DataService) { }
}
