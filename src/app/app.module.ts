import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {ToastyModule} from 'ng2-toasty';
import {RouterModule, Routes} from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectDetailComponent } from './project-detail/project-detail.component';
import { ScenarioDetailComponent } from './scenario-detail/scenario-detail.component';

const routes: Routes = [
  { path: 'project', component: ProjectListComponent },
  { path: 'project/:id', component: ProjectDetailComponent },
  { path: 'scenario/:id', component: ScenarioDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    ProjectDetailComponent,
    ScenarioDetailComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, { useHash: true }),
    ToastyModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
