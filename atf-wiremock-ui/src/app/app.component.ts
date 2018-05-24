import {Component, OnInit} from '@angular/core';
import {WireMockService} from '../service/wire-mock.service';
import {ToastOptions, ToastyService} from 'ng2-toasty';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  window = window;

  constructor(
    public wireMockService: WireMockService,
    private toastyService: ToastyService
  ) { }

  ngOnInit(): void {}

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
