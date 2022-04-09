import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../services/group.service";

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  groups: any[] = [];

  constructor(
    private groupService: GroupService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.readGroups();
  }

  readGroups(): void {
    this.groupService.readAll()
      .subscribe(
        groups => {
          this.groups = groups;
        },
        error => {
          console.log(error);
        });
  }

  createRandomGroup(): void {
    this.groupService.createRandom()
      .subscribe(
        group => {
          this.router.navigate(['/groups/' + group.id]);
        },
        error => {
          console.log(error);
        });
  }

  refresh(): void {
    this.readGroups();
  }

  selectGroup(group: any) {
    this.router.navigate(['/groups/' + group.id]);
  }
}
