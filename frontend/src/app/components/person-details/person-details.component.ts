import {Component, OnInit} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.css']
})
export class PersonDetailsComponent implements OnInit {

  person: any = null;

  constructor(
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getPerson(this.route.snapshot.paramMap.get('id'));
  }

  getPerson(id: any): void {
    this.personService.read(id)
      .subscribe(
        person => {
          this.person = person;
        },
        error => {
          console.log(error);
        });
  }

  deletePerson() {
    this.personService.delete(this.person.id)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/persons']);
        },
        error => {
          console.log(error);
        });
  }

  updatePerson() {
    const toUpdate = {
      firstName: this.person.firstName,
      lastName: this.person.lastName,
      age: this.person.age,
    };

    this.personService.update(this.person.id, toUpdate)
      .subscribe(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        });
  }

  selectGroup(group: any) {
    this.router.navigate(['/groups/' + group.id]);
  }
}
