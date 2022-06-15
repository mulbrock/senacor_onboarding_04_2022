import {Component, Input, OnInit} from '@angular/core';
import {PersonInterface} from "../../../../interfaces/person-interface";
import {GroupInterface} from "../../../../interfaces/group-interface";
import {GroupServiceService} from "../../../../services/group-service.service";

@Component({
  selector: 'app-person-list-item',
  templateUrl: './person-list-item.component.html',
  styleUrls: ['./person-list-item.component.css']
})
export class PersonListItemComponent implements OnInit {

  @Input() person!: PersonInterface;
  personsGroups: Set<GroupInterface> = new Set<GroupInterface>();

  expanded: boolean = false;
  editMode: boolean = false;

  constructor(private groupService: GroupServiceService) { }

  ngOnInit(): void {
    this.getPersonsGroups();
  }

  private getPersonsGroups(): void{
      this.personsGroups = this.groupService.getMatchingGroupsForPerson(this.person.id);
  }

  toggleExpand(): void {
    this.expanded = !this.expanded;
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
  }

  saveButtonClicked(): void {
    this.toggleEditMode();
  }

  collapse(): void {
    this.expanded = false;
    this.editMode = false;
  }
}
