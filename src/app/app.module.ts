import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import { AppComponent } from './app.component';
import {ToastyModule} from 'ng2-toasty';
import {RouterModule, Routes} from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectDetailComponent } from './project-detail/project-detail.component';
import { ScenarioDetailComponent } from './scenario-detail/scenario-detail.component';
import {DiffComponent} from './shared/diff/diff.component';
import {ProjectService} from './service/project.service';
import {Http, HttpModule} from '@angular/http';
import { ScenarioListItemComponent } from './scenario-list-item/scenario-list-item.component';
import {ScenarioService} from './service/scenario.service';
import {StepService} from './service/step.service';
import { StepResultItemComponent } from './step-result-item/step-result-item.component';
import { StepItemComponent } from './step-item/step-item.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MockServiceResponseComponent } from './mock-service-response/mock-service-response.component';
import { StepParameterSetComponent } from './step-parameter-set/step-parameter-set.component';
import {Globals} from './globals';
import { ProjectSettingsComponent } from './project-settings/project-settings.component';
import { ScenarioSettingsComponent } from './scenario-settings/scenario-settings.component';
import {VersionService} from './service/version.service';
import {CustomToastyService} from './service/custom-toasty.service';
import { SearchComponent } from './search-scenario/search-scenario.component';
import {SearchScenarioService} from './service/search-scenario.service';
import {SyncScrollDirective} from './shared/directives/sync-scroll.directive';


export function HttpLoaderFactory(http: Http) {
  return new TranslateHttpLoader(http);
}

const routes: Routes = [
  { path: '', component: ProjectListComponent },
  { path: 'project/:id', component: ProjectDetailComponent },
  { path: 'project/:id/settings', component: ProjectSettingsComponent },
  { path: 'scenario/:id', component: ScenarioDetailComponent },
  { path: 'scenario/:id/settings', component: ScenarioSettingsComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    ProjectDetailComponent,
    ScenarioDetailComponent,
    ScenarioListItemComponent,
    StepResultItemComponent,
    StepItemComponent,
    MockServiceResponseComponent,
    StepParameterSetComponent,
    ProjectSettingsComponent,
    ScenarioSettingsComponent,
    DiffComponent,
    SyncScrollDirective,
    ScenarioSettingsComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot(routes, { useHash: true }),
    ToastyModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [Http]
      }
    }),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ProjectService, ScenarioService, StepService, CustomToastyService, VersionService, Globals, SearchScenarioService],
  bootstrap: [AppComponent]
})
export class AppModule { }
