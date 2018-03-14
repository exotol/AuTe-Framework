export class Scenario {
  code: string;
  projectCode: string;
  projectName: string;
  name: string;
  scenarioGroup: string;

  beforeScenarioIgnore: boolean;
  afterScenarioIgnore: boolean;

  failed: boolean;

  _selected = false;
}
