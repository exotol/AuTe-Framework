import { Injectable } from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Step} from '../model/step';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {Globals} from '../globals';

@Injectable()
export class StepService {

  public serviceUrl = '/rest/steps';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  saveStep(step: Step): Observable<Step> {
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + step.id,
      step,
      {headers: this.headers}
    ).map(value => value.json() as Step);
  }
}
