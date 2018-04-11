import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {StepResult} from '../model/step-result';
import {StartScenarioInfo} from '../model/start-scenario-info';

@Component({
  selector: 'app-scenario-list-item',
  templateUrl: './scenario-list-item.component.html',
  styleUrls: ['./scenario-list-item.component.css']
})
export class ScenarioListItemComponent implements OnInit {

  @Input()
  scenario: Scenario;
  @Input()
  projectCode: string;
  @Input()
  isLinkTitleScenario = true;
  @Output() onStateChange = new EventEmitter<any>();

  stepResultList: StepResult[];

  state = 'none';
  showResultDetails = false;
  startScenarioInfo: StartScenarioInfo;
  resultCheckTimeout = 500;
  executedSteps: number;
  totalSteps: number;

  constructor(
    private scenarioService: ScenarioService
  ) { }

  ngOnInit() {
    this.scenario._selected = false;
  }

  stateChanged() {
    this.onStateChange.emit({state: this.state, executedSteps: this.executedSteps, totalSteps: this.totalSteps})
  }

  runScenario() {
    if (this.state !== 'executing') {
      this.executedSteps = 0;
      this.stepResultList = [];
      // this.totalSteps = 0;
      this.state = 'starting';
      this.stateChanged();
      this.scenarioService.run(this.projectCode, this.scenario)
        .subscribe(startScenarioInfo => {
          this.startScenarioInfo = startScenarioInfo;
          this.state = 'executing';
          this.stateChanged();
          this.checkState();
        });
    }
  }

  checkState() {
    if (this.startScenarioInfo) {
      this.scenarioService.executionStatus(this.startScenarioInfo.runningUuid)
        .subscribe(executionResult => {
          if (executionResult.scenarioResultList && executionResult.scenarioResultList[0]) {
            const scenarioResult = executionResult.scenarioResultList[0];
            let allSteps:StepResult [] = scenarioResult.stepResultList;
            if (allSteps.length > 0) {
              if (this.stepResultList.length == 0) {
                this.stepResultList.push(allSteps[0]);
              }
              this.stepResultList[this.stepResultList.length - 1] = allSteps[this.stepResultList.length - 1];
              for (let i = this.stepResultList.length; i < allSteps.length; i++) {
                this.stepResultList.push(allSteps[i]);
              }
            }

            this.scenario.failed = allSteps != null && allSteps.filter(result => result.result === 'Fail').length > 0;

            this.executedSteps = allSteps.filter(stepResult => stepResult.editable).length;
            this.totalSteps = scenarioResult.totalSteps;

            if (executionResult.finished) {
              this.state = 'finished';
            } else {
              setTimeout(() => {
                this.checkState();
              }, this.resultCheckTimeout);
            }
            this.stateChanged();
          } else {
            setTimeout(() => {
              this.checkState();
            }, this.resultCheckTimeout);
          }
        }, error => {
          if (error.message && !error.message.contains('Unexpected end of JSON input')) {
            setTimeout(() => {
              this.checkState();
            }, this.resultCheckTimeout);
          }
        });
    }
  }

  resultDetailsToggle() {
    this.showResultDetails = !this.showResultDetails;
  }

  stop(): void {
    if (this.startScenarioInfo) {
      this.scenarioService
        .stop(this.startScenarioInfo.runningUuid)
        .subscribe();
    }
  }

  getMapStyleForScenario(): string {
    if (this.state !== 'executing' && this.state !== 'starting') {
      if (this.scenario.failed === true) { return 'failedScenario'; }
      if (this.scenario.failed === false) { return 'passedScenario'; }
    }
    return '';
  }
}
