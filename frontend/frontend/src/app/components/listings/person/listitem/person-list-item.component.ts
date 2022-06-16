import {Component, Input, OnInit} from '@angular/core';

import {ReadPersonInterface} from "../../../../interfaces/read-person-interface";
import {ReadGroupInterface} from "../../../../interfaces/read-group-interface";
import {GroupServiceService} from "../../../../services/group-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent implements OnInit {

  @Input() person!: ReadPersonInterface;
  personsGroups: Set<ReadGroupInterface> = new Set<ReadGroupInterface>();

  expanded: boolean = false;
  editMode: boolean = false;

  constructor(private groupService: GroupServiceService,
              private router: Router) { }

  ngOnInit(): void {
    this.getPersonsGroups();
  }

  private getPersonsGroups(): void{
      this.personsGroups = this.groupService.getMatchingGroupsForPerson(this.person.id);
  }

  expand(): void {
    this.expanded = true;
  }

  editButtonClicked(): void {
    this.router.navigate(["/edit"], {queryParams: {id: this.person.id}})
  }

  collapse(): void {
    this.expanded = false;
    this.editMode = false;
  }

  validateForm(): void {

  }
}
