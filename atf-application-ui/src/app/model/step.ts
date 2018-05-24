import {ExpectedServiceRequest} from './expected-service-request';
import {MockServiceResponse} from './mock-service-response';
import {StepParameterSet} from './step-parameter-set';
import {FormData} from './form-data';
import {MqMock} from './mq-mock';
import {ExpectedMqRequest} from './expected-mq-request';
import {SqlData} from './sql-data';
import {ScenarioVariableFromMqRequest} from './scenario-variable-from-mq-request';
import {MqMessage} from "./mq-message";

export class Step {
  code: string;
  expectedServiceRequestList: ExpectedServiceRequest[];
  relativeUrl: string;
  requestMethod: string;
  request: string;
  requestHeaders: string;
  expectedResponse: string;
  expectedResponseIgnore: boolean;
  expectedStatusCode: number;
  jsonXPath: string;
  requestBodyType = 'JSON';
  usePolling: boolean;
  pollingJsonXPath: string;
  mockServiceResponseList: MockServiceResponse[];
  disabled: boolean;
  stepComment: string;
  savedValuesCheck: any = {};
  stepParameterSetList: StepParameterSet[] = [];
  responseCompareMode = 'JSON';
  formDataList: FormData[] = [];
  multipartFormData: boolean;
  mqMessages: MqMessage[];
  jsonCompareMode = 'NON_EXTENSIBLE';
  script: string;
  numberRepetitions: string;
  parseMockRequestUrl: string;
  parseMockRequestXPath: string;
  parseMockRequestScenarioVariable: string;
  timeoutMs: string;
  mqMockResponseList: MqMock[];
  expectedMqRequestList: ExpectedMqRequest[];
  sqlDataList: SqlData[];
  scenarioVariableFromMqRequestList: ScenarioVariableFromMqRequest[];
  stepMode: string;

  mqOutputQueueName: string;
  mqInputQueueName: string;
  mqTimeoutMs: string;

}
