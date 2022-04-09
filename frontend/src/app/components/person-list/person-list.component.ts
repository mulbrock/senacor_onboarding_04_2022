import {Component, OnInit} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  persons: any[] = [];

  constructor(
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.readPersons();
  }

  readPersons(): void {
    this.personService.readAll()
      .subscribe(
        persons => {
          this.persons = persons;
        },
        error => {
          console.log(error);
        });
  }

  refresh(): void {
    this.readPersons();
  }

  selectPerson(person: any) {
    this.router.navigate(['/persons/' + person.id]);
  }

}
