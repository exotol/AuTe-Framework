import { Injectable } from '@angular/core';
import {Scenario} from '../model/scenario';
import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {StepResult} from '../model/step-result';
import 'rxjs/add/operator/map';
import {Step} from '../model/step';
import {Globals} from '../globals';

@Injectable()
export class ScenarioService {

  public serviceUrl = '/rest/scenarios';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  run(scenario: Scenario): Observable<StepResult[]> {
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + scenario.id + '/exec',
      {},
      {headers: this.headers}
    ).map(value => value.json() as StepResult[]);
  }

  findOne(scenarioId: number): Observable<Scenario> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + scenarioId)
      .map(value => value.json() as Scenario);
  }

  findScenarioSteps(scenarioId: number): Observable<Step[]> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + scenarioId + '/steps')
      .map(value => value.json() as Step[]);
  }

  saveStepList(scenario: Scenario, stepList: Step[]): Observable<Step[]> {
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + scenario.id + '/steps',
      stepList,
      {headers: this.headers}
    ).map(value => value.json() as Step[]);
  }
}
