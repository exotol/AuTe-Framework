import {Step} from './step';

export class StepResult {
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
}
