import {Stand} from './stand';
import {AmqpBroker} from './amqp-broker';

export class Project {
  name: string;
  beforeScenarioId: Number;
  afterScenarioId: Number;
  projectCode: string;
  standList: Stand[];
  stand: Stand;
  useRandomTestId: boolean;
  testIdHeaderName: string;
  amqpBroker: AmqpBroker;
}
