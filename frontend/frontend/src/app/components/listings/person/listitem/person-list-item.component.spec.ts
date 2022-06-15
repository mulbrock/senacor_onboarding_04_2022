import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonListItemComponent } from './person-list-item.component';

describe('ListitemComponent', () => {
  let component: PersonListItemComponent;
  let fixture: ComponentFixture<PersonListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
