import {Component, OnInit} from '@angular/core';
import {Mapping} from '../../model/mapping';
import {BodyPattern} from '../../model/body-pattern';
import {WireMockService} from '../../service/wire-mock.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

import 'rxjs/add/operator/switchMap';

import {ToastyService, ToastOptions} from 'ng2-toasty';
import {HeaderItem} from '../../model/request-mapping';

@Component({
  selector: 'app-mapping-detail',
  templateUrl: './mapping-detail.component.html'
})
export class MappingDetailComponent implements OnInit {

  mapping: Mapping;
  customRequestHeaders: HeaderItem[];
  customResponseHeaders: HeaderItem[];

  constructor(
    public wireMockService: WireMockService,
    private route: ActivatedRoute,
    private toastyService: ToastyService,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => {
        const id = params.get('uuid');
        if (id === 'new') {
          this.mapping = new Mapping();
          this.customRequestHeaders = [];
          const contentTypeHeader = new HeaderItem();
          contentTypeHeader.headerName = 'Content-Type';
          contentTypeHeader.headerValue = 'text/xml';
          this.customResponseHeaders = [ contentTypeHeader ];
          return new Promise<Mapping>(() => { });
        } else {
          return this.wireMockService.findOne(id);
        }
      })
      .subscribe(mapping => {
        this.customRequestHeaders = [];
        this.customResponseHeaders = [];
        this.mapping = mapping;
        this.setRequestHeaders(mapping.request.headers);
        this.setResponseHeaders(mapping.response.headers);
      });
  }

  addBodyPattern() {
    if (!this.mapping.request.bodyPatterns) {
      this.mapping.request.bodyPatterns = [];
    }
    this.mapping.request.bodyPatterns.push(new BodyPattern());
  }

  removeBodyPattern(bodyPattern: BodyPattern) {
    this.mapping.request.bodyPatterns = this.mapping.request.bodyPatterns.filter(item => item !== bodyPattern);
  }

  applyMapping() {
    this.mapping.request.headers = this.getRequestHeaders();
    this.mapping.response.headers = this.getResponseHeaders();
    this.wireMockService
      .apply(this.mapping)
      .then(value => {
        this.mapping = value;
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigate(['/mapping', this.mapping.uuid]);
        const toastOptions: ToastOptions = {
          title: 'Applied',
          msg: 'Маппинг применен',
          showClose: true,
          timeout: 5000,
          theme: 'bootstrap'
        };
        this.toastyService.success(toastOptions);
      });
  }

  getRequestHeaders(): any {
    const obj = {};
    this.customRequestHeaders
      .forEach(header => obj[header.headerName] = { [header.compareType]: header.headerValue });
    return obj;
  }

  getResponseHeaders(): any {
    const obj = {};
    this.customResponseHeaders
      .forEach(header => obj[header.headerName] = header.headerValue);
    return obj;
  }

  setRequestHeaders(headers: any) {
    if (headers) {
      this.customRequestHeaders = [];
      for (const headerName of Object.keys(headers)) {
        const headerItem = new HeaderItem();
        headerItem.headerName = headerName;
        for (const compareType of Object.keys(headers[headerName])) {
          headerItem.compareType = compareType;
          headerItem.headerValue = headers[headerName][compareType];
        }
        this.customRequestHeaders.push(headerItem);
      }
    }
  }

  setResponseHeaders(headers: any) {
    if (headers) {
      this.customResponseHeaders = [];
      for (const headerName of Object.keys(headers)) {
        const headerItem = new HeaderItem();
        headerItem.headerName = headerName;
        headerItem.headerValue = headers[headerName];
        this.customResponseHeaders.push(headerItem);
      }
    }
  }

  // noinspection JSMethodCanBeStatic
  addHeaderPattern(customRequestHeaders: HeaderItem[]) {
    if (!customRequestHeaders) {
      customRequestHeaders = [];
    }
    customRequestHeaders.push(new HeaderItem());
  }

  deleteRequestHeader(header: HeaderItem) {
    this.customRequestHeaders = this.customRequestHeaders.filter(value => value !== header);
  }

  deleteResponseHeader(header: HeaderItem) {
    this.customResponseHeaders = this.customResponseHeaders.filter(value => value !== header);
  }
}
