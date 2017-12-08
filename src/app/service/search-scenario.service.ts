import {Injectable} from '@angular/core';
import {Globals} from '../globals';
import {Headers, Http} from '@angular/http';
import {Scenario} from '../model/scenario';


@Injectable()
export class SearchScenarioService {
  public serviceUrl = '/rest/projects';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(
    private globals: Globals,
    private http: Http
  ) { }

  searchByMethod(projectId: number, queryString: string): Promise<Scenario[]> {
    const url = this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectId + '/search ';

    return this.http.post(url, {'relativeUrl': queryString},
      {headers: this.headers})
      .map(value => value.json() as Scenario[]).toPromise();
    /*return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectId + '/scenarios')
      .map(value => value.json() as Scenario[]).toPromise();*/
  }
}
