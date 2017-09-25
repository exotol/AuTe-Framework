import { Injectable } from '@angular/core';
import {Scenario} from '../model/scenario';
import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {StepResult} from '../model/step-result';
import 'rxjs/add/operator/map';

@Injectable()
export class ScenarioService {

  public serviceUrl = '/api/rest/scenarios';
  private headers = new Headers({'Content-Type': 'text/plain'});

  constructor(
    private http: Http
  ) { }

  run(scenario: Scenario): Observable<StepResult[]> {
    return this.http.post(
      this.serviceUrl + '/' + scenario.id + '/exec',
      {},
      {headers: this.headers}
    ).map(value => value.json() as StepResult[]);
  }
}
