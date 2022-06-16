import { Injectable } from '@angular/core';
import {CreateUpdatePersonInterface} from "../interfaces/create-update-person-interface";
import {ReadPersonInterface} from "../interfaces/read-person-interface";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor() { }

  public mapReadPersonToUpdatePerson(person: ReadPersonInterface): CreateUpdatePersonInterface {
    return {
      "firstName": person.firstName,
      "lastName": person.lastName,
      "age": String(person.age)
    }
  }
}
