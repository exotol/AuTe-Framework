import {Component, Input, OnInit} from '@angular/core';
import {MockServiceResponse} from '../model/mock-service-response';

@Component({
  selector: 'app-mock-service-response',
  templateUrl: './mock-service-response.component.html'
})
export class MockServiceResponseComponent implements OnInit {

  @Input()
  mockServiceResponse: MockServiceResponse;

  constructor() { }

  ngOnInit() {
  }
}
