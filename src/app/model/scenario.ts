import {ScenarioGroup} from './scenario-group';
import {Stand} from './stand';

export class Scenario {
  id: number;
  projectId: number;
  projectName: string;
  projectStand: Stand;
  name: string;
  scenarioGroup: ScenarioGroup;

  beforeScenarioIgnore: boolean;
  beforeScenarioId: Number;
  afterScenarioIgnore: boolean;
  afterScenarioId: Number;

  failed: boolean;

  _selected = false;
}
