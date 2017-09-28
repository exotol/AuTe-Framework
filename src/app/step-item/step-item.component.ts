import {Component, Input, OnInit} from '@angular/core';
import {Step} from '../model/step';
import {MockServiceResponse} from '../model/mock-service-response';

@Component({
  selector: 'app-step-item',
  templateUrl: './step-item.component.html',
  styles: [
    '.nav-tabs > li > a { padding-top: 3px; padding-bottom: 3px; }',
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
    '.row { margin-bottom: 5px; }',
    '.input-group-btn > select { padding: 0; width: 85px; border-top-left-radius: 5px; border-bottom-left-radius: 5px; border-right: 0; }'
  ]
})
export class StepItemComponent implements OnInit {

  @Input()
  step: Step;

  tab = 'summary';

  constructor() { }

  ngOnInit() {
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  addMockServiceResponse() {
    this.step.mockServiceResponseList.push(new MockServiceResponse());
  }

  removeMockServiceResponse(mockServiceResponse: MockServiceResponse) {
    if (confirm('Confirm: remove mock response')) {
      const indexToRemove = this.step.mockServiceResponseList.indexOf(mockServiceResponse);
      if (indexToRemove > -1) {
        this.step.mockServiceResponseList.splice(indexToRemove, 1);
      }
    }
  }
}
