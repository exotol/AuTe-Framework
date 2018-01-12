import {BodyPattern} from './body-pattern';
import {BasicAuthCredentials} from 'model/basic-auth-credentials';

export class RequestMapping {
  url: string;
  urlPattern: string;
  method: string;
  headers: any;
  bodyPatterns: BodyPattern[];
  basicAuthCredentials: BasicAuthCredentials;
}

export class HeaderItem {
  headerName: string;
  headerValue: string;
  compareType: string;
}
