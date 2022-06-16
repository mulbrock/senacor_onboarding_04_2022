import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from "@angular/common/http";
import { catchError, Observable, throwError} from "rxjs";
import {ReadPersonInterface} from "../interfaces/read-person-interface";
import {ReadGroupInterface} from "../interfaces/read-group-interface";
import {CreateUpdatePersonInterface} from "../interfaces/create-update-person-interface";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private endpoint = "http://localhost:8080/api/";
  public persons!: Array<String>;

  constructor(private httpClient: HttpClient) { }

  getAllPersons(): Observable<any> {
    return this.httpClient.get<Array<ReadPersonInterface>>(this.endpoint + "persons").pipe(
      catchError(DataService.handleError)
    );
  }

  getPersonByID(id: string): Observable<any> {
    return this.httpClient.get<ReadPersonInterface>(this.endpoint + "persons/" + id).pipe(
      catchError(DataService.handleError)
    );
  }

  updatePersonByID(id: string, person: CreateUpdatePersonInterface): Observable<any>{
    console.log(person)

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    }

    return this.httpClient.put<CreateUpdatePersonInterface>(this.endpoint + "persons/" + id, person, httpOptions).pipe(
      catchError(DataService.handleError)
    );
  }

  getAllGroups(): Observable<any> {
    return this.httpClient.get<Array<ReadGroupInterface>>(this.endpoint + "groups").pipe(
      catchError(DataService.handleError)
    );
  }

  private static handleError(error: HttpErrorResponse): any {
    if(error.error instanceof ErrorEvent){
      console.error("An error occurred: " + error.error.message);
    } else {
      console.error(
        `Backend error code ${error.status}, ` +
        `body was ${error.error}`);
    }
    return throwError(() => {
      "Something bad happened. Please try again later."
    })
  }
}
