import {Component, OnInit} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-person-create',
  templateUrl: './person-create.component.html',
  styleUrls: ['./person-create.component.css']
})
export class PersonCreateComponent implements OnInit {

  person: any = {
    firstName: Math.random().toString(36).slice(2),
    lastName: Math.random().toString(36).slice(2),
    age: Math.floor(Math.random() * 100)
  };

  constructor(
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  createPerson(): void {
    const data = {
      firstName: this.person.firstName,
      lastName: this.person.lastName,
      age: this.person.age,
    };

    this.personService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/persons/' + response.id]);
        },
        error => {
          console.log(error);
        });
  }

}
