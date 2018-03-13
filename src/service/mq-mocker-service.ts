import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {MqLogItem} from '../model/mq-log-item';

@Injectable()
export class MqMockerService {

  public mqMockerAdminUrl: string = window['mqMockerUrl'] + '/__admin';

  constructor(private http: Http) { }

  getRequestList(): Observable<MqLogItem[]> {
    return this.http
      .get(this.mqMockerAdminUrl + '/request-list')
      .map(value => value.json() as MqLogItem[]);
  }
}
