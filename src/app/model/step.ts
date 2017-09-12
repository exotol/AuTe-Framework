import {ExpectedServiceRequest} from './expected-service-request';
import {MockServiceResponse} from './mock-service-response';
import {StepParameterSet} from './step-parameter-set';

export class Step {
  id: number;
  expectedServiceRequests: ExpectedServiceRequest[];
  sort: number;
  relativeUrl: string;
  requestMethod: string;
  request: string;
  requestHeaders: string;
  expectedResponse: string;
  expectedResponseIgnore: boolean;
  savingValues: string;
  responses: string;
  dbParams: string;
  tmpServiceRequestsDirectory: string;
  expectedStatusCode: number;
  sql: string;
  sqlSavedParameter: string;
  jsonXPath: string;
  requestBodyType: string;
  usePolling: boolean;
  pollingJsonXPath: string;
  mockServiceResponseList: MockServiceResponse[];
  disabled: boolean
  stepComment: string;
  savedValuesCheck: any;
  stepParameterSetList: StepParameterSet[];
}
