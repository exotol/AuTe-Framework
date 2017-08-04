import {Component, OnInit} from '@angular/core';
import {WireMockService} from '../service/wire-mock.service';
import {Mapping} from '../model/mapping';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  mappingList: Mapping[];

  constructor(private wireMockService: WireMockService) { }

  ngOnInit(): void {
    this.wireMockService.getMappingList()
      .then(mappingList => this.mappingList = mappingList);
  }
}
