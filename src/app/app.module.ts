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

const routes: Routes = [
  { path: 'requests', component: RequestListComponent },
  { path: 'mapping', component: MappingListComponent },
  { path: 'mapping/:uuid', component: MappingDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    MappingDetailComponent,
    InputNullComponent,
    RequestListComponent,
    MappingListComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(routes, { useHash: true }),
    ToastyModule.forRoot()
  ],
  providers: [WireMockService],
  bootstrap: [AppComponent]
})
export class AppModule { }
