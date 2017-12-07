import { Injectable } from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Step} from '../model/step';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {Globals} from '../globals';

@Injectable()
export class StepService {

  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  saveStep(projectCode: String, scenarioGroup: String, scenarioCode: String, step: Step): Observable<Step> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code,
      step,
      {headers: this.headers}
    ).map(value => value.json() as Step);
  }

  cloneStep(projectCode: String, scenarioGroup: String, scenarioCode: String, step: Step): Observable<Step> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code + '/clone',
      {},
      {headers: this.headers}
    ).map(value => value.json() as Step);
  }
}
