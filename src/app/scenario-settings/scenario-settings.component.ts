import { Component, OnInit } from '@angular/core';
import {Scenario} from '../model/scenario';
import {ToastOptions, ToastyService} from 'ng2-toasty';
import {ScenarioService} from '../service/scenario.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {ScenarioGroup} from '../model/scenario-group';

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
  ScenarioSettingsComponent = ScenarioSettingsComponent;

  toastOptions: ToastOptions = {
    title: 'Updated',
    msg: 'Scenario updated',
    showClose: true,
    timeout: 5000,
    theme: 'bootstrap'
  };

  static scenarioGroupCompareFn(c1: ScenarioGroup, c2: ScenarioGroup): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  constructor(
    private route: ActivatedRoute,
    private scenarioService: ScenarioService,
    private projectService: ProjectService,
    private toastyService: ToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findOne(+params.get('id')))
      .subscribe(value => {
        this.scenario = value;

        this.projectService.findOne(this.scenario.projectId)
          .subscribe(project => {
            this.project = project;

            this.projectService.findScenariosByProject(this.project.id)
              .subscribe(scenarioList => this.scenarioList = scenarioList);
          });
      });
  }

  save(): void {
    this.scenarioService.saveOne(this.scenario)
      .subscribe(value => {
        this.scenario = value;
        this.toastyService.success(this.toastOptions);
      });
  }
}
