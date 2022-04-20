import { Component, OnInit } from '@angular/core';
import { Person } from '../Person'
import { PersonService } from "../person.service";
import { MessageService } from "../message.service";

@Component({
  selector: 'app-person-list',
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class Persons implements OnInit {
  selectedPerson?: Person;
  people: Person[] = [];

  personModel = new Person('Georgi', 'Mavrov', 20);

  constructor(private personService: PersonService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.getPeople()
  }

  getPeople(): void {
    this.personService.getPeople().subscribe(people => this.people = people);
  }

  onSubmit() {
    this.personService.createPerson(this.personModel as Person).subscribe(person => console.log({person}) )
  }
}
