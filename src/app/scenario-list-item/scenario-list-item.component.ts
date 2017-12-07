import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {StepResult} from '../model/step-result';

@Component({
  selector: 'app-scenario-list-item',
  templateUrl: './scenario-list-item.component.html',
  styles: [' input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }']
})
export class ScenarioListItemComponent implements OnInit {

  @Input()
  scenario: Scenario;
  @Input()
  projectCode: String;
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
        .subscribe(value => {
          this.stepResultList = value;
          this.state = 'finished';
          this.stateChanged();
          this.scenario.failed = value.filter(value2 => value2.result === 'Fail').length > 0;
        });
    }
  }

  resultDetailsToggle() {
    this.showResultDetails = !this.showResultDetails;
  }
}
