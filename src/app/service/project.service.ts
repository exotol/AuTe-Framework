import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Project} from '../model/project';
import {Headers, Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {Scenario} from '../model/scenario';
import {Globals} from '../globals';

@Injectable()
export class ProjectService {

  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  findAll(): Observable<Project[]> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl)
      .map(value => value.json() as Project[]);
  }

  save(project: Project): Observable<Project> {
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + project.code,
      project,
      { headers: this.headers }
    ).map(value => value.json() as Project);
  }

  findOne(projectCode: String): Observable<Project> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode)
      .map(value => value.json() as Project);
  }

  findScenariosByProject(projectCode: String): Observable<Scenario[]> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios')
      .map(value => value.json() as Scenario[]);
  }

  createScenario(project: Project, scenario: Scenario): Observable<Scenario> {
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + project.code + '/scenarios',
      scenario,
      {headers: this.headers}
    ).map(value => value.json() as Scenario);
  }

  /*
  downloadYaml(project: Project, selectedScenarios: number[]) {
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + project.code + '/export-selected-to-yaml',
      selectedScenarios,
      {responseType: ResponseContentType.Blob}
    ).map(value => new Blob([value.blob()]));
  }
  */
}
