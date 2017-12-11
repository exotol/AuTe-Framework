import { Component, OnInit } from '@angular/core';
import {Project} from '../model/project';
import {ProjectService} from '../service/project.service';
import {ImportProject} from '../model/import-project';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html'
})
export class ProjectListComponent implements OnInit {

  projectList: Project[];
  displayImportProjectForm = false;
  importProject: ImportProject = new ImportProject();

  constructor(
    private projectService: ProjectService
  ) { }

  ngOnInit() {
    this.projectService.findAll().subscribe(value => this.projectList = value);
  }

  doImport() {
    this.projectService.saveFullProject(this.importProject)
      .subscribe();
  }

}
