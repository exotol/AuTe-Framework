import { Component, OnInit } from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {CustomToastyService} from '../service/custom-toasty.service';

@Component({
  selector: 'app-scenario-settings',
  templateUrl: './scenario-settings.component.html',
  styles: [
    'input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }',
    '.row { margin-bottom: 7px; }'
  ]
})
export class ScenarioSettingsComponent implements OnInit {

  scenario: Scenario;
  project: Project;
  scenarioList: Scenario[];

  constructor(
    private route: ActivatedRoute,
    private scenarioService: ScenarioService,
    private projectService: ProjectService,
    private customToastyService: CustomToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findOne(+params.get('id')))
      .subscribe(value => {
        this.scenario = value;

        this.projectService.findOne(this.scenario.projectCode)
          .subscribe(project => {
            this.project = project;

            this.projectService.findScenariosByProject(this.project.code)
              .subscribe(scenarioList => this.scenarioList = scenarioList);
          });
      });
  }

  save(): void {
    const toasty = this.customToastyService.saving();
    this.scenarioService.saveOne(this.scenario)
      .subscribe(value => {
        this.scenario = value;
        this.customToastyService.success('Сохранено', 'Сценарий сохранен');
      }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
  }
}
