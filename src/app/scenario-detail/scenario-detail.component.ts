import { Component, OnInit } from '@angular/core';
import {Scenario} from '../model/scenario';
import {Step} from '../model/step';
import {ScenarioService} from '../service/scenario.service';
import {Router, ActivatedRoute, ParamMap} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {StepService} from '../service/step.service';
import {CustomToastyService} from '../service/custom-toasty.service';

@Component({
  selector: 'app-scenario-detail',
  templateUrl: './scenario-detail.component.html',
  styleUrls: ['./scenario-detail.component.css']
})
export class ScenarioDetailComponent implements OnInit {

  scenario: Scenario;
  stepList: Step[];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private scenarioService: ScenarioService,
    private stepService: StepService,
    private customToastyService: CustomToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findOne(+params.get('id')))
      .subscribe(value => this.scenario = value,
        () => {
          this.router.navigate(['/'], {replaceUrl: true});
        });

    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findScenarioSteps(+params.get('id')))
      .subscribe(value => this.stepList = value);
  }

  saveSteps() {
    if (this.scenario && this.stepList) {
      const toasty = this.customToastyService.saving();
      this.scenarioService.saveStepList(this.scenario, this.stepList)
        .subscribe(savedStepList => {
          this.customToastyService.success('Сохранено', 'Шаги сохранены');
          this.stepList = savedStepList;
        }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
    }
  }

  addStep() {
    if (!this.stepList) {
      this.stepList = [];
    }
    const newStep = new Step();
    const maxSort = Math.max.apply(null, this.stepList.map(value => value.sort));
    newStep.sort = Number.isInteger(maxSort) ? maxSort + 50 : 50;
    this.stepList.push(newStep);
  }

  onDeleteClick(step: Step) {
    if (confirm('Confirm: delete step')) {
      const indexToRemove = this.stepList.indexOf(step);
      if (indexToRemove > -1) {
        this.stepList.splice(indexToRemove, 1);
      }
    }
  }

  addStepBefore(step: Step) {
    const addAfterIndex = this.stepList.indexOf(step);
    if (addAfterIndex > -1) {
      const newStep = new Step();
      newStep.sort = step.sort;

      this.stepList
        .filter(value => value.sort >= step.sort)
        .forEach(value => value.sort += 50);

      this.stepList.splice(addAfterIndex, 0, newStep);
    }
  }

  onUpClick(step: Step) {
    const index = this.stepList.indexOf(step);
    if (index > 0) {
      const topSort = this.stepList[index - 1].sort;
      this.stepList[index - 1].sort = step.sort;
      step.sort = topSort;

      const tmpStep = this.stepList[index];
      this.stepList[index] = this.stepList[index - 1];
      this.stepList[index - 1] = tmpStep;
    }
  }

  onDownClick(step: Step) {
    const index = this.stepList.indexOf(step);
    if (index > -1 && index < this.stepList.length - 1) {
      const bottomSort = this.stepList[index + 1].sort;
      this.stepList[index + 1].sort = step.sort;
      step.sort = bottomSort;

      const tmpStep = this.stepList[index];
      this.stepList[index] = this.stepList[index + 1];
      this.stepList[index + 1] = tmpStep;
    }
  }

  onCloneClick(step: Step) {
    if (confirm('Confirm: Clone step')) {
      const toasty = this.customToastyService.saving('Создание клона шага...', 'Создание может занять некоторое время...');
      this.stepService.cloneStep(step)
        .subscribe(clonedStep => {
          const maxSort = Math.max.apply(null, this.stepList.map(value => value.sort));
          clonedStep.sort = Number.isInteger(maxSort) ? maxSort + 50 : 50;
          this.stepList.push(clonedStep);
          this.customToastyService.success('Сохранено', 'Шаг склонирован');
        }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
    }
  }

  deleteScenario() {
    const initialRoute = this.router.url;
    if (confirm('Confirm: Delete scenario')) {
      const toasty = this.customToastyService.deletion('Удаление сценария...');
      this.scenarioService.deleteOne(this.scenario)
        .subscribe(() => {
          if (this.router.url === initialRoute) {
            this.router.navigate(['/project', this.scenario.projectId], {replaceUrl: true});
          }
          this.customToastyService.success('Удалено', 'Сценарий удалён');
        }, error => this.customToastyService.error('Ошибка', error), () => this.customToastyService.clear(toasty));
    }
  }
}
