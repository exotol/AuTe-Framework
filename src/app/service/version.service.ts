import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import {Globals} from '../globals';
import {Observable} from 'rxjs/Observable';
import {Version} from '../model/version';

@Injectable()
export class VersionService {

  public serviceUrl = '/rest/version';

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  getVersion(): Observable<Version> {
    return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl)
      .map(value => value.json() as Version);
  }
}
