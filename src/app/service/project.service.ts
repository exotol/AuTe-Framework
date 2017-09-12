import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Project} from '../model/project';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ProjectService {

  public serviceUrl = '/api/rest/project';
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
}
