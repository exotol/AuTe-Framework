import {Stand} from './stand';

export class Scenario {
  code: string;
  projectCode: string;
  projectName: string;
  name: string;
  scenarioGroup: String;
  lastRunFailures: number;

  beforeScenarioIgnore: boolean;
  afterScenarioIgnore: boolean;

  stand: Stand;

  _selected = false;

  getPath(): string {
    return (this.scenarioGroup ? this.scenarioGroup + '/' : '') + this.code;
  }
}
