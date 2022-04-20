import { Component, OnInit, Input } from '@angular/core';
import { Person } from "../Person";
import { PersonService } from "../person.service";
import { Location } from "@angular/common";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-person-detail',
  templateUrl: './person-detail.component.html',
  styleUrls: ['./person-detail.component.css']
})
export class PersonDetailComponent implements OnInit {

  constructor(private personService: PersonService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.getPerson();
  }

  @Input() person?: Person;

  getPerson(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.personService.getPerson(id)
      .subscribe(person => this.person = person);
  }

  goBack() {
    this.location.back();
  }
  save() {
    if(this.person) {
      this.personService.updatePerson(this.person)
        .subscribe(() => this.goBack());
    }
  }
}
