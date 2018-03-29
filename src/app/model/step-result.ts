import {Step} from './step';

export class StepResult {
  testId: string;
  step: Step;
  result: string;

  details: string;
  expected: string;
  actual: string;
  diff: string;
  requestUrl: string;
  requestBody: string;
  pollingRetryCount: number;
  savedParameters: string;
  description: string;
  editable: boolean;
  cookies: string;
}
