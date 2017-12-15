import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {StepResult} from '../model/step-result';

@Component({
  selector: 'app-scenario-list-item',
  templateUrl: './scenario-list-item.component.html',
  styleUrls: ['./scenario-list-item.component.css']
})
export class ScenarioListItemComponent implements OnInit {

  @Input()
  scenario: Scenario;
  @Input()
  projectCode: String;
  @Input()
  isLinkTitleScenario = true;
  @Output() onStateChange = new EventEmitter<any>();

  stepResultList: StepResult[];

  state = 'none';
  showResultDetails = false;

  constructor(
    private scenarioService: ScenarioService
  ) { }

  ngOnInit() {
    this.scenario._selected = false;
  }

  stateChanged() {
    this.onStateChange.emit({state: this.state})
  }

  runScenario() {
    if (this.state !== 'executing') {
      this.state = 'executing';
      this.stateChanged();
      this.scenarioService.run(this.projectCode, this.scenario)
        .subscribe(stepResultList => {
          this.stepResultList = stepResultList;
          this.state = 'finished';
          this.stateChanged();
          this.scenario.failed = stepResultList.filter(result => result.result === 'Fail').length > 0;
        });
    }
  }

  resultDetailsToggle() {
    this.showResultDetails = !this.showResultDetails;
  }

  getMapStyleForScenario(): string {
    if (this.state !== 'executing') {
      if (this.scenario.failed) { return 'failedScenario'; }
      if (this.scenario.failed === false) { return 'passedScenario'; }
    }
    return '';
  }
}
