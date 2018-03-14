import {Component, Input, OnInit} from '@angular/core';
import {MqMockResponse} from '../model/mq-mock-response';

@Component({
  selector: 'app-mq-mock-response',
  templateUrl: './mq-mock-response.component.html'
})
export class MqMockResponseComponent implements OnInit {

  @Input()
  mqMockResponse: MqMockResponse;

  constructor() { }

  ngOnInit() {
  }

}
