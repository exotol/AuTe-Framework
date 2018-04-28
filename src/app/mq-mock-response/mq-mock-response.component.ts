import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MqMockResponse} from '../model/mq-mock-response';

@Component({
  selector: 'app-mq-mock-response',
  templateUrl: './mq-mock-response.component.html'
})
export class MqMockResponseComponent {

  @Input()
  mqMockResponse: MqMockResponse;

  @Output()
  onDelete = new EventEmitter<any>();

  deleteMqMockResponse() {
    this.onDelete.emit();
  }
}
