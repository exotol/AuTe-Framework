import {ExpectedServiceRequest} from './expected-service-request';
import {MockServiceResponse} from './mock-service-response';
import {StepParameterSet} from './step-parameter-set';
import {FormData} from './form-data';

export class Step {
  code: String;
  expectedServiceRequestList: ExpectedServiceRequest[];
  relativeUrl: string;
  requestMethod: string;
  request: string;
  requestHeaders: string;
  expectedResponse: string;
  expectedResponseIgnore: boolean;
  expectedStatusCode: number;
  sql: string;
  sqlSavedParameter: string;
  jsonXPath: string;
  requestBodyType: string;
  usePolling: boolean;
  pollingJsonXPath: string;
  mockServiceResponseList: MockServiceResponse[];
  disabled: boolean;
  stepComment: string;
  savedValuesCheck: any = {};
  stepParameterSetList: StepParameterSet[] = [];
  responseCompareMode: string;
  formDataList: FormData[] = [];
  multipartFormData: boolean;
  mqName: string;
  mqMessage: string;
  jsonCompareMode: String;
  script: String;
  numberRepetitions: String;
}
