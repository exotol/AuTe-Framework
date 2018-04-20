import {Component, DoCheck, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Step} from '../model/step';
import {MockServiceResponse} from '../model/mock-service-response';
import {ToastOptions, ToastyService} from 'ng2-toasty';
import {ExpectedServiceRequest} from '../model/expected-service-request';
import {FormData} from '../model/form-data';
import {MqMockResponse} from '../model/mq-mock-response';
import {ExpectedMqRequest} from '../model/expected-mq-request';
import {SqlData} from '../model/sql-data';
import {ScenarioVariableFromMqRequest} from '../model/scenario-variable-from-mq-request';
import {NameValueProperty} from '../model/name-value-property';
import {StepService} from '../service/step.service';

@Component({
  selector: 'app-step-item',
  templateUrl: './step-item.component.html',
  styles: [
    '.nav-tabs > li > a { padding: 3px 7px; }',
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
    '.row { margin-bottom: 5px; }',
    '.input-group-btn > select { padding: 0; width: 85px; border-right: 0; }',
    '.input-group-btn:first-child > select { border-top-left-radius: 5px; border-bottom-left-radius: 5px; }',
    '.request-body-field { display: flex; margin-bottom: 10px; }',
    '.request-body-field > * { margin: 0 5px; min-width: 0; }',
    '.request-body-field > .request-body-field__name { margin-left: 0; min-width: 25%; flex: 0 0; }',
    '.request-body-field > .request-body-field__type { min-width: 80px; flex: 0 0; }',
    '.request-body-field > .request-body-field__remove { margin-right: 0; flex: 0 0; }'
  ]
})
export class StepItemComponent implements OnInit , DoCheck {

  @Input()
  step: Step;
  @Input()
  showUpDownDeleteCloneButtons: Boolean;
  @Input()
  showDiffOnChange: Boolean;

  @Output() onDeleteClick = new EventEmitter<any>();
  @Output() onUpClick = new EventEmitter<any>();
  @Output() onDownClick = new EventEmitter<any>();
  @Output() onCloneClick = new EventEmitter<any>();
  @Output() onChange = new EventEmitter<any>();

  Object = Object;

  oldStep: Step;

  tab = 'details';
  toastOptions: ToastOptions = {
    title: 'Warning',
    msg: 'Checked variable already exists',
    showClose: true,
    timeout: 15000,
    theme: 'bootstrap'
  };

  changed: Boolean = false;

  constructor(
    private toastyService: ToastyService,
    private stepService: StepService
  ) { }

  ngOnInit() {
    if (this.step && !this.step.stepMode) {
      this.step.stepMode = 'REST';
    }
    this.oldStep = this.stepService.copyStep(this.step);
  }

  selectTab(tabName: string) {
    if (this.tab === tabName) {
      this.tab = 'none';
    } else {
      this.tab = tabName;
    }
    return false;
  }

  addMockServiceResponse() {
    if (!this.step.mockServiceResponseList) {
      this.step.mockServiceResponseList = [];
    }
    this.step.mockServiceResponseList.push(new MockServiceResponse());
  }

  addMqMockResponse() {
    if (!this.step.mqMockResponseList) {
      this.step.mqMockResponseList = [];
    }
    this.step.mqMockResponseList.push(new MqMockResponse());
  }

  removeMockServiceResponse(mockServiceResponse: MockServiceResponse) {
    if (confirm('Confirm: remove mock response')) {
      const indexToRemove = this.step.mockServiceResponseList.indexOf(mockServiceResponse);
      if (indexToRemove > -1) {
        this.step.mockServiceResponseList.splice(indexToRemove, 1);
      }
    }
  }

  addSqlData() {
    if (!this.step.sqlDataList) {
      this.step.sqlDataList = [];
    }
    const sqlData = new SqlData();
    sqlData.sqlReturnType = 'MAP';
    this.step.sqlDataList.push(sqlData);
  }

  removeSqlData(data: SqlData) {
    if (confirm('Confirm: remove sql data')) {
      const indexToRemove = this.step.sqlDataList.indexOf(data);
      if (indexToRemove > -1) {
        this.step.sqlDataList.splice(indexToRemove, 1);
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

  addExpectedMqRequest() {
    if (!this.step.expectedMqRequestList) {
      this.step.expectedMqRequestList = [];
    }
    this.step.expectedMqRequestList.push(new ExpectedMqRequest());
  }

  removeExpectedServiceRequest(expectedServiceRequest: ExpectedServiceRequest) {
    if (confirm('Confirm: remove expected service request')) {
      const indexToRemove = this.step.expectedServiceRequestList.indexOf(expectedServiceRequest);
      if (indexToRemove > -1) {
        this.step.expectedServiceRequestList.splice(indexToRemove, 1);
      }
    }
  }

  removeExpectedMqRequest(expectedMqRequest: ExpectedMqRequest) {
    if (confirm('Confirm: remove expected MQ request')) {
      const indexToRemove = this.step.expectedMqRequestList.indexOf(expectedMqRequest);
      if (indexToRemove > -1) {
        this.step.expectedMqRequestList.splice(indexToRemove, 1);
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
    if (!this.step.formDataList) {
      this.step.formDataList = [];
    }
    this.step.formDataList.push(new FormData());
  }

  removeMqMockResponse(mqMockResponse: MqMockResponse) {
    if (confirm('Confirm: remove MQ mock response')) {
      const indexToRemove = this.step.mqMockResponseList.indexOf(mqMockResponse);
      if (indexToRemove > -1) {
        this.step.mqMockResponseList.splice(indexToRemove, 1);
      }
    }
  }

  removeVariableFromMq(variableFromMq: ScenarioVariableFromMqRequest) {
    const indexToRemove = this.step.scenarioVariableFromMqRequestList.indexOf(variableFromMq);
    if (indexToRemove > -1) {
      this.step.scenarioVariableFromMqRequestList.splice(indexToRemove, 1);
    }
  }

  addVariableFromMq() {
    if (!this.step.scenarioVariableFromMqRequestList) {
      this.step.scenarioVariableFromMqRequestList = [];
    }
    this.step.scenarioVariableFromMqRequestList.push(new ScenarioVariableFromMqRequest());
  }

  addMqProperty() {
    if (!this.step.mqPropertyList) {
      this.step.mqPropertyList = [];
    }
    this.step.mqPropertyList.push(new NameValueProperty());
  }

  removeMqProperty(property: NameValueProperty) {
    const indexToRemove = this.step.mqPropertyList.indexOf(property);
    if (indexToRemove > -1) {
      this.step.mqPropertyList.splice(indexToRemove, 1);
    }
  }

  ngDoCheck(): void {
    if(!this.stepService.equals(this.step, this.oldStep)){
      this.changed = true;
      this.onChange.emit(this.step);
    }
  }

  resetChangeState(): void {
    this.oldStep = this.stepService.copyStep(this.step);
    this.changed = false;
  }

}
