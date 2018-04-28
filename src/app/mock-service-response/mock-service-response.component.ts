import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HeaderItem, MockServiceResponse} from '../model/mock-service-response';

@Component({
  selector: 'app-mock-service-response',
  templateUrl: './mock-service-response.component.html'
})
export class MockServiceResponseComponent implements OnInit {

  @Input()
  mockServiceResponse: MockServiceResponse;

  @Output()
  onDelete = new EventEmitter<any>();

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

  deleteResponse() {
    this.onDelete.emit();
  }
}
