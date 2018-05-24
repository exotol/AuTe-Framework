import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Project} from '../model/project';
import {Headers, Http, Response} from '@angular/http';
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

  create(project: Project): Observable<Project> {
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl,
      project,
      { headers: this.headers }
    ).map(value => value.json() as Project);
  }

  findOne(projectCode: string): Observable<Project> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode)
      .map(value => value.json() as Project);
  }

  findScenariosByProject(projectCode: string): Observable<Scenario[]> {
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

  addNewGroup(projectCode: string, groupName: string): Observable<string[]> {
    return this.http.post(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/group',
      groupName,
      {headers: this.headers}
    ).map(value => value.json() as string[]);
  }

  renameGroup(projectCode: string, oldGroupName: string, newGroupName: string) {
    return this.http.put(
      this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/group',
      {
        oldGroupName: oldGroupName,
        newGroupName: newGroupName
      },
      {headers: this.headers}
    ).map(value => value.json() as string[]);
  }
}
