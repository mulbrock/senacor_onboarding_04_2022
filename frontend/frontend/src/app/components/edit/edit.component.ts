import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {ReadPersonInterface} from "../../interfaces/read-person-interface";
import {DataService} from "../../services/data.service";
import {PersonService} from "../../services/person.service";
import {ReadGroupInterface} from "../../interfaces/read-group-interface";
import {GroupServiceService} from "../../services/group-service.service";
import {CreateUpdateState} from "../../enums/create-update-state";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  personForm!: FormGroup;
  person!: ReadPersonInterface;
  personGroups!: Set<ReadGroupInterface>;

  public componentState!: CreateUpdateState;

  constructor(
    private dataService: DataService,
    private personService: PersonService,
    private groupService: GroupServiceService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.person = this.createEmptyPerson();

    if (!this.router.routerState.snapshot.url.endsWith("/create_person")){
      this.componentState = CreateUpdateState.UPDATE;
      let id = this.route.snapshot.queryParams['id'];
      this.personGroups = this.groupService.getMatchingGroupsForPerson(id);
      this.populateFormFromPersonID(id);
    } else {
      this.componentState = CreateUpdateState.CREATE;
    }
    this.personForm = this.createPersonForm();
  }

  private populateFormFromPersonID(id: string): void {
    if (id) {
      this.dataService.getPersonByID(id).subscribe(item => {
        if (item) {
          this.person = item;
          this.createPersonForm();
        } else {
          this.router.navigateByUrl("");
        }
      });
    }
  }

  public navigateBackButtonClicked(): void {
    this.router.navigate(["/"])
  }

  public discardButtonClicked(): void {
    this.personForm = this.createPersonForm();
  }

  public saveButtonClicked(): void {
    if (this.isFormSectionValid(this.personForm.get("firstName"))
      && this.isFormSectionValid(this.personForm.get("lastName"))
      && this.isFormSectionValid(this.personForm.get("age"))){

      console.log("FORM VALID")

      if (this.personForm.get("firstName")?.dirty){
        this.person.firstName = this.personForm.get("firstName")?.value;
      }
      if (this.personForm.get("lastName")?.dirty){
        this.person.lastName = this.personForm.get("lastName")?.value;
      }
      if (this.personForm.get("age")?.dirty){
        this.person.age = this.personForm.get("age")?.value;
      }
      let personDTO = this.personService.mapReadPersonToUpdatePerson(this.person);
      if (this.componentState == CreateUpdateState.UPDATE){
        this.dataService.updatePersonByID(this.person.id, personDTO).subscribe();
      } else {
        this.dataService.createPerson(personDTO).subscribe();
      }
    }
  }

  private isFormSectionValid(formSection: AbstractControl | null): boolean {
    if (formSection){
      if (this.componentState == CreateUpdateState.UPDATE){
        if (formSection.untouched){
          return true;
        }
        if (formSection.dirty){
          return formSection.valid;
        }
      } else if (this.componentState == CreateUpdateState.CREATE){
        if (formSection.untouched){
          return false;
        }
        return formSection.valid;
      }
    }
    return false;
  }

  private createPersonForm(){
    return new FormGroup({
      firstName: new FormControl(this.person.firstName, [
        Validators.required,
        Validators.minLength(1)
      ], []),
      lastName: new FormControl(this.person.lastName, [
        Validators.required,
        Validators.minLength(1)
      ]),
      age: new FormControl(this.person.age, [
        Validators.required,
        Validators.min(1)
      ], [])
    });
  }

  private createEmptyPerson(): ReadPersonInterface {
    return {
      id: "0",
      firstName: "",
      lastName: "",
      age: 0,
      groups: []
    }
  }
}
