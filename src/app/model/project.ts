import {Stand} from './stand';
import {AmqpBroker} from './amqp-broker';

export class Project {
  name: string;
  beforeScenarioPath: string;
  afterScenarioPath: string;
  code: string;
  stand: Stand;
  useRandomTestId: boolean;
  testIdHeaderName: string;
  amqpBroker: AmqpBroker;
  groupList: string[];
}
