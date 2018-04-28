import {NameValueProperty} from "./name-value-property";

export class MqMessage {
  queueName: string;
  message: string;
  properties: NameValueProperty[];
}
