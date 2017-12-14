import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {VersionService} from './service/version.service';
import {Version} from './model/version';

const LANG_TOKEN = 'bsc_autotester_language';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  public version: Version;
  langs: string[] = ['en', 'ru'];
  locale: string;

  constructor(private versionService: VersionService, private translate: TranslateService) {
    translate.addLangs(this.langs);
    this.locale = localStorage.getItem(LANG_TOKEN) || 'en';
    translate.setDefaultLang(this.locale);
  }

  ngOnInit(): void {
    this.versionService.getVersion().subscribe(version => this.version = version);
  }

  changeLocale(): void {
    this.translate.use(this.locale);
    this.saveCurrentLangAsDefault();
  }

  saveCurrentLangAsDefault() {
    localStorage.setItem(LANG_TOKEN, this.locale);
  }
}
