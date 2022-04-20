import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BMW Frontend';
  message = "I'm read only";

  canClick = true;
  canEdit = false;

  onEditClick() {
    this.canEdit = !this.canEdit;
    if(this.canEdit) {
      this.message = 'You can edit me!';
    } else {
      this.message = "I'm read only";
    }
  }

  sayHello() {
    alert('Hello' + this.title);
  }
}
