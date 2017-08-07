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
  customHeaders: HeaderItem[];

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
          this.customHeaders = [];
          return new Promise<Mapping>(() => { });
        } else {
          return this.wireMockService.findOne(id);
        }
      })
      .subscribe(mapping => {
        console.log(mapping);
        this.customHeaders = [];
        this.mapping = mapping;
        this.setHeaders(mapping.request.headers);
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
    console.log('before', this.mapping.request.headers);
    this.mapping.request.headers = this.getHeaders();
    console.log('after', this.mapping.request.headers);
    this.wireMockService
      .apply(this.mapping)
      .then(value => {
        this.mapping = value;
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

  getHeaders(): any {
    console.log('get headers', this.customHeaders);
    const obj = {};
    this.customHeaders.forEach(header => {
      obj[header.headerName] = {
        [header.compareType]: header.headerValue
      }
    });
    return obj;
  }

  setHeaders(headers: any) {
    console.log('set headers', headers);
    this.customHeaders = [];
    for (const headerName of Object.keys(headers)) {
      const headerItem = new HeaderItem();
      headerItem.headerName = headerName;
      for (const compareType of Object.keys(headers[headerName])) {
        headerItem.compareType = compareType;
        headerItem.headerValue = headers[headerName][compareType];
      }
      this.customHeaders.push(headerItem);
    }

  }

  addHeaderPattern() {
    if (!this.customHeaders) {
      this.customHeaders = [];
    }
    this.customHeaders.push(new HeaderItem());
  }

  deleteHeader(header: HeaderItem) {
    this.customHeaders = this.customHeaders.filter(value => value !== header);
  }
}
