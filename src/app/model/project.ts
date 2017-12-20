import {Stand} from './stand';
import {AmqpBroker} from './amqp-broker';

export class Project {
  name: string;
  beforeScenarioPath: String;
  afterScenarioPath: String;
  code: string;
  stand: Stand;
  useRandomTestId: boolean;
  testIdHeaderName: string;
  amqpBroker: AmqpBroker;
  groupList: String[];
}
