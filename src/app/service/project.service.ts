import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Project} from '../model/project';
import {Headers, Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {Scenario} from '../model/scenario';

@Injectable()
export class ProjectService {

  public serviceUrl = '/api/rest/projects';
  private headers = new Headers({'Content-Type': 'text/plain'});

  constructor(
    private http: Http
  ) { }

  findAll(): Observable<Project[]> {
    return this.http.get(this.serviceUrl)
      .map(value => value.json() as Project[]);
  }

  save(project: Project): void {
    this.http.put(
      this.serviceUrl + '/' + project.id,
      project,
      this.headers
    );
  }

  findOne(projectId: number): Observable<Project> {
    return this.http.get(this.serviceUrl + '/' + projectId)
      .map(value => value.json() as Project);
  }

  findScenariosByProject(projectId: number): Observable<Scenario[]> {
    return this.http.get(this.serviceUrl + '/' + projectId + '/scenarios')
      .map(value => value.json() as Scenario[]);
  }
}
