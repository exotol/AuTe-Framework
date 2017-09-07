import {BodyPattern} from './body-pattern';

export class RequestMapping {
  url: string;
  urlPattern: string;
  method: string;
  headers: any;
  bodyPatterns: BodyPattern[];
}

export class HeaderItem {
  headerName: string;
  headerValue: string;
  compareType: string;
}
