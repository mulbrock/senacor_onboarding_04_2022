import {  Injectable } from '@angular/core';
import { Person } from "./Person";
import { mockPersons } from "./mock-persons";
import { catchError, map, tap, Observable, of } from "rxjs";
import { MessageService } from "./message.service";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private httpClient: HttpClient, private messageService: MessageService) { }

  private baseUrl = '/api/v1/persons';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  private log(message: string) {
    this.messageService.add(`PersonService: ${message}`);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** POST: Create a new person on the server*/
  createPerson(person: Person): Observable<Person> {
    return this.httpClient.post<Person>(this.baseUrl, person, this.httpOptions).pipe(
      tap((newPerson: Person) => this.log(`Created Person with ID=${newPerson.id}`)),
      catchError(this.handleError<Person>('createPerson'))
    );
  }

  getPeople(): Observable<Person[]> {
    const people = this.httpClient.get<Person[]>(this.baseUrl)
      .pipe(
        tap(_ => this.log('Fetched people')),
        catchError(this.handleError<Person[]>('getPeople', []))
      )
    this.messageService.add('PersonService: fetched people');
    return people
  }

  /**
   * Get A person by ID, returns 404 if the person is not found
   * @param id - Person ID
   */
  getPerson(id: number): Observable<Person> {
    const url = `${this.baseUrl}/${id}`;
    return this.httpClient.get<Person>(url).pipe(
      tap(_=> this.log(`Fetched Person with id=${id}`)),
      catchError(this.handleError<Person>(`getPerson id=${id}`))
    )
  }

  /**
   * Update an existing Person on the BE
   * @param person
   */
  updatePerson(person: Person) {
    return this.httpClient.put(this.baseUrl, person, this.httpOptions).pipe(
      tap(_ => this.log(`Updated Person with id=${person.id}`)),
      catchError(this.handleError<any>('updatePerson'))
    );
  }
}
