import {Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import { NgModule } from '@angular/core';
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
  queryString: string;
  scenarioList: Scenario[];
  @Input() projectId;
  @ViewChild('searchResult') searchResult: ElementRef;
  @ViewChild('queryInput') queryInput: ElementRef;

  constructor(
    private searchScenarioService: SearchScenarioService
  ) {
      this.placeholderText = 'Поиск по наименованию метода';
      this.errorMsg = 'Сценарий не найден';
  }

  ngOnInit() {
  }

  search() {
    this.errorFlag = false;

    if (this.queryString) {
      this.searchScenarioService.searchByMethod(this.projectId, this.queryString)
        .then( (result) => {this.scenarioList = result; })
        .catch( () => {
          this.errorFlag = true;
        });
    } else {
      this.scenarioList = null;
    }
  }

  @HostListener('window:mousedown', ['$event.target'])
  onMouseDown(target) {
    const isShowSearchResult = (!this.searchResult || this.searchResult.nativeElement.contains(target))
                                || this.queryInput.nativeElement.contains(target);

    if (isShowSearchResult) {
      return;
    }
    this.errorFlag = false;
    this.scenarioList = null;
  }
}
