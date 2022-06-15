import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private endpoint = "http://localhost/api/";
  public persons!: Array<String>;

  constructor(private httpClient: HttpClient) { }

  getAllPersons(): Observable<any> {
    return this.httpClient.get(this.endpoint + "persons").pipe(
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
