import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {ToastyModule} from 'ng2-toasty';
import {RouterModule, Routes} from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectDetailComponent } from './project-detail/project-detail.component';
import { ScenarioDetailComponent } from './scenario-detail/scenario-detail.component';
import {ProjectService} from './service/project.service';
import {HttpModule} from '@angular/http';
import { ScenarioListItemComponent } from './scenario-list-item/scenario-list-item.component';
import {ScenarioService} from './service/scenario.service';
import {StepService} from './service/step.service';

const routes: Routes = [
  { path: '', component: ProjectListComponent },
  { path: 'project/:id', component: ProjectDetailComponent },
  { path: 'scenario/:id', component: ScenarioDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    ProjectDetailComponent,
    ScenarioDetailComponent,
    ScenarioListItemComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot(routes, { useHash: true }),
    ToastyModule.forRoot()
  ],
  providers: [ProjectService, ScenarioService, StepService],
  bootstrap: [AppComponent]
})
export class AppModule { }
