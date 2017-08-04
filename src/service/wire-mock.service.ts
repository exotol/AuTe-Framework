import { Injectable } from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Mapping} from '../model/mapping';

@Injectable()
export class WireMockService {

  // URL to WireMock
  // private serviceUrl = 'http://piphagor.bscmsc.ru/bsc-wire-mock';
  public serviceUrl = 'http://localhost:7770';
  public adminUrl = this.serviceUrl + '/__admin';
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

  save(mapping: Mapping): Promise<Mapping> {
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

  findOne(uuid: String): Promise<Mapping> {
    return this.http
      .get(this.adminUrl + '/mappings/' + uuid)
      .toPromise()
      .then(response => response.json() as Mapping)
      .catch(reason => console.log(reason));
  }
}
