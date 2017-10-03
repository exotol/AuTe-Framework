import { Component, OnInit } from '@angular/core';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ScenarioGroup} from '../model/scenario-group';
import {Stand} from '../model/stand';
import {Scenario} from '../model/scenario';
import {ToastOptions, ToastyService} from 'ng2-toasty';

@Component({
  selector: 'app-project-settings',
  templateUrl: './project-settings.component.html',
  styles: [
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
    '.row {margin-bottom: 4px;}',
    'input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }'
  ]
})
export class ProjectSettingsComponent implements OnInit {

  project: Project;

  tab = 'details';
  scenarioList: Scenario[];

  private toastOptions: ToastOptions = {
    title: 'Updated',
    msg: 'Project settings updated',
    showClose: true,
    timeout: 5000,
    theme: 'bootstrap'
  };

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private toastyService: ToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findOne(+params.get('id')))
      .subscribe(value => {
        this.project = value;

        this.projectService.findScenariosByProject(this.project.id)
          .subscribe(scenarioList => this.scenarioList = scenarioList);
      });
  }

  save(): void {
    this.projectService.save(this.project)
      .subscribe(value => {
        this.project = value;
        this.toastyService.success(this.toastOptions)
      });
  }

  selectTab(tabName: string): boolean {
    this.tab = tabName;
    return false;
  }

  removeScenarioGroup(scenarioGroup: ScenarioGroup): void {
    const indexToRemove = this.project.scenarioGroups.indexOf(scenarioGroup);
    if (indexToRemove > -1) {
      this.project.scenarioGroups.splice(indexToRemove, 1);
    }
  }

  addScenarioGroup(): void {
    if (!this.project.scenarioGroups) {
      this.project.scenarioGroups = [];
    }
    this.project.scenarioGroups.push(new ScenarioGroup());
  }

  selectAsDefaultStand(stand: Stand): void {
    this.project.stand = stand;
  }

  addStand(): void {
    if (!this.project.standList) {
      this.project.standList = [];
    }
    this.project.standList.push(new Stand());
  }

  removeStand(stand: Stand): void {
    const indexToRemove = this.project.standList.indexOf(stand);
    if (indexToRemove > -1) {
      this.project.standList.splice(indexToRemove, 1);
    }
  }
}
