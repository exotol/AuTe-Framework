import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../service/project.service';
import {Project} from '../model/project';
import {ActivatedRoute, ParamMap} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {Scenario} from '../model/scenario';
import {ScenarioGroup} from '../model/scenario-group';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit {

  project: Project;
  scenarioList: Scenario[];

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findOne(+params.get('id')))
      .subscribe(value => this.project = value);

    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findScenariosByProject(+params.get('id')))
      .subscribe(value => this.scenarioList = value);
  }

  selectGroup(scenarioGroup: ScenarioGroup) {
    // TODO: filter
  }

  downloadAsExcel() {
    // TODO
    // formaction="${pageContext.request.contextPath}/project/${project.id}/export-to-excel"
  }

  downloadSelectedAsYaml() {
    // TODO
    // formaction="${pageContext.request.contextPath}/project/${project.id}/export-selected-to-yaml"
  }
}
