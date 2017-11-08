import {Scenario} from './scenario';
import {ScenarioGroup} from './scenario-group';
import {Stand} from './stand';
import {AmqpBroker} from './amqp-broker';

export class Project {
  id: number;
  name: string;
  beforeScenarioId: Number;
  afterScenarioId: Number;
  projectCode: string;
  scenarioList: Scenario[];
  scenarioGroups: ScenarioGroup[];
  standList: Stand[];
  stand: Stand;
  useRandomTestId: boolean;
  testIdHeaderName: string;
  amqpBroker: AmqpBroker;
}
