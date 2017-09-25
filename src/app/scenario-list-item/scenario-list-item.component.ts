import {Component, Input, OnInit} from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {StepResult} from '../model/step-result';

@Component({
  selector: 'app-scenario-list-item',
  templateUrl: './scenario-list-item.component.html'
})
export class ScenarioListItemComponent implements OnInit {

  @Input()
  scenario: Scenario;
  stepResultList: StepResult[];

  state = 'none';

  constructor(
    private scenarioService: ScenarioService
  ) { }

  ngOnInit() {
  }

  runScenario() {
    if (confirm('Confirm: run scenario')) {
      this.state = 'executing';
      this.scenarioService.run(this.scenario)
        .subscribe(value => { this.stepResultList = value; this.state = 'finished'; });
    }
  }
}
