import { Injectable } from '@angular/core';
import {Scenario} from '../model/scenario';
import {Headers, Http, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {Step} from '../model/step';
import {Globals} from '../globals';
import {StartScenarioInfo} from '../model/start-scenario-info';
import {ExecutionResult} from '../model/execution-result';
import {ScenarioIdentity} from "../model/scenario-identity";
import {StepResult} from "../model/step-result";

@Injectable()
export class ScenarioService {

  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  run(projectCode: string, scenario: Scenario): Observable<StartScenarioInfo> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/start',
      {},
      {headers: this.headers}
    ).map(value => value.json() as StartScenarioInfo);
  }

  executionStatus(runningUuid: string): Observable<ExecutionResult> {
    return this.http.get(
      this.globals.serviceBaseUrl + '/rest/execution/' + runningUuid + '/status'
    ).map(value => value.json() as ExecutionResult);
  }

  stop(runningUuid: string): Observable<any> {
    return this.http.post(
      this.globals.serviceBaseUrl + '/rest/execution/' + runningUuid + '/stop',
      {},
      {headers: this.headers}
    );
  }

  findOne(projectCode: string, scenarioGroup: string, scenarioCode: string): Observable<Scenario> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http
      .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath)
      .map(value => value.json() as Scenario);
  }

  findScenarioSteps(projectCode: string, scenarioGroup: string, scenarioCode: string): Observable<Step[]> {
    const scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
    return this.http
      .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps')
      .map(value => value.json() as Step[]);
  }

  saveStepList(projectCode: string, scenario: Scenario, stepList: Step[]): Observable<Step[]> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps',
      stepList,
      {headers: this.headers}
    ).map(value => value.json() as Step[]);
  }

  saveOne(projectCode: string, scenario: Scenario, scenarioGroup: string): Observable<Scenario> {
    const scenarioPath = (scenarioGroup ? scenarioGroup+ '/' : '') + scenario.code;
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath,
      scenario,
      {headers: this.headers}
    ).map(value => value.json() as Scenario);
  }

  deleteOne(projectCode: string, scenario: Scenario): Observable<any> {
    const scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
    return this.http.delete(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath
    );
  }

  getResults(identity: ScenarioIdentity): Observable<StepResult[]> {
    return this.http.post(this.globals.serviceBaseUrl + '/rest/execution/results', identity)
      .map(data => {
        try {
          return data.json() as StepResult[];
        } catch (e) {
          console.log(e);
          return [];
        }
      });
  }

  downloadReport(identities: ScenarioIdentity[]): Observable<Blob> {
    return this.http.post(
      this.globals.serviceBaseUrl + '/rest/execution/report',
      identities,
      {responseType: ResponseContentType.Blob }
    ).map(data => data.blob());
  }
}
