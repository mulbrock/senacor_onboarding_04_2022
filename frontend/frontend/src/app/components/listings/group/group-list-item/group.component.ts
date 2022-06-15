import {Component, Input, OnInit} from '@angular/core';
import {GroupInterface} from "../../../../interfaces/group-interface";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  @Input() inputGroup!: GroupInterface;

  constructor() { }

  ngOnInit(): void {
  }



}
