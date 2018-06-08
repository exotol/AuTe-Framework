/*
 * Copyright 2018 BSC Msc, LLC 
 *
 * This file is part of the ATF project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

import {Injectable} from '@angular/core';
import {Globals} from '../globals';
import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Scenario} from '../model/scenario';


@Injectable()
export class SearchScenarioService {
  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  searchByMethod(projectCode: number, queryString: string): Observable<Scenario[]> {
    const url = this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/search ';

    return this.http.post(url, {'relativeUrl': queryString},
      {headers: this.headers})
      .map(value => value.json() as Scenario[]);
  }
}
