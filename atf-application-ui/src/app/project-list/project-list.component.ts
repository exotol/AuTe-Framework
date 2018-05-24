import {Component, OnInit} from '@angular/core';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {CustomToastyService} from '../service/custom-toasty.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html'
})
export class ProjectListComponent implements OnInit {

  newProjectName: string;
  newProjectCode: string;
  projectList: Project[];

  constructor(
    private customToastyService: CustomToastyService,
    private projectService: ProjectService,
    private translate: TranslateService
  ) { }

  ngOnInit() {
    this.projectService.findAll().subscribe(value => this.projectList = value);
  }

  saveNewProject() {
    if (confirm('Confirm: create new project')) {
      const newProject = new Project();
      newProject.name = this.newProjectName;
      newProject.code = this.newProjectCode;

      const toasty = this.customToastyService.saving('Сохранение проекта...', 'Сохранение может занять некоторое время...');
      const t = this;
      this.projectService.create(newProject)
        .subscribe(savedProject => {
          t.projectList.push(savedProject);
          t.customToastyService.success('Сохранено', 'Проект создан');
        }, error => this.handleError(error), () => this.customToastyService.clear(toasty));
    }
  }

  private handleError(error: any) {
    const message = JSON.parse(error._body).message;
    console.log(message);
    this.translate.get(message).subscribe(value => {
      this.customToastyService.error('Ошибка', value);
    });
  }
}
