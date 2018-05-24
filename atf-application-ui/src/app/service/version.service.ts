import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import {Globals} from '../globals';
import {Observable} from 'rxjs/Observable';
import {Version} from '../model/version';
import {WiremockVersion} from "../model/wiremock-version";

@Injectable()
export class VersionService {

  public managerServiceUrl = '/rest/version/manager';
  public executorServiceUrl = '/rest/version/executor';
  public wiremockServiceUrl = '/rest/version/wiremock';

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  getManagerVersion(): Observable<Version> {
    return this.getVersion(this.managerServiceUrl);
  }

  getExecutorVersion(): Observable<Version> {
    return this.getVersion(this.executorServiceUrl);
  }

  getProjectsWiremockVersions(): Observable<Array<WiremockVersion>> {
    return this.http.get(this.globals.serviceBaseUrl + this.wiremockServiceUrl)
      .map(value => value.json() as Array<WiremockVersion>);
  }

  getVersion(url): Observable<Version> {
    return this.http.get(this.globals.serviceBaseUrl + url).map(value => value.json() as Version);
  }
}
