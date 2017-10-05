import { Component, OnInit } from '@angular/core';
import {Scenario} from '../model/scenario';
import {Step} from '../model/step';
import {ScenarioService} from '../service/scenario.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {ToastOptions, ToastyService} from 'ng2-toasty';

@Component({
  selector: 'app-scenario-detail',
  templateUrl: './scenario-detail.component.html'
})
export class ScenarioDetailComponent implements OnInit {

  scenario: Scenario;
  stepList: Step[];

  constructor(
    private route: ActivatedRoute,
    private scenarioService: ScenarioService,
    private toastyService: ToastyService
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findOne(+params.get('id')))
      .subscribe(value => this.scenario = value);

    this.route.paramMap
      .switchMap((params: ParamMap) => this.scenarioService.findScenarioSteps(+params.get('id')))
      .subscribe(value => this.stepList = value);
  }

  saveSteps() {
    if (this.scenario && this.stepList) {
      this.scenarioService.saveStepList(this.scenario, this.stepList)
        .subscribe(savedStepList => {
          const toastOptions: ToastOptions = {
            title: 'Updated',
            msg: 'Steps updated',
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap'
          };
          this.toastyService.success(toastOptions);
          this.stepList = savedStepList;
        });
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
}
