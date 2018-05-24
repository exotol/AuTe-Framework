export class MockServiceResponse {
  code: string;
  serviceUrl: string;
  responseBody: string;
  headers: HeaderItem[];
  httpStatus: number;
  contentType: number;
  userName: string;
  password: string;
  pathFilter: string;
}

export class HeaderItem {
  headerName: string;
  headerValue: string;
  compareType: string = 'equalTo';
}
