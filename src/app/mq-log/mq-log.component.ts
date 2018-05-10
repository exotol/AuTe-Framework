import { Component, OnInit } from '@angular/core';
import {MqLogItem} from '../../model/mq-log-item';
import {MqMockerService} from '../../service/mq-mocker-service';

@Component({
  selector: 'app-mq-log',
  templateUrl: './mq-log.component.html',
  styles: [
    '.clear-line { height: 30px; border-top-color: white; cursor: default; }'
  ]
})
export class MqLogComponent implements OnInit {

  mqLog: MqLogItem[] = [];
  selectedRequest: MqLogItem = null;
  tab = 'summary';
  sourceTextLimit = 1000;

  requestLimit = 30;

  constructor(
    public mqMockerService: MqMockerService
  ) { }

  ngOnInit() {
    this.updateList();
  }

  updateList() {
    this.sourceTextLimit = 1000;
    this.selectedRequest = null;
    this.mqMockerService.getRequestList(this.requestLimit)
      .subscribe(value => {
        this.mqLog = value;
      });
  }

  clear() {
    this.mqMockerService
      .clear()
      .subscribe(() => {
        this.updateList();
      });
  }

  select(mqLogItem: MqLogItem) {
    this.selectedRequest = mqLogItem;
    this.sourceTextLimit = 1000;
  }
}
