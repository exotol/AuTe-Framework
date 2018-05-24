import { Component, OnInit } from '@angular/core';
import {Mapping} from '../../model/mapping';
import {WireMockService} from '../../service/wire-mock.service';

@Component({
  selector: 'app-mapping-list',
  templateUrl: './mapping-list.component.html'
})
export class MappingListComponent implements OnInit {

  mappingList: Mapping[];
  displayDetails = false;

  constructor(
    public wireMockService: WireMockService
  ) { }

  ngOnInit() {
    this.wireMockService.getMappingList()
      .then(mappingList => this.mappingList = mappingList
        .sort((a, b) => (
            a.request.url ? a.request.url : a.request.urlPattern) > (b.request.url ? b.request.url : b.request.urlPattern) ? 1 : -1
        ));
  }

  detailsToggle() {
    this.displayDetails = !this.displayDetails;
  }
}
