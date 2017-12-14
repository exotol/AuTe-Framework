import {Component, Input, OnInit} from '@angular/core';
import * as JsDiff from 'diff';

@Component({
  selector: 'app-diff',
  templateUrl: './diff.component.html',
  styleUrls: ['./diff.component.scss']
})
export class DiffComponent implements OnInit {
  @Input('expected') expected: string;
  @Input('actual') actual: string;
  expectedDiff: any[];
  actualDiff: any[];
  syncScroll: boolean = true;

  ngOnInit() {
    this.formDiff();
  }

  private formDiff(): void {
    const actualResultStr: string = this.prepareStringForComparison(this.actual);
    const expectedResultStr: string = this.prepareStringForComparison(this.expected);

    const diff: any[] = JsDiff.diffLines(expectedResultStr, actualResultStr);

    diff.forEach((item, i, arr) => {
      if (item.removed && arr[i + 1] && arr[i + 1].added) {
        item.rowDiff = arr[i + 1].rowDiff = JsDiff.diffWordsWithSpace(item.value, arr[i + 1].value);
      }
    });

    this.expectedDiff = diff.filter(item => !item.added).map(item => {
      if (item.rowDiff) {
        item.rowDiff = item.rowDiff.filter(rowDiffItem => !rowDiffItem.added);
      }
      return item;
    });

    this.actualDiff = diff.filter(item => !item.removed).map(item => {
      if (item.rowDiff) {
        item.rowDiff = item.rowDiff.filter(rowDiffItem => !rowDiffItem.removed);
      }
      return item;
    });
  }

  private prepareStringForComparison(str: string): string {
    if (!str) {
      return '';
    }
    const resultObject: any = this.tryToParseAsJSON(str);
    return resultObject ?
      JSON.stringify(resultObject, null, 2) :
      str.replace(/\r/g, '').replace(/\t/g, '  ').trim();
  }

  private tryToParseAsJSON(str: string): any {
    try {
      return JSON.parse(str);
    } catch (e) {
      return null;
    }
  }
}
