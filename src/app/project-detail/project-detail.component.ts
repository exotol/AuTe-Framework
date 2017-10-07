import {AfterContentChecked, Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ProjectService} from '../service/project.service';
import {Project} from '../model/project';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {Scenario} from '../model/scenario';
import {ScenarioGroup} from '../model/scenario-group';
import {Globals} from '../globals';
import {ScenarioListItemComponent} from '../scenario-list-item/scenario-list-item.component';
import { saveAs } from 'file-saver/FileSaver';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styles: ['input.select-all { width: 24px; height: 24px; margin: 0; vertical-align: middle; }']
})
export class ProjectDetailComponent implements OnInit, AfterContentChecked {

  project: Project;
  scenarioList: Scenario[];
  filter: Scenario;
  selectAllFlag = false;
  failCount = 0;
  Math: any;
  newScenarioName = '';

  @ViewChildren(ScenarioListItemComponent) scenarioComponentList: QueryList<ScenarioListItemComponent>;
  executingStateExecuting = 0;
  executingStateFinished = 0;

  constructor(
    public globals: Globals,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {
    this.Math = Math;
  }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findOne(+params.get('id')))
      .subscribe(value => {
        this.project = value;

        this.route
          .queryParams
          .subscribe(params => {
            if (+params['scenarioGroupId']) {
              this.filter = new Scenario();
              this.filter.scenarioGroup =
                this.project.scenarioGroups
                  .find(scenarioGroup => scenarioGroup.id === +params['scenarioGroupId']);
            }
          });
      });

    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findScenariosByProject(+params.get('id')))
      .subscribe(value => this.scenarioList = value);
  }

  ngAfterContentChecked(): void {
    this.updateFailCountSum();
  }

  selectGroup(scenarioGroup?: ScenarioGroup): boolean {
    this.filter = new Scenario();
    this.filter.scenarioGroup = scenarioGroup;

    this.router.navigate([], {queryParams: {scenarioGroupId: scenarioGroup ? scenarioGroup.id : -1}});
    this.updateFailCountSum();

    return false;
  }

  selectAllGroups(): boolean {
    this.filter = null;

    this.router.navigate([]);
    this.updateFailCountSum();

    return false;
  }

  downloadSelectedAsYaml() {
    const selectedScenarios = this.scenarioList
      .filter(value => this.isSelectedScenario(value))
      .map(value => value.id);
    this.projectService.downloadYaml(this.project, selectedScenarios)
      .subscribe(res => saveAs(res, 'PROJECT_' + this.project.projectCode + '.yml'))
  }

  isSelectedScenario(scenario: Scenario) {
    return scenario && scenario._selected && this.isDisplayScenario(scenario);
  }

  isDisplayScenario(scenario: Scenario) {
    return !this.filter ||
      (!this.filter.scenarioGroup && !scenario.scenarioGroup) ||
      (this.filter.scenarioGroup && scenario && scenario.scenarioGroup &&
          scenario.scenarioGroup.id === this.filter.scenarioGroup.id
      );
  }

  executeSelectedScenarios() {
    this.scenarioComponentList
      .filter(item => item.scenario._selected && this.isDisplayScenario(item.scenario))
      .forEach(value => value.runScenario());
  }

  selectAll() {
    this.selectAllFlag = !this.selectAllFlag;
    this.scenarioComponentList.forEach(item => item.scenario._selected = this.selectAllFlag);
  }

  updateFailCountSum() {
    if (this.scenarioList) {
      this.failCount = this.scenarioList
        .filter(item => this.isDisplayScenario(item))
        .map(value => value.lastRunFailures)
        .reduce((previousValue, currentValue) => previousValue + currentValue, 0);
    } else {
      this.failCount = 0;
    }
    return this.failCount;
  }

  updateExecutionStatus() {
    this.executingStateExecuting = this.scenarioComponentList
      .filter(item => item.state === 'executing')
      .length;
    this.executingStateFinished = this.scenarioComponentList
      .filter(item => item.state === 'finished')
      .length;
  }

  // noinspection JSUnusedLocalSymbols
  onStateChange(event: any, scenario: Scenario) {
    this.updateExecutionStatus();
  }

  saveNewScenario() {
    const newScenario = new Scenario();
    newScenario.name = this.newScenarioName;

    this.projectService.createScenario(this.project, newScenario)
      .subscribe(savedScenario => {
        this.scenarioList.push(savedScenario);
        this.newScenarioName = '';
      });
  }
}
