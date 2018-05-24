import {RequestMapping} from './request-mapping';
import {ResponseMapping} from './response-mapping';

export class Mapping {
  id: string;
  request: RequestMapping;
  response: ResponseMapping;
  uuid: string;
  priority: number;

  constructor() {
    this.request = new RequestMapping;
    this.response = new ResponseMapping;
  }
}
