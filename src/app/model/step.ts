import {ExpectedServiceRequest} from './expected-service-request';
import {MockServiceResponse} from './mock-service-response';
import {StepParameterSet} from './step-parameter-set';
import {FormData} from './form-data';
import {MqMockResponse} from './mq-mock-response';
import {ExpectedMqRequest} from './expected-mq-request';
import {SqlData} from './sql-data';
import {ScenarioVariableFromMqRequest} from './scenario-variable-from-mq-request';
import {NameValueProperty} from './name-value-property';

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
  mqName: string;
  mqMessage: string;
  mqPropertyList: NameValueProperty[];
  jsonCompareMode = 'NON_EXTENSIBLE';
  script: string;
  numberRepetitions: string;
  parseMockRequestUrl: string;
  parseMockRequestXPath: string;
  parseMockRequestScenarioVariable: string;
  timeoutMs: string;
  mqMockResponseList: MqMockResponse[];
  expectedMqRequestList: ExpectedMqRequest[];
  sqlDataList: SqlData[];
  scenarioVariableFromMqRequestList: ScenarioVariableFromMqRequest[];
  stepMode = 'REST';

  mqOutputQueueName: string;
  mqInputQueueName: string;
  mqTimeoutMs: string;

  _changed: boolean = false;
}
