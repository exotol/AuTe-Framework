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

  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  run(projectCode: String, scenario: Scenario): Observable<StepResult[]> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/exec',
      {},
      {headers: this.headers}
    ).map(value => value.json() as StepResult[]);
  }

  findOne(projectCode: String, scenarioGroup: String, scenarioCode: String): Observable<Scenario> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http
      .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath)
      .map(value => value.json() as Scenario);
  }

  findScenarioSteps(projectCode: String, scenarioGroup: String, scenarioCode: String): Observable<Step[]> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http
      .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps')
      .map(value => value.json() as Step[]);
  }

  saveStepList(projectCode: String, scenario: Scenario, stepList: Step[]): Observable<Step[]> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps',
      stepList,
      {headers: this.headers}
    ).map(value => value.json() as Step[]);
  }

  saveOne(projectCode: String, scenario: Scenario): Observable<Scenario> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath,
      scenario,
      {headers: this.headers}
    ).map(value => value.json() as Scenario);
  }

  deleteOne(scenario: Scenario): Observable<any> {
    return this.http.delete(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + scenario.id
    );
  }
}
