import { Component } from '@angular/core';
import {ToastOptions, ToastyService} from 'ng2-toasty';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(
    private toastyService: ToastyService
  ) { }

  showToast() {
    const toastOptions: ToastOptions = {
      title: 'Saved',
      msg: 'Маппинги сохранены на диск',
      showClose: true,
      timeout: 5000,
      theme: 'bootstrap'
    };
    this.toastyService.success(toastOptions)
  }
}
