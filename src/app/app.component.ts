import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {VersionService} from './service/version.service';
import {Version} from './model/version';
import {WiremockVersion} from "./model/wiremock-version";

const LANG_TOKEN = 'bsc_autotester_language';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  public managerVersion: Version;
  public executorVersion: Version;
  public projectsWiremockVersions: Array<WiremockVersion>;
  langs: string[] = ['en', 'ru'];
  locale: string;

  constructor(private versionService: VersionService, private translate: TranslateService) {
    translate.addLangs(this.langs);
    this.locale = localStorage.getItem(LANG_TOKEN) || 'en';
    translate.setDefaultLang(this.locale);
  }

  ngOnInit(): void {
    this.versionService.getManagerVersion().subscribe(version => this.managerVersion = version);
    this.versionService.getExecutorVersion().subscribe(version => this.executorVersion = version);
    this.versionService.getProjectsWiremockVersions().subscribe(version => this.projectsWiremockVersions = version);
  }

  changeLocale(): void {
    this.translate.use(this.locale);
    this.saveCurrentLangAsDefault();
  }

  saveCurrentLangAsDefault() {
    localStorage.setItem(LANG_TOKEN, this.locale);
  }
}
