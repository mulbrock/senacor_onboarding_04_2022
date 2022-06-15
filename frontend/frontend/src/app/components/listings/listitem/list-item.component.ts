import {Component, Input, OnInit} from '@angular/core';
import {PersonInterface} from "../../../interfaces/person-interface";

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.css']
})
export class ListItemComponent implements OnInit {

  @Input() person!: PersonInterface;

  constructor() { }

  ngOnInit(): void {
  }

}
