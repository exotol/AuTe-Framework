import {Step} from './step';
import {RequestData} from '../request-data';

export class StepResult {
  testId: string;
  step: Step;
  result: string;

  details: string;
  expected: string;
  actual: string;
  requestUrl: string;
  requestBody: string;
  pollingRetryCount: number;
  savedParameters: string;
  description: string;
  editable: boolean;
  cookies: string;
  requestDataList: RequestData[];
}
