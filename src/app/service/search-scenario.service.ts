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
