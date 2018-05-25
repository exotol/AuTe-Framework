import { Injectable } from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Mapping} from '../model/mapping';
import {Observable} from 'rxjs/Observable';
import {RequestList} from '../model/request-list';
import 'rxjs/add/operator/map';

@Injectable()
export class WireMockService {

  // URL to WireMock
  public adminUrl = '/__admin';
  private headers = new Headers({'Content-Type': 'text/plain'});

  constructor(private http: Http) { }

  getMappingList(): Promise<Mapping[]> {
    return this.http
      .get(this.adminUrl + '/mappings')
      .toPromise()
      .then(response => response.json().mappings as Mapping[])
      .catch(reason => console.log(reason));
  }

  deleteOne(mapping: Mapping) {
    this.http
      .delete(this.adminUrl + '/mappings/' + mapping.uuid);
  }

  apply(mapping: Mapping): Promise<Mapping> {
    if (mapping.uuid) {
      return this.http
        .put(
          this.adminUrl + '/mappings/' + mapping.uuid,
          mapping,
          { headers: this.headers }
        )
        .toPromise()
        .then(response => response.json() as Mapping)
        .catch(reason => console.log(reason));
    } else {
      return this.http
        .post(
          this.adminUrl + '/mappings',
          mapping,
          { headers: this.headers }
        )
        .toPromise()
        .then(response => response.json() as Mapping)
        .catch(reason => console.log(reason));
    }
  }

  saveToBackStorage(): Promise<null> {
    return this.http
      .post(
        this.adminUrl + '/mappings/save',
        null,
        { headers: this.headers }
      )
      .toPromise()
      .catch(reason => console.log(reason));
  }

  findOne(uuid: string): Promise<Mapping> {
    return this.http
      .get(this.adminUrl + '/mappings/' + uuid)
      .toPromise()
      .then(response => response.json() as Mapping)
      .catch(reason => console.log(reason));
  }

  getRequestList(limit?): Observable<RequestList> {
    return this.http
      .get(limit ? this.adminUrl + '/requests?limit=' + (limit ? limit : 50) : this.adminUrl + '/requests')
      .map(value => value.json() as RequestList);
  }

  clearRequestList(): Observable<any> {
    return this.http
      .post(
        this.adminUrl + '/requests/reset',
        null,
        { headers: this.headers }
      );
  }
}