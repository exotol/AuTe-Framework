import {MqMockResponse} from "./mq-mock-response";

export class MqMock {
  code: string;
  responseBody: string;
  sourceQueueName: string;
  httpUrl: string;
  responses: MqMockResponse[] = [];
  xpath: string;
}
