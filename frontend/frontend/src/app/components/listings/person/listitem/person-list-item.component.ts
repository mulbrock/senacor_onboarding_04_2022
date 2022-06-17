import {Component, Input, OnInit} from '@angular/core';

import {ReadPersonInterface} from "../../../../interfaces/read-person-interface";
import {ReadGroupInterface} from "../../../../interfaces/read-group-interface";
import {GroupServiceService} from "../../../../services/group-service.service";
import {Router} from "@angular/router";
import {DataService} from "../../../../services/data.service";
import {GlobalStateService} from "../../../../services/global-state.service";

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent implements OnInit {

  @Input() person!: ReadPersonInterface;
  personsGroups: Set<ReadGroupInterface> = new Set<ReadGroupInterface>();

  expanded: boolean = false;

  constructor(private groupService: GroupServiceService,
              private dataService: DataService,
              private stateService: GlobalStateService,
              private router: Router) { }

  ngOnInit(): void {
    this.getPersonsGroups();
  }

  private getPersonsGroups(): void{
      this.personsGroups = this.groupService.getMatchingGroupsForPerson(this.person.id);
  }

  expand(): void {
    this.expanded = !this.expanded;
  }

  toggleExpand(): void {
    this.expanded = !this.expanded;
  }

  editButtonClicked(): void {
    this.router.navigate(["/edit"], {queryParams: {id: this.person.id}})
  }

  deleteButtonClicked(): void {
    let deletePerson = confirm("Do you really want to delete this person?");
    if (deletePerson) {
      this.dataService.deletePersonByID(this.person.id)
        .subscribe((response) => {
          if (response.status == 204){
            this.stateService.fetchPersonData();
          }
        });
    }
  }

  validateForm(): void {

  }
}
