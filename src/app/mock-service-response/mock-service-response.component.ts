import {Component, Input, OnInit} from '@angular/core';
import {HeaderItem, MockServiceResponse} from '../model/mock-service-response';

@Component({
  selector: 'app-mock-service-response',
  templateUrl: './mock-service-response.component.html'
})
export class MockServiceResponseComponent implements OnInit {

  @Input()
  mockServiceResponse: MockServiceResponse;

  tab = 'responseBody';

  constructor() { }

  ngOnInit() {
    if(!this.mockServiceResponse.headers){
      this.mockServiceResponse.headers = [];
    }
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  addHeader() {
    this.mockServiceResponse.headers.push(new HeaderItem());
  }

  deleteHeader(header: HeaderItem){
    this.mockServiceResponse.headers = this.mockServiceResponse.headers.filter(value => value !== header);
  }
}
