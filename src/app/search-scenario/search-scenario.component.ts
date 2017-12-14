import {Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import	'rxjs/Rx';
import {Scenario} from '../model/scenario';
import {SearchScenarioService} from '../service/search-scenario.service';

@Component({
  selector: 'app-search',
  templateUrl: './search-scenario.component.html',
  styleUrls: ['./search-scenario.component.css']
})
export class SearchComponent implements OnInit {
  placeholderText: string;
  errorMsg: string;
  errorFlag: boolean;
  scenarioList: Scenario[];
  queryField: FormControl;
  @Input() projectId;
  @ViewChild('searchResult') searchResult: ElementRef;
  @ViewChild('queryInput') queryInput: ElementRef;

  constructor(
    private searchScenarioService: SearchScenarioService
  ) {
      this.placeholderText = 'Поиск по наименованию метода';
      this.errorMsg = 'Ничего не найдено';
  }

  ngOnInit() {
    this.queryField = new FormControl();
    this.queryField.valueChanges
      .debounceTime(400)
      .distinctUntilChanged()
      .subscribe( query => {
          if (query === '') {
            this.hiddenResultBlock();
            return;
          }
          this.search(query);
        }
      )
  }

  search(query) {
    this.errorFlag = false;
    this.searchScenarioService.searchByMethod(this.projectId, query)
      .subscribe(
        (result) => {
                            if (!result.length) {
                                this.hiddenResultBlock(true);
                                return;
                            }
                            this.scenarioList = result;
                          },
        () => {
          this.hiddenResultBlock(true);
        }
      );
  }

  hiddenResultBlock(stateErrorFlag: boolean = false) {
    this.errorFlag = stateErrorFlag;
    this.scenarioList = null;
  }

  @HostListener('window:mousedown', ['$event.target'])
  onMouseDown(target) {
    const isShowSearchResult = (!this.searchResult || this.searchResult.nativeElement.contains(target))
                                || this.queryInput.nativeElement.contains(target);

    if (isShowSearchResult) {
      return;
    }

    this.hiddenResultBlock();
  }
}
