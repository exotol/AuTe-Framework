import {BodyPattern} from './body-pattern';

export class RequestMapping {
  urlPattern: String;
  method: String;
  bodyPatterns: BodyPattern[];
}
