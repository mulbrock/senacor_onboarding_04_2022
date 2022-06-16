import {Component, Input, OnInit} from '@angular/core';
import {ReadGroupInterface} from "../../../../interfaces/read-group-interface";

@Component({
  selector: 'app-group-list-item',
  templateUrl: './group-list-item.component.html',
  styleUrls: ['./group-list-item.component.css']
})
export class GroupListItemComponent implements OnInit {

  @Input() inputGroup!: ReadGroupInterface;

  constructor() { }

  ngOnInit(): void {
  }



}
