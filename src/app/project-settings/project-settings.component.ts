import { Component, OnInit } from '@angular/core';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Scenario} from '../model/scenario';
import {CustomToastyService} from '../service/custom-toasty.service';

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

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute,
    private customToastyService: CustomToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.projectService.findOne(params.get('projectCode')))
      .subscribe(value => {
        this.project = value;

        this.projectService.findScenariosByProject(this.project.code)
          .subscribe(scenarioList => this.scenarioList = scenarioList);
      });
  }

  save(): void {
    const toasty = this.customToastyService.saving('Сохранение...', 'Сохранение может занять некоторое время...');
    this.projectService.save(this.project)
      .subscribe(
        value => {
          this.project = value;
          this.customToastyService.success('Сохранено', 'Параметры проекта сохранены');
        },
        error => this.customToastyService.error('Ошибка', error),
        () => this.customToastyService.clear(toasty)
        );
  }

  selectTab(tabName: string): boolean {
    this.tab = tabName;
    return false;
  }

  removeScenarioGroup(group: string): void {
    const indexToRemove = this.project.groupList.indexOf(group);
    if (indexToRemove > -1) {
      this.project.groupList.splice(indexToRemove, 1);
    }
  }

  addGroup(): void {
    let newGroupName: string;
    if ((newGroupName = prompt('New group name')) && newGroupName && newGroupName.length > 0) {
      const toasty = this.customToastyService.saving('Сохранение группы...', 'Сохранение может занять некоторое время...');
      this.projectService.addNewGroup(this.project.code, newGroupName)
        .subscribe(
          groupList => {
            this.project.groupList = groupList;
            this.customToastyService.success('Сохранено', 'Новая группа создана');
          },
          error => this.customToastyService.error('Ошибка', 'Возможно, директорая с таким названием уже существует <hr/>' + error),
          () => this.customToastyService.clear(toasty));
    }
  }

  renameGroup(oldGroupName: string) {
    let newGroupName: string;
    if ((newGroupName = prompt('Rename group', oldGroupName.toString())) && newGroupName && newGroupName.length > 0) {
      const toasty = this.customToastyService.saving('Сохранение группы...', 'Сохранение может занять некоторое время...');
      this.projectService.renameGroup(this.project.code, oldGroupName, newGroupName)
        .subscribe(
          groupList => {
            this.project.groupList = groupList;
            this.customToastyService.success('Сохранено', 'Группа переименована');
          },
          error => this.customToastyService.error('Ошибка', 'Возможно, директорая с таким названием уже существует <hr/>' + error),
          () => this.customToastyService.clear(toasty));
    }
  }
}
