import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {WireMockService} from '../service/wire-mock.service';
import {HttpModule} from '@angular/http';
import {MappingDetailComponent} from './mapping-detail/mapping-detail.component';
import {FormsModule} from '@angular/forms';
import { InputNullComponent } from './input-null/input-null.component';
import {RouterModule, Routes} from '@angular/router';
import {ToastyModule} from 'ng2-toasty';
import { RequestListComponent } from './requests/request-list.component';
import { MappingListComponent } from './mapping-list/mapping-list.component';
import ObjNgFor from './pipe/obj-ng-for-pipe';
import { MqLogComponent } from './mq-log/mq-log.component';
import {MqMockerService} from '../service/mq-mocker-service';

const routes: Routes = [
  { path: 'requests', component: RequestListComponent },
  { path: 'mq-log', component: MqLogComponent },
  { path: 'mapping', component: MappingListComponent },
  { path: 'mapping/:uuid', component: MappingDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    MappingDetailComponent,
    InputNullComponent,
    RequestListComponent,
    MappingListComponent,
    ObjNgFor,
    MqLogComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(routes, { useHash: true }),
    ToastyModule.forRoot()
  ],
  providers: [WireMockService, MqMockerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
