import {Component, Input, OnInit} from '@angular/core';
import {Mapping} from '../../model/mapping';

@Component({
  selector: 'app-mapping-detail',
  templateUrl: './mapping-detail.component.html'
})
export class MappingDetailComponent implements OnInit {

  @Input()
  mapping: Mapping;

  constructor() { }

  ngOnInit() {
  }

}
