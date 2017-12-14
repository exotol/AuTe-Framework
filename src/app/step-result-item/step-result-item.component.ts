import {Component, Input, OnInit} from '@angular/core';
import {StepResult} from '../model/step-result';
import {StepService} from '../service/step.service';
import {CustomToastyService} from '../service/custom-toasty.service';
import {ActivatedRoute, ParamMap} from '@angular/router';

@Component({
  selector: 'app-step-result-item',
  templateUrl: './step-result-item.component.html',
  styles: [
    '.nav-tabs > li > a { padding-top: 3px; padding-bottom: 3px; }',
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
    '.row { margin-bottom: 5px; }',
    '.input-group-btn > select { padding: 0; width: 85px; border-top-left-radius: 5px; border-bottom-left-radius: 5px; border-right: 0; }'
  ]
})
export class StepResultItemComponent implements OnInit {

  @Input()
  stepResult: StepResult;

  tab = 'summary';
  projectCode: String;
  scenarioGroup: String;
  scenarioCode: String;

  constructor(
    private route: ActivatedRoute,
    private stepService: StepService,
    private customToastyService: CustomToastyService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      console.log(params);
      this.projectCode = params['projectCode'];
      this.scenarioGroup = params['scenarioGroup'];
      this.scenarioCode = params['scenarioCode'];
    });
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  saveStep() {
    const toasty = this.customToastyService.saving();
    this.stepService.saveStep(this.projectCode, this.scenarioGroup, this.scenarioCode, this.stepResult.step)
      .subscribe(() => {
        this.customToastyService.success('Сохранено', 'Шаг сохранен');
      }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
  }
}
