import {ScenarioGroup} from './scenario-group';
import {StepResult} from './step-result';
import {Step} from './step';
import {Stand} from './stand';

export class Scenario {
  id: number;
  projectId: number;
  projectName: string;
  projectStand: Stand;
  name: string;
  scenarioGroup: ScenarioGroup;
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
