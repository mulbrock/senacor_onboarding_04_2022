import {Component, Input, OnInit} from '@angular/core';
import {ReadGroupInterface} from "../../../../interfaces/read-group-interface";
import {DataService} from "../../../../services/data.service";
import {GlobalStateService} from "../../../../services/global-state.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-group-list-item',
  templateUrl: './group-list-item.component.html',
  styleUrls: ['./group-list-item.component.css']
})
export class GroupListItemComponent implements OnInit {

  @Input() inputGroup!: ReadGroupInterface;
  expanded = false;

  constructor(private dataService: DataService,
              private stateService: GlobalStateService,
              private router: Router) { }

  ngOnInit(): void {
  }

  toggleExpand(): void {
    this.expanded = !this.expanded;
  }

  deleteButtonClicked(): void {
    let groupDelete = confirm("Do you really want to delete this group?");
    if (groupDelete){
      this.dataService.deleteGroup(this.inputGroup.id).subscribe(response => {
        if (response.status == 204){
          this.stateService.fetchGroupData();
        }
      });
      this.router.navigate(["/groups"]);
    }
  }
}
