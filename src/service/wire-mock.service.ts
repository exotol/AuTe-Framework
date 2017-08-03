import { Injectable } from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Mapping} from '../model/mapping';

@Injectable()
export class WireMockService {

  // URL to WireMock
  // private serviceUrl = 'http://piphagor.bscmsc.ru/bsc-wire-mock';
  private serviceUrl = 'http://localhost:7770';
  private adminUrl = this.serviceUrl + '/__admin';

  constructor(private http: Http) { }

  getMappingList(): Promise<Mapping[]> {
    return this.http.get(this.adminUrl + '/mappings')
      .toPromise()
      .then(response => response.json().mappings as Mapping[]);
  }

  deleteOne(mapping: Mapping) {
    this.http.delete(this.adminUrl + '/mappings/' + mapping.uuid);
  }
}
