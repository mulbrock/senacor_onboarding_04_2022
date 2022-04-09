import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../services/group.service";

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.css']
})
export class GroupDetailsComponent implements OnInit {

  group: any = null;

  constructor(
    private groupService: GroupService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getGroup(this.route.snapshot.paramMap.get('id'));
  }

  getGroup(id: any): void {
    this.groupService.read(id)
      .subscribe(
        group => {
          this.group = group;
        },
        error => {
          console.log(error);
        });
  }

  deleteGroup() {
    this.groupService.delete(this.group.id)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/groups']);
        },
        error => {
          console.log(error);
        });
  }

  selectPerson(person: any) {
    this.router.navigate(['/persons/' + person.id]);
  }
}
