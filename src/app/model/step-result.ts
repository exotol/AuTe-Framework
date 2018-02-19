import {Step} from './step';

export class StepResult {
  testId: String;
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
  cookies: String;
}
