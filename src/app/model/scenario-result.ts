import {Scenario} from './scenario';
import {StepResult} from './step-result';

export class ScenarioResult {
  scenario: Scenario;
  stepResultList: StepResult[];
  totalSteps: number;
}
