import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../service/project.service';
import {Project} from '../model/project';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {Scenario} from '../model/scenario';
import {ScenarioGroup} from '../model/scenario-group';
import {PlatformLocation} from '@angular/common';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit {

  project: Project;
  scenarioList: Scenario[];
  filter = new Scenario();

  constructor(
    public platformLocation: PlatformLocation,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) { }

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

  selectGroup(scenarioGroup: ScenarioGroup): boolean {
    this.filter = new Scenario();
    this.filter.scenarioGroup = scenarioGroup;

    this.router.navigate([], scenarioGroup != null ? {queryParams: {scenarioGroupId: scenarioGroup.id}} : {})
      .then(value => console.log('navigate then:', value));

    return false;
  }

  downloadAsExcel() {
    // TODO
    // formaction="${pageContext.request.contextPath}/project/${project.id}/export-to-excel"
  }

  downloadSelectedAsYaml() {
    // TODO
    // formaction="${pageContext.request.contextPath}/project/${project.id}/export-selected-to-yaml"
  }

  isDisplayScenario(scenario: Scenario) {
    return this.filter == null || this.filter.scenarioGroup == null ||
      (scenario != null && scenario.scenarioGroup != null &&
          scenario.scenarioGroup.id === this.filter.scenarioGroup.id
      );
  }
}
