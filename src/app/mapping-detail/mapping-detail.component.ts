import {Component, OnInit} from '@angular/core';
import {Mapping} from '../../model/mapping';
import {BodyPattern} from '../../model/body-pattern';
import {WireMockService} from '../../service/wire-mock.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

import 'rxjs/add/operator/switchMap';

import {ToastyService, ToastOptions} from 'ng2-toasty';

@Component({
  selector: 'app-mapping-detail',
  templateUrl: './mapping-detail.component.html'
})
export class MappingDetailComponent implements OnInit {

  mapping: Mapping;

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
          return new Promise<Mapping>(() => { });
        } else {
          return this.wireMockService.findOne(id);
        }
      })
      .subscribe(mapping => {
        console.log(mapping);
        this.mapping = mapping;
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
}
