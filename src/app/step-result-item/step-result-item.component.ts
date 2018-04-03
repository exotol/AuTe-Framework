import {Component, Input, OnInit} from '@angular/core';
import {StepResult} from '../model/step-result';
import {StepService} from '../service/step.service';
import {CustomToastyService} from '../service/custom-toasty.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Scenario} from '../model/scenario';

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
  @Input()
  scenario: Scenario;
  expectedDiff: string;
  actualDiff: string;

  tab = 'details';
  projectCode: string;

  constructor(
    private route: ActivatedRoute,
              private stepService: StepService,
    private customToastyService: CustomToastyService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.projectCode = params['projectCode'];
      this.formatText();
    });
  }

  formatText() {

    let diffs: any = this.stepResult.diff;
    if (!diffs) {
      return;
    }

    this.expectedDiff = '';
    this.actualDiff = '';

    // выделяем запись в ожидаемых, в случае если прерации EQUAL-INSERT-EQUAL и rownum записей не отличаются
    let expectedChanges:number[] = [];
    let prevOps: string = '';
    let prevText: string = '';
    let rowNum = 0;
    let prevRowNum = 0;
    let insertText = '';


    for (let x = 0; x < diffs.length; x++) {
      let op = diffs[x].operation;    // Operation (insert, delete, equal)
      let text = diffs[x].text;
      switch (op) {
        case 'INSERT':
        {
          this.actualDiff = this.actualDiff.concat(this.wrapChanged(text, "added"));
          prevOps = prevOps + 'I';
          insertText = text;
          break;
        }
        case 'DELETE':
        {
          rowNum = rowNum + text.split('\n').length - 1;
          this.expectedDiff = this.expectedDiff.concat(this.wrapChanged(text, "removed"));
          prevOps = '';
          break;
        }
        case 'EQUAL':
        {
          let splitted = text.split('\n');
          rowNum = rowNum + splitted.length - 1;
          this.actualDiff = this.actualDiff.concat(text);
          this.expectedDiff = this.expectedDiff.concat(text);
          let iLength = insertText.split('\n').filter(v => v.trim() != '').length;

          // последняя строка предпоследнего изменения
          var lastPrev = prevText.split("\n").pop().trim();
          var firstCurrent = text.split("\n")[0];

          // нам надо выделить строку
          if (prevOps == 'EI' && iLength > 1 && !this.actualDiff.includes(lastPrev + firstCurrent)) {
            expectedChanges.push(prevRowNum);
          }
          insertText = '';
          prevOps = 'E';
          prevText = text;
          prevRowNum = rowNum;
          break;
        }
      }
    }

    this.actualDiff = this.wrapChangedLines(this.actualDiff, "added", "added-row", []);
    this.expectedDiff = this.wrapChangedLines(this.expectedDiff, "removed", "removed-row", expectedChanges);

  }

  /** оборачиваем строку */
  wrapChanged(text: string, classToWrap: string) {
    let wrapped: string;
    if (text.endsWith('\n')) {
      wrapped = '<span class="' + classToWrap + '">' + text.substr(0, text.length - 1) + '</span>' + '\n';
    } else {
      wrapped = '<span class="' + classToWrap + '">' + text + '</span>';
    }
    return wrapped;
  }

  /** оборачиваем строку */
  wrapToDiv(span:String) {
    return '<div class="unchanged-row">' + span + '</div>';
  }


  /** оборачиваем строку, если в ней есть изменения */
  wrapChangedLines(text: string, classToWrap: string, classWrap: string, expectedChanges: number[]) {
    const beginPattern = '<span class="' + classToWrap + '">';
    const endPattern = '</span>';

    let lines = text.split("\n");
    let results = [];
    let resultsToDiv = [];

    let hasChanges = function (line:string):boolean {
      return line.indexOf(beginPattern) != -1 && line.indexOf(beginPattern) != -1;
    };

    let hasUnclosedStart = function (line:string):boolean {
      let index = 0;
      let closed = false;
      let beginPatternIndex = line.indexOf(beginPattern, index);
      let endPatternIndex = line.indexOf(endPattern, index);
      while (beginPatternIndex != -1 || endPatternIndex != -1) {
        if (beginPatternIndex != -1) {
          closed = false;
          index = beginPatternIndex + beginPattern.length;
        }

        if (endPatternIndex == -1 && !closed) {
          closed = true;
        } else if (endPatternIndex != -1) {
          index = endPatternIndex + endPattern.length;
        }
        beginPatternIndex = line.indexOf(beginPattern, index);
        endPatternIndex = line.indexOf(endPattern, index);
      }
      ;
      return closed;
    };


    let hasClosedEnd = function (line:string):boolean {
      let index = 0;
      let closed = false;
      let beginPatternIndex = line.indexOf(beginPattern, index);
      let endPatternIndex = line.indexOf(endPattern, index);
      while (beginPatternIndex != -1 || endPatternIndex != -1) {

        if (beginPatternIndex != -1) {
          closed = false;
          index = beginPatternIndex + beginPattern.length;
        }
        endPatternIndex = line.indexOf(endPattern, index);
        if (endPatternIndex != -1) {
          closed = true;
          index = endPatternIndex + endPattern.length;
        }
        beginPatternIndex = line.indexOf(beginPattern, index);
      };
      return closed;
    };


    let wasChanges = false;
    for (let i = 0; i < lines.length; i++) {
      let line = lines[i];

      // появилась строка с изменениями, но до этого были строки без изменений
      let changed = hasChanges(line);
      let unclosedStart = hasUnclosedStart(line);
      let closedEnd = hasClosedEnd(line);
      let oneStringDiff = changed && !unclosedStart && !closedEnd;

      if (changed && !wasChanges) {
        if (resultsToDiv.length > 0) {
          results.push(this.wrapToDiv(this.wrapChanged(resultsToDiv.join('\n'), "diff-content")));
          resultsToDiv = [];
        }
        wasChanges = false;
      }
      if (unclosedStart) {
        wasChanges = true;
      }

      if (oneStringDiff) {
        if (resultsToDiv.length > 0) {
          results.push(this.wrapToDiv(this.wrapChanged(resultsToDiv.join('\n'), wasChanges ? classWrap : "diff-content")));
          resultsToDiv = [];
        }
        wasChanges = true;
      }

      //noinspection TypeScriptValidateTypes
      if (!changed && expectedChanges.find(x => x == i)) {
        resultsToDiv.push(this.wrapChanged(line, "removed-row"));
      } else {
        resultsToDiv.push(line);
      }


      if (oneStringDiff || closedEnd) {
        if (resultsToDiv.length > 0) {
          results.push(this.wrapToDiv(this.wrapChanged(resultsToDiv.join('\n'), classWrap)));
          resultsToDiv = [];
        }
        wasChanges = false;
      }
    }

    if (resultsToDiv.length > 0) {
      results.push(this.wrapToDiv(this.wrapChanged(resultsToDiv.join('\n'), wasChanges ? classWrap : "diff-content")));
    }

    return results.join('\n');
  }

  selectTab(tabName: string) {
    this.tab = tabName;
    return false;
  }

  saveStep() {
    const toasty = this.customToastyService.saving();
    this.stepService.saveStep(this.projectCode, this.scenario.scenarioGroup, this.scenario.code, this.stepResult.step)
      .subscribe(() => {
        this.customToastyService.success('Сохранено', 'Шаг сохранен');
      }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
  }

  public isShowDiffComponent() {
    return this.stepResult && this.stepResult.expected && this.stepResult.expected.length <= 100000
      && this.stepResult.actual && this.stepResult.actual.length <= 100000
  }
}
