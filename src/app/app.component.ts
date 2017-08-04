import {Component, OnInit} from '@angular/core';
import {WireMockService} from '../service/wire-mock.service';
import {Mapping} from '../model/mapping';
import {ToastOptions, ToastyService} from 'ng2-toasty';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  mappingList: Mapping[];

  constructor(
    private wireMockService: WireMockService,
    private toastyService: ToastyService
  ) { }

  ngOnInit(): void {
    this.wireMockService.getMappingList()
      .then(mappingList => this.mappingList = mappingList);
  }

  saveToBackStorage() {
    if (confirm('Confirm saving')) {
      this.wireMockService.saveToBackStorage().then(() => {
        const toastOptions: ToastOptions = {
          title: 'Saved',
          msg: 'Маппинги сохранены на диск',
          showClose: true,
          timeout: 5000,
          theme: 'bootstrap'
        };
        this.toastyService.success(toastOptions)
      });
    }
  }
}
