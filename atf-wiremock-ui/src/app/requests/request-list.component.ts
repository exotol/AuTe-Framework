import { Component, OnInit } from '@angular/core';
import {WireMockService} from '../../service/wire-mock.service';
import {RequestList} from '../../model/request-list';
import {Request} from '../../model/request';

@Component({
  selector: 'app-requests',
  templateUrl: './request-list.component.html',
  styles: [
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}'
  ]
})
export class RequestListComponent implements OnInit {

  requestList: RequestList;
  selectedRequest: Request;
  tab = 'summary';
  requestLimit = 30;

  constructor(
    public wireMockService: WireMockService
  ) { }

  ngOnInit() {
    this.updateRequestList();
  }

  updateRequestList() {
    this.selectedRequest = null;
    this.requestList = null;
    this.wireMockService
      .getRequestList(this.requestLimit)
      .subscribe(value => this.requestList = value);
  }

  clearRequestList() {
    if (confirm('Confirm: clear request list')) {
      this.selectedRequest = null;
      this.requestList = null;
      this.wireMockService.clearRequestList().subscribe(() => this.updateRequestList());
    }
  }

  select(request: Request) {
    this.selectedRequest = request;
    return false;
  }

  selectTab(newTab: string) {
    this.tab = newTab;
    return false;
  }
}