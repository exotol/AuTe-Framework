import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MqMock} from '../model/mq-mock';
import {MqMockResponse} from "../model/mq-mock-response";

@Component({
  selector: 'app-mq-mock-response',
  templateUrl: './mq-mock-response.component.html'
})
export class MqMockResponseComponent implements OnInit{
  @Input()
  mqMockResponse: MqMock;

  @Output()
  onDelete = new EventEmitter<any>();

  severalResponses: boolean;

  ngOnInit() {
    this.severalResponses = this.mqMockResponse.responses.length > 1;
  }

  deleteMqMockResponse() {
    this.onDelete.emit();
  }

  addDestinationQueue() {
    this.mqMockResponse.responses.push(new MqMockResponse());
  }

  deleteDestinationQueue(index) {
    this.mqMockResponse.responses.splice(index, 1);
  }

  onToggleSeveralResponses() {
    if (!this.severalResponses) {
      this.mqMockResponse.responses.splice(1, this.mqMockResponse.responses.length - 1);
    }
  }
}
