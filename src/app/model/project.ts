import {Scenario} from './scenario';
import {ScenarioGroup} from './scenario-group';
import {Stand} from './stand';

export class Project {
  id: number;
  name: string;
  beforeScenario: Scenario;
  afterScenario: Scenario;
  projectCode: string;
  scenarios: Scenario[];
  scenarioGroups: ScenarioGroup[];
  standList: Stand[];
  stand: Stand;
  useRandomTestId: boolean;
  testIdHeaderName: string;
}
