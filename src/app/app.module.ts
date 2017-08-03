import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {WireMockService} from '../service/wire-mock.service';
import {HttpModule} from '@angular/http';
import {MappingDetailComponent} from './mapping-detail/mapping-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    MappingDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpModule
  ],
  providers: [WireMockService],
  bootstrap: [AppComponent]
})
export class AppModule { }
