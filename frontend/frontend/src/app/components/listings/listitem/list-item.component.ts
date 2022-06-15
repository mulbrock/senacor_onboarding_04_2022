import {Component, Input, OnInit} from '@angular/core';
import {DataService} from "../../../services/data.service";

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.css']
})
export class ListItemComponent implements OnInit {

  @Input() inputItem!: String;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.test();
  }

}
