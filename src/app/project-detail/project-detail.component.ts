import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../service/project.service';
import {Project} from '../model/project';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit {

  projectList: Project[];

  constructor(
    private projectService: ProjectService
  ) { }

  ngOnInit() {
    this.projectService.findAll().subscribe(value => this.projectList = value);
  }

}
