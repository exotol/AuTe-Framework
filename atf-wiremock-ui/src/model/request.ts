import {Mapping} from './mapping';

export class Request {
  id: string;
  request: RequestData;
  responseDefinition: any;
  response: any;
  wasMatched: boolean;
  stubMapping: Mapping;
}

class RequestData {
  url: string;
  absoluteUrl: string;
  method: string;
  clientIp: string;
  headers: any;
  cookies: any;
  browserProxyRequest: boolean;
  loggedDate: number;
  bodyAsBase64: string;
  body: string;
  loggedDateString: string;
}
