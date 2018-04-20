import { Component, OnInit } from '@angular/core';
import {Scenario} from '../model/scenario';
import {ScenarioService} from '../service/scenario.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {CustomToastyService} from '../service/custom-toasty.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-scenario-settings',
  templateUrl: './scenario-settings.component.html',
  styleUrls: ['./scenario-settings.component.css']
})
export class ScenarioSettingsComponent implements OnInit {

  scenario: Scenario;
  project: Project;
  projectCode: string;
  sGroup: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private scenarioService: ScenarioService,
    private projectService: ProjectService,
    private customToastyService: CustomToastyService,
    private translate: TranslateService
  ) { }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.projectCode = params['projectCode'];

      this.scenarioService
        .findOne(this.projectCode, params['scenarioGroup'], params['scenarioCode'])
        .subscribe(value => {
          this.scenario = value;
          this.sGroup = this.scenario.scenarioGroup;
        });

      this.projectService
        .findOne(this.projectCode)
        .subscribe(project => this.project = project);
    });

  }

  save(): void {
    const toasty = this.customToastyService.saving();
    this.scenarioService.saveOne(this.project.code, this.scenario, this.sGroup)
      .subscribe(value => {
        this.scenario = value;
        const scenarioPath = this.scenario.scenarioGroup ? this.scenario.scenarioGroup : '';
        this.router.navigate([
          '/project', this.scenario.projectCode,
          'scenario', scenarioPath, this.scenario.code,
          'settings'], {replaceUrl: false});
        this.sGroup = this.scenario.scenarioGroup;
        this.customToastyService.success('Сохранено', 'Сценарий сохранен');
      },
        error => this.handleError(error),
        () => this.customToastyService.clear(toasty));
  }

  private handleError(error: any) {
    const message = JSON.parse(error._body).message;
    this.translate.get(message).subscribe(value => {
      this.customToastyService.error('Ошибка', value);
    });
  }
}
