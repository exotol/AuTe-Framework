import {Component, OnInit} from '@angular/core';
import {VersionService} from './service/version.service';
import {Version} from './model/version';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  public version: Version;

  constructor(private versionService: VersionService) { }

  ngOnInit(): void {
    this.versionService.getVersion().subscribe(version => this.version = version);
  }
}
