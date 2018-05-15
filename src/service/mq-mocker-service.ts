import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {MqLogItem} from '../model/mq-log-item';

@Injectable()
export class MqMockerService {

  public mqMockerAdminUrl = '/mq-mock/__admin';

  constructor(private http: Http) { }

  getRequestList(limit): Observable<MqLogItem[]> {
    return this.http
      .get(this.mqMockerAdminUrl + '/request-list' + (limit ? '?limit=' + limit : ''))
      .map(value => value.json() as MqLogItem[])
      .map(value => value.reverse());
  }

  clear(): Observable<any> {
    return this.http
      .post(this.mqMockerAdminUrl + '/request-list/clear', {});
  }
}
