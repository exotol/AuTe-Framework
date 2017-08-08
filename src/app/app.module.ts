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

const routes: Routes = [
  { path: 'mapping/:uuid', component: MappingDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    MappingDetailComponent,
    InputNullComponent
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
