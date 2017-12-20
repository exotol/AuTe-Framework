import { Component, OnInit } from '@angular/core';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Stand} from '../model/stand';
import {Scenario} from '../model/scenario';
import {CustomToastyService} from '../service/custom-toasty.service';
import {AmqpBroker} from '../model/amqp-broker';

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

  addStand(): void {
    if (!this.project.standList) {
      this.project.standList = [];
    }
    this.project.standList.push(new Stand());
  }

  removeStand(stand: Stand): void {
    if (confirm('Confirm: Remove stand')) {
      const indexToRemove = this.project.standList.indexOf(stand);
      if (indexToRemove > -1) {
        this.project.standList.splice(indexToRemove, 1);
      }
    }
  }

  addAmqpBroker() {
    this.project.amqpBroker = new AmqpBroker();
  }

  removeAmqpBroker() {
    if (confirm('Confirm: remove AMQP broker')) {
      this.project.amqpBroker = null;
    }
  }

  addGroup(): void {
    let newGroupName: String;
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

  renameGroup(oldGroupName: String) {
    let newGroupName: String;
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
