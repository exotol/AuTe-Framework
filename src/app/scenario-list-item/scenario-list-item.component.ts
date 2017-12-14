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
  @Input() isLinkTitleScenario = true;
  @Output() onStateChange = new EventEmitter<any>();

  stepResultList: StepResult[];

  state = 'none';
  showResultDetails = false;

  constructor(
    private scenarioService: ScenarioService
  ) { }

  ngOnInit() {
    this.scenario._selected = false; // this.scenario.lastRunFailures !== 0;
  }

  stateChanged() {
    this.onStateChange.emit({state: this.state})
  }

  runScenario() {
    if (this.state !== 'executing') {
      this.state = 'executing';
      this.scenario.failed = null;
      this.stateChanged();
      this.scenarioService.run(this.scenario)
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
}
