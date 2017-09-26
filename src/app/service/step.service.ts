import { Injectable } from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Step} from '../model/step';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class StepService {

  public serviceUrl = '/api/rest/steps';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private http: Http
  ) { }

  saveStep(step: Step): Observable<Step> {
    return this.http.put(
      this.serviceUrl + '/' + step.id,
      step,
      {headers: this.headers}
    ).map(value => value.json() as Step);
  }
}
