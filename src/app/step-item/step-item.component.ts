import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Step} from '../model/step';
import {MockServiceResponse} from '../model/mock-service-response';
import {ToastOptions, ToastyService} from 'ng2-toasty';
import {ExpectedServiceRequest} from '../model/expected-service-request';
import {FormData} from '../model/form-data';

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
  @Input()
  showUpDownDeleteCloneButtons: Boolean;

  @Output() onDeleteClick = new EventEmitter<any>();
  @Output() onUpClick = new EventEmitter<any>();
  @Output() onDownClick = new EventEmitter<any>();
  @Output() onCloneClick = new EventEmitter<any>();

  Object = Object;

  tab = 'summary';
  toastOptions: ToastOptions = {
    title: 'Warning',
    msg: 'Checked variable already exists',
    showClose: true,
    timeout: 15000,
    theme: 'bootstrap'
  };

  constructor(
    private toastyService: ToastyService
  ) { }

  ngOnInit() {
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  addMockServiceResponse() {
    if (!this.step.mockServiceResponseList) {
      this.step.mockServiceResponseList = [];
    }
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

  updateCheckedValueName(oldCheckedValueName: string): boolean {
    let newName: string;
    if (newName = prompt('New parameter name', oldCheckedValueName)) {
      if (newName !== oldCheckedValueName) {
        if (!this.step.savedValuesCheck[newName]) {
          Object.defineProperty(this.step.savedValuesCheck, newName,
            Object.getOwnPropertyDescriptor(this.step.savedValuesCheck, oldCheckedValueName));
          delete this.step.savedValuesCheck[oldCheckedValueName];
        } else {
          this.toastyService.warning(this.toastOptions);
        }
      }
    }
    return false;
  }

  addCheckedValue() {
    let newName: string;
    if (newName = prompt('New parameter name')) {
      if (!this.step.savedValuesCheck) {
        this.step.savedValuesCheck = {};
      }
      if (!this.step.savedValuesCheck[newName]) {
        this.step.savedValuesCheck[newName] = '';
      } else {
        this.toastyService.warning(this.toastOptions);
      }
    }
  }

  removeCheckedValue(checkedValueName: string) {
    delete this.step.savedValuesCheck[checkedValueName];
  }

  addExpectedServiceRequest() {
    if (!this.step.expectedServiceRequestList) {
      this.step.expectedServiceRequestList = [];
    }
    this.step.expectedServiceRequestList.push(new ExpectedServiceRequest());
  }

  removeExpectedServiceRequest(expectedServiceRequest: ExpectedServiceRequest) {
    if (confirm('Confirm: remove expected service request')) {
      const indexToRemove = this.step.expectedServiceRequestList.indexOf(expectedServiceRequest);
      if (indexToRemove > -1) {
        this.step.expectedServiceRequestList.splice(indexToRemove, 1);
      }
    }
  }

  deleteStep() {
    this.onDeleteClick.emit({step: this.step});
  }

  disabledToggle() {
    this.step.disabled = !this.step.disabled;
  }

  upStep() {
    this.onUpClick.emit();
  }

  downStep() {
    this.onDownClick.emit();
  }

  cloneStep() {
    this.onCloneClick.emit();
  }

  removeFormData(formData: FormData) {
    const indexToRemove = this.step.formDataList.indexOf(formData);
    if (indexToRemove > -1) {
      this.step.formDataList.splice(indexToRemove, 1);
    }
  }

  addFormData() {
    this.step.formDataList.push(new FormData());
  }
}
