import {Component, Input, OnInit} from '@angular/core';
import {StepResult} from '../model/step-result';
import {StepService} from '../service/step.service';
import {CustomToastyService} from '../service/custom-toasty.service';
import * as JsDiff from 'diff';

@Component({
  selector: 'app-step-result-item',
  templateUrl: './step-result-item.component.html',
  styles: [
    '.nav-tabs > li > a { padding-top: 3px; padding-bottom: 3px; }',
    '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
    '.row { margin-bottom: 5px; }',
    '.input-group-btn > select { padding: 0; width: 85px; border-top-left-radius: 5px; border-bottom-left-radius: 5px; border-right: 0; }',
    '.form-control .diff-content { white-space: pre-wrap; }',
    '.form-control .added-row { background-color: #dfd; }',
    '.form-control .removed-row { background-color: #fdd; }',
    '.form-control .added { background-color: #afa; font-weight: bold; }',
    '.form-control .removed { background-color: #fbb; font-weight: bold; }'
  ]
})
export class StepResultItemComponent implements OnInit {

  @Input()
  stepResult: StepResult;

  tab = 'summary';
  diff: any[];

  constructor(
    private stepService: StepService,
    private customToastyService: CustomToastyService
  ) { }

  ngOnInit() {
    // stepResult.actual
    // stepResult.expected
    this.processResultString();
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  saveStep() {
    const toasty = this.customToastyService.saving();
    this.stepService.saveStep(this.stepResult.step)
      .subscribe(() => {
        this.customToastyService.success('Сохранено', 'Шаг сохранен');
      }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
  }

  private processResultString() {
    const actualResultStr: string = this.prepareStringForComparison(this.stepResult.actual);
    const expectedResultStr: string = this.prepareStringForComparison(this.stepResult.expected);

    this.diff = JsDiff.diffLines(expectedResultStr, actualResultStr);

    this.diff.forEach((item, i, arr) => {
      if (item.removed && arr[i + 1] && arr[i + 1].added) {
        item.rowDiff = arr[i + 1].rowDiff = JsDiff.diffWordsWithSpace(item.value, arr[i + 1].value);
      }
    });
  }

  private prepareStringForComparison(str: string): string {
    if (!str) {
      return '';
    }
    const resultObject: object = this.tryToParseAsJSON(str);
    return resultObject ?
      JSON.stringify(resultObject, null, 2) :
      str.replace(/\r/g, '').replace(/\t/g, '  ').trim();
  }

  private tryToParseAsJSON(str: string): object {
    try {
      return JSON.parse(str);
    } catch (e) {
      return null;
    }
  }
}
