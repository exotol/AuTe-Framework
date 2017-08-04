import {RequestMapping} from './request-mapping';
import {ResponseMapping} from './response-mapping';

export class Mapping {
  id: String;
  request: RequestMapping;
  response: ResponseMapping;
  uuid: String;
  priority: number;

  constructor() {
    this.request = new RequestMapping;
    this.response = new ResponseMapping;
  }
}
