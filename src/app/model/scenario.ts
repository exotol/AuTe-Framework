import {StepResult} from './step-result';
import {Step} from './step';
import {Stand} from './stand';

export class Scenario {
  id: number;
  projectCode: string;
  projectName: string;
  name: string;
  scenarioGroup: String;
  stepResults: StepResult[];
  lastRunFailures: number;

  beforeScenarioIgnore: boolean;
  beforeScenarioId: Number;
  afterScenarioIgnore: boolean;
  afterScenarioId: Number;

  stepList: Step[];
  stand: Stand;

  _selected = false;
}
