webpackJsonp([1],{

/***/ "../../../../../src async recursive":
/***/ (function(module, exports) {

function webpackEmptyContext(req) {
	throw new Error("Cannot find module '" + req + "'.");
}
webpackEmptyContext.keys = function() { return []; };
webpackEmptyContext.resolve = webpackEmptyContext;
module.exports = webpackEmptyContext;
webpackEmptyContext.id = "../../../../../src async recursive";

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar navbar-default\" style=\"margin-bottom: 0;\">\r\n  <div class=\"container-fluid\">\r\n    <div class=\"navbar-header\">\r\n      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\r\n        <span class=\"sr-only\">Toggle navigation</span>\r\n        <span class=\"icon-bar\"></span>\r\n        <span class=\"icon-bar\"></span>\r\n        <span class=\"icon-bar\"></span>\r\n      </button>\r\n      <a class=\"navbar-brand\" href=\"#\">BSC AutoTester</a>\r\n    </div>\r\n    <div id=\"navbar\" class=\"navbar-collapse collapse\">\r\n      <ul class=\"nav navbar-nav\">\r\n        <li [routerLinkActive]=\"'active'\"><a [routerLink]=\"['/']\">Projects</a></li>\r\n      </ul>\r\n    </div>\r\n  </div>\r\n</nav>\r\n\r\n<router-outlet></router-outlet>\r\n\r\n<div class=\"help-block\" *ngIf=\"version\">{{version.implementationVersion}} {{version.implementationDate}}</div>\r\n<ng2-toasty [position]=\"'top-right'\"></ng2-toasty>\r\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_version_service__ = __webpack_require__("../../../../../src/app/service/version.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AppComponent = (function () {
    function AppComponent(versionService) {
        this.versionService = versionService;
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.versionService.getVersion().subscribe(function (version) { return _this.version = version; });
    };
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-root',
        template: __webpack_require__("../../../../../src/app/app.component.html")
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__service_version_service__["a" /* VersionService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_version_service__["a" /* VersionService */]) === "function" && _a || Object])
], AppComponent);

var _a;
//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__ = __webpack_require__("../../../../ng2-toasty/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__project_list_project_list_component__ = __webpack_require__("../../../../../src/app/project-list/project-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__project_detail_project_detail_component__ = __webpack_require__("../../../../../src/app/project-detail/project-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__scenario_detail_scenario_detail_component__ = __webpack_require__("../../../../../src/app/scenario-detail/scenario-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__scenario_list_item_scenario_list_item_component__ = __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__step_result_item_step_result_item_component__ = __webpack_require__("../../../../../src/app/step-result-item/step-result-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__step_item_step_item_component__ = __webpack_require__("../../../../../src/app/step-item/step-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__mock_service_response_mock_service_response_component__ = __webpack_require__("../../../../../src/app/mock-service-response/mock-service-response.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__step_parameter_set_step_parameter_set_component__ = __webpack_require__("../../../../../src/app/step-parameter-set/step-parameter-set.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__project_settings_project_settings_component__ = __webpack_require__("../../../../../src/app/project-settings/project-settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__scenario_settings_scenario_settings_component__ = __webpack_require__("../../../../../src/app/scenario-settings/scenario-settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__service_version_service__ = __webpack_require__("../../../../../src/app/service/version.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};























var routes = [
    { path: '', component: __WEBPACK_IMPORTED_MODULE_5__project_list_project_list_component__["a" /* ProjectListComponent */] },
    { path: 'project/:projectCode', component: __WEBPACK_IMPORTED_MODULE_6__project_detail_project_detail_component__["a" /* ProjectDetailComponent */] },
    { path: 'project/:projectCode/settings', component: __WEBPACK_IMPORTED_MODULE_19__project_settings_project_settings_component__["a" /* ProjectSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioCode/settings', component: __WEBPACK_IMPORTED_MODULE_20__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioGroup/:scenarioCode/settings', component: __WEBPACK_IMPORTED_MODULE_20__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioCode', component: __WEBPACK_IMPORTED_MODULE_7__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioGroup/:scenarioCode', component: __WEBPACK_IMPORTED_MODULE_7__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */] },
];
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["b" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_5__project_list_project_list_component__["a" /* ProjectListComponent */],
            __WEBPACK_IMPORTED_MODULE_6__project_detail_project_detail_component__["a" /* ProjectDetailComponent */],
            __WEBPACK_IMPORTED_MODULE_7__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */],
            __WEBPACK_IMPORTED_MODULE_10__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */],
            __WEBPACK_IMPORTED_MODULE_13__step_result_item_step_result_item_component__["a" /* StepResultItemComponent */],
            __WEBPACK_IMPORTED_MODULE_14__step_item_step_item_component__["a" /* StepItemComponent */],
            __WEBPACK_IMPORTED_MODULE_16__mock_service_response_mock_service_response_component__["a" /* MockServiceResponseComponent */],
            __WEBPACK_IMPORTED_MODULE_17__step_parameter_set_step_parameter_set_component__["a" /* StepParameterSetComponent */],
            __WEBPACK_IMPORTED_MODULE_19__project_settings_project_settings_component__["a" /* ProjectSettingsComponent */],
            __WEBPACK_IMPORTED_MODULE_20__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_9__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_4__angular_router__["a" /* RouterModule */].forRoot(routes, { useHash: true }),
            __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["a" /* ToastyModule */].forRoot(),
            __WEBPACK_IMPORTED_MODULE_15__angular_forms__["a" /* FormsModule */]
        ],
        providers: [__WEBPACK_IMPORTED_MODULE_8__service_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_11__service_scenario_service__["a" /* ScenarioService */], __WEBPACK_IMPORTED_MODULE_12__service_step_service__["a" /* StepService */], __WEBPACK_IMPORTED_MODULE_22__service_custom_toasty_service__["a" /* CustomToastyService */], __WEBPACK_IMPORTED_MODULE_21__service_version_service__["a" /* VersionService */], __WEBPACK_IMPORTED_MODULE_18__globals__["a" /* Globals */]],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */]]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/app/globals.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Globals; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var Globals = (function () {
    function Globals() {
        this.serviceBaseUrl = '';
    }
    return Globals;
}());
Globals = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])()
], Globals);

//# sourceMappingURL=globals.js.map

/***/ }),

/***/ "../../../../../src/app/mock-service-response/mock-service-response.component.html":
/***/ (function(module, exports) {

module.exports = "<div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-8\">\r\n      <input class=\"form-control\" placeholder=\"Service URL\" title=\"\" [(ngModel)]=\"mockServiceResponse.serviceUrl\"/>\r\n    </div>\r\n    <div class=\"col-sm-4\">\r\n      <input class=\"form-control\" placeholder=\"HTTP-status: 200, 404, 500, [empty]\" title=\"\" [(ngModel)]=\"mockServiceResponse.httpStatus\"/>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <label>Response body</label>\r\n      <textarea class=\"form-control\" placeholder=\"Response body\" title=\"\" rows=\"7\" [(ngModel)]=\"mockServiceResponse.responseBody\"></textarea>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/mock-service-response/mock-service-response.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_mock_service_response__ = __webpack_require__("../../../../../src/app/model/mock-service-response.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MockServiceResponseComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MockServiceResponseComponent = (function () {
    function MockServiceResponseComponent() {
    }
    MockServiceResponseComponent.prototype.ngOnInit = function () {
    };
    return MockServiceResponseComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_mock_service_response__["a" /* MockServiceResponse */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_mock_service_response__["a" /* MockServiceResponse */]) === "function" && _a || Object)
], MockServiceResponseComponent.prototype, "mockServiceResponse", void 0);
MockServiceResponseComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-mock-service-response',
        template: __webpack_require__("../../../../../src/app/mock-service-response/mock-service-response.component.html")
    }),
    __metadata("design:paramtypes", [])
], MockServiceResponseComponent);

var _a;
//# sourceMappingURL=mock-service-response.component.js.map

/***/ }),

/***/ "../../../../../src/app/model/amqp-broker.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AmqpBroker; });
var AmqpBroker = (function () {
    function AmqpBroker() {
    }
    return AmqpBroker;
}());

//# sourceMappingURL=amqp-broker.js.map

/***/ }),

/***/ "../../../../../src/app/model/expected-service-request.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ExpectedServiceRequest; });
var ExpectedServiceRequest = (function () {
    function ExpectedServiceRequest() {
    }
    return ExpectedServiceRequest;
}());

//# sourceMappingURL=expected-service-request.js.map

/***/ }),

/***/ "../../../../../src/app/model/form-data.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return FormData; });
var FormData = (function () {
    function FormData() {
    }
    return FormData;
}());

//# sourceMappingURL=form-data.js.map

/***/ }),

/***/ "../../../../../src/app/model/mock-service-response.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MockServiceResponse; });
var MockServiceResponse = (function () {
    function MockServiceResponse() {
    }
    return MockServiceResponse;
}());

//# sourceMappingURL=mock-service-response.js.map

/***/ }),

/***/ "../../../../../src/app/model/scenario.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Scenario; });
var Scenario = (function () {
    function Scenario() {
        this._selected = false;
    }
    return Scenario;
}());

//# sourceMappingURL=scenario.js.map

/***/ }),

/***/ "../../../../../src/app/model/step-parameter-set.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepParameterSet; });
var StepParameterSet = (function () {
    function StepParameterSet() {
    }
    return StepParameterSet;
}());

//# sourceMappingURL=step-parameter-set.js.map

/***/ }),

/***/ "../../../../../src/app/model/step-parameter.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepParameter; });
var StepParameter = (function () {
    function StepParameter(name) {
        if (name) {
            this.name = name;
        }
    }
    return StepParameter;
}());

//# sourceMappingURL=step-parameter.js.map

/***/ }),

/***/ "../../../../../src/app/model/step-result.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepResult; });
var StepResult = (function () {
    function StepResult() {
    }
    return StepResult;
}());

//# sourceMappingURL=step-result.js.map

/***/ }),

/***/ "../../../../../src/app/model/step.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Step; });
var Step = (function () {
    function Step() {
        this.savedValuesCheck = {};
        this.stepParameterSetList = [];
        this.formDataList = [];
    }
    return Step;
}());

//# sourceMappingURL=step.js.map

/***/ }),

/***/ "../../../../../src/app/project-detail/project-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<ng-container *ngIf=\"project\">\r\n  <ol class=\"breadcrumb\">\r\n    <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">Projects</a></li>\r\n    <li class=\"breadcrumb-item active\">{{project.code}}. {{project.name}}</li>\r\n  </ol>\r\n\r\n  <h4>{{project.name}}</h4>\r\n  <a [routerLink]=\"['/project/' + project.code + '/settings']\">Settings</a>\r\n\r\n  <nav aria-label=\"Scenario groups\">\r\n    <ul class=\"pagination\">\r\n      <li [class.active]=\"!filter\">\r\n        <a href=\"#\" (click)=\"selectAllGroups()\">All</a>\r\n      </li>\r\n      <li [class.active]=\"filter && !filter.scenarioGroup\">\r\n        <a href=\"#\" (click)=\"selectGroup()\">Without group</a>\r\n      </li>\r\n      <li [class.active]=\"filter && scenarioGroup == filter.scenarioGroup\" *ngFor=\"let scenarioGroup of scenarioGroupList\">\r\n        <a href=\"#\" (click)=\"selectGroup(scenarioGroup)\">{{scenarioGroup}}</a>\r\n      </li>\r\n    </ul>\r\n  </nav>\r\n\r\n  <div class=\"container-fluid\" style=\"background-color: #f5f5f5; padding-top: 10px;\">\r\n    <div class=\"row\">\r\n      <div style=\"height: 40px;\" class=\"col-sm-6\">\r\n        <label>\r\n          <input class=\"select-all\" type=\"checkbox\" title=\"Select\" (click)=\"selectAll()\" />\r\n        </label>\r\n        <button class=\"btn btn-sm btn-default\" (click)=\"executeSelectedScenarios()\">Execute selected scenarios</button>\r\n      </div>\r\n      <div class=\"col-sm-1\" style=\"display: none; font-weight: bold;\" [style.color]=\"failCount > 0 ? 'red' : ''\">\r\n        {{failCount}}\r\n      </div>\r\n      <div class=\"col-sm-4\">\r\n        <div class=\"progress\" style=\"background-color: #fff;\">\r\n          <div class=\"progress-bar progress-bar-striped\"\r\n               role=\"progressbar\"\r\n               [class.active]=\"executingStateExecuting > 0\"\r\n               [style.display]=\"(executingStateExecuting + executingStateFinished) > 0 ? '' : 'none'\"\r\n               [style.width]=\"(5 + executingStateFinished / (executingStateExecuting + executingStateFinished) * 95) + '%'\">\r\n            {{Math.round(executingStateFinished / (executingStateExecuting + executingStateFinished) * 100)}} %\r\n            {{executingStateExecuting > 0 ? '(left: ' + executingStateExecuting + ')' : ''}}\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div style=\"min-height: 33px; margin-top: 7px; margin-bottom: 25px;\" [style.display]=\"isDisplayScenario(scenario) ? '' : 'none'\" *ngFor=\"let scenario of scenarioList\">\r\n    <app-scenario-list-item [scenario]=\"scenario\" [projectCode]=\"project.code\" (onStateChange)=\"onStateChange($event, scenario)\"></app-scenario-list-item>\r\n  </div>\r\n\r\n  <div class=\"container\">\r\n    <div class=\"panel panel-default\">\r\n      <div class=\"panel-body\">\r\n        <a class=\"btn btn-default\" target=\"_blank\" [attr.href]=\"globals.serviceBaseUrl + '/rest/projects/' + project.code + '/get-yaml'\">Download project as YAML</a>\r\n        <!--button class=\"btn btn-default\" (click)=\"downloadSelectedAsYaml()\">Download selected scenarios as YAML</button-->\r\n      </div>\r\n    </div>\r\n  </div>\r\n\r\n  <div class=\"container\">\r\n    <div class=\"panel panel-default\">\r\n      <div class=\"panel-body\">\r\n        <label>Create new scenario</label>\r\n        <div class=\"input-group\">\r\n          <input placeholder=\"Scenario name\" class=\"form-control\" title=\"New scenario name\" [(ngModel)]=\"newScenarioName\" />\r\n          <span class=\"input-group-btn\">\r\n            <button class=\"btn btn-success\" (click)=\"saveNewScenario()\"><span class=\"glyphicon glyphicon-plus\"></span> Create</button>\r\n          </span>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n\r\n</ng-container>\r\n<div class=\"help-block\" *ngIf=\"!project\">\r\n  <span class=\"glyphicon glyphicon-time\"></span>\r\n  Loading...\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/project-detail/project-detail.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_switchMap__ = __webpack_require__("../../../../rxjs/add/operator/switchMap.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_switchMap___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_switchMap__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__model_scenario__ = __webpack_require__("../../../../../src/app/model/scenario.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__scenario_list_item_scenario_list_item_component__ = __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectDetailComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var ProjectDetailComponent = (function () {
    function ProjectDetailComponent(globals, route, router, projectService, customToastyService) {
        this.globals = globals;
        this.route = route;
        this.router = router;
        this.projectService = projectService;
        this.customToastyService = customToastyService;
        this.selectAllFlag = false;
        this.failCount = 0;
        this.newScenarioName = '';
        this.scenarioGroupList = [];
        this.executingStateExecuting = 0;
        this.executingStateFinished = 0;
        this.Math = Math;
    }
    ProjectDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.paramMap
            .switchMap(function (params) { return _this.projectService.findOne(params.get('projectCode')); })
            .subscribe(function (value) {
            _this.project = value;
            _this.route
                .queryParams
                .subscribe(function (params) {
                if (params['scenarioGroup']) {
                    _this.filter = new __WEBPACK_IMPORTED_MODULE_4__model_scenario__["a" /* Scenario */]();
                    _this.filter.scenarioGroup = params['scenarioGroup'];
                }
            });
        });
        this.route.paramMap
            .switchMap(function (params) { return _this.projectService.findScenariosByProject(params.get('projectCode')); })
            .subscribe(function (value) {
            _this.scenarioList = value;
            _this.scenarioList
                .map(function (scenario) { return scenario.scenarioGroup; })
                .forEach(function (groupName) {
                if (groupName && _this.scenarioGroupList.indexOf(groupName) === -1) {
                    _this.scenarioGroupList.push(groupName);
                }
            });
        });
    };
    ProjectDetailComponent.prototype.ngAfterContentChecked = function () {
        this.updateFailCountSum();
    };
    ProjectDetailComponent.prototype.selectGroup = function (scenarioGroup) {
        this.filter = new __WEBPACK_IMPORTED_MODULE_4__model_scenario__["a" /* Scenario */]();
        this.filter.scenarioGroup = scenarioGroup;
        this.router.navigate([], { queryParams: { scenarioGroup: scenarioGroup ? scenarioGroup : -1 } });
        this.updateFailCountSum();
        return false;
    };
    ProjectDetailComponent.prototype.selectAllGroups = function () {
        this.filter = null;
        this.router.navigate([]);
        this.updateFailCountSum();
        return false;
    };
    /*
    downloadSelectedAsYaml() {
      const selectedScenarios = this.scenarioList
        .filter(value => this.isSelectedScenario(value))
        .map(value => value.id);
      this.projectService.downloadYaml(this.project, selectedScenarios)
        .subscribe(res => saveAs(res, 'PROJECT_' + this.project.code + '.yml'))
    }
    */
    ProjectDetailComponent.prototype.isSelectedScenario = function (scenario) {
        return scenario && scenario._selected && this.isDisplayScenario(scenario);
    };
    ProjectDetailComponent.prototype.isDisplayScenario = function (scenario) {
        return !this.filter ||
            (!this.filter.scenarioGroup && !scenario.scenarioGroup) ||
            (this.filter.scenarioGroup && scenario && scenario.scenarioGroup &&
                scenario.scenarioGroup === this.filter.scenarioGroup);
    };
    ProjectDetailComponent.prototype.executeSelectedScenarios = function () {
        var _this = this;
        this.scenarioComponentList
            .filter(function (item) { return item.scenario._selected && _this.isDisplayScenario(item.scenario); })
            .forEach(function (value) { return value.runScenario(); });
    };
    ProjectDetailComponent.prototype.selectAll = function () {
        var _this = this;
        this.selectAllFlag = !this.selectAllFlag;
        this.scenarioComponentList.forEach(function (item) { return item.scenario._selected = _this.selectAllFlag; });
    };
    ProjectDetailComponent.prototype.updateFailCountSum = function () {
        var _this = this;
        if (this.scenarioList) {
            this.failCount = this.scenarioList
                .filter(function (item) { return _this.isDisplayScenario(item); })
                .filter(function (value) { return value.failed; })
                .length;
        }
        else {
            this.failCount = 0;
        }
        return this.failCount;
    };
    ProjectDetailComponent.prototype.updateExecutionStatus = function () {
        this.executingStateExecuting = this.scenarioComponentList
            .filter(function (item) { return item.state === 'executing'; })
            .length;
        this.executingStateFinished = this.scenarioComponentList
            .filter(function (item) { return item.state === 'finished'; })
            .length;
    };
    // noinspection JSUnusedLocalSymbols
    ProjectDetailComponent.prototype.onStateChange = function (event, scenario) {
        this.updateExecutionStatus();
    };
    ProjectDetailComponent.prototype.saveNewScenario = function () {
        var _this = this;
        var newScenario = new __WEBPACK_IMPORTED_MODULE_4__model_scenario__["a" /* Scenario */]();
        newScenario.name = this.newScenarioName;
        var toasty = this.customToastyService.saving('Сохранение сценария...', 'Сохранение может занять некоторое время...');
        this.projectService.createScenario(this.project, newScenario)
            .subscribe(function (savedScenario) {
            _this.scenarioList.push(savedScenario);
            _this.newScenarioName = '';
            _this.customToastyService.success('Сохранено', 'Сценарий создан');
        }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    return ProjectDetailComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_15" /* ViewChildren */])(__WEBPACK_IMPORTED_MODULE_6__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */]),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["_16" /* QueryList */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["_16" /* QueryList */]) === "function" && _a || Object)
], ProjectDetailComponent.prototype, "scenarioComponentList", void 0);
ProjectDetailComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-project-detail',
        template: __webpack_require__("../../../../../src/app/project-detail/project-detail.component.html"),
        styles: ['input.select-all { width: 24px; height: 24px; margin: 0; vertical-align: middle; }']
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__globals__["a" /* Globals */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _f || Object])
], ProjectDetailComponent);

var _a, _b, _c, _d, _e, _f;
//# sourceMappingURL=project-detail.component.js.map

/***/ }),

/***/ "../../../../../src/app/project-list/project-list.component.html":
/***/ (function(module, exports) {

module.exports = "<h4>Projects</h4>\r\n<div class=\"help-block\" *ngIf=\"!projectList\">\r\n  <span class=\"glyphicon glyphicon-time\"></span>\r\n  Loading...\r\n</div>\r\n<table class=\"table table-condensed\" *ngIf=\"projectList\">\r\n  <tbody>\r\n    <tr *ngFor=\"let project of projectList\">\r\n      <td>\r\n        <a [routerLink]=\"['/project', project.code]\">{{project.name}}</a>\r\n      </td>\r\n      <td>\r\n        <ng-container *ngIf=\"project.stand\">\r\n          {{project.stand.serviceUrl}}\r\n        </ng-container>\r\n      </td>\r\n    </tr>\r\n  </tbody>\r\n</table>\r\n"

/***/ }),

/***/ "../../../../../src/app/project-list/project-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectListComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProjectListComponent = (function () {
    function ProjectListComponent(projectService) {
        this.projectService = projectService;
    }
    ProjectListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.projectService.findAll().subscribe(function (value) { return _this.projectList = value; });
    };
    return ProjectListComponent;
}());
ProjectListComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-project-list',
        template: __webpack_require__("../../../../../src/app/project-list/project-list.component.html")
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */]) === "function" && _a || Object])
], ProjectListComponent);

var _a;
//# sourceMappingURL=project-list.component.js.map

/***/ }),

/***/ "../../../../../src/app/project-settings/project-settings.component.html":
/***/ (function(module, exports) {

module.exports = "<ol class=\"breadcrumb\">\r\n  <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">Projects</a></li>\r\n  <li *ngIf=\"project\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project', project.code]\">{{project.code}}. {{project.name}}</a></li>\r\n  <li class=\"breadcrumb-item active\">Settings</li>\r\n</ol>\r\n\r\n<div class=\"container-fluid\" *ngIf=\"project\">\r\n  <h3>{{project.name}}</h3>\r\n  <button class=\"btn btn-success\" style=\"margin-bottom: 15px;\" (click)=\"save()\">Save project settings</button>\r\n  <div>\r\n    <ul class=\"nav nav-tabs\">\r\n      <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">Details</a></li>\r\n      <!--li [class.active]=\"tab == 'groups'\"><a href=\"#\" (click)=\"selectTab('groups')\">Scenario groups</a></li-->\r\n      <li [class.active]=\"tab == 'stand'\"><a href=\"#\" (click)=\"selectTab('stand')\">Stand</a></li>\r\n      <li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">json</a></li>\r\n    </ul>\r\n    <div class=\"tab-content\" style=\"padding: 10px;\">\r\n      <div [style.display]=\"tab == 'details' ? '' : 'none'\">\r\n        <label>Project name</label>\r\n        <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.name\"/>\r\n\r\n        <label>Before scenario</label>\r\n        <select class=\"form-control\" title=\"Before scenario\" [(ngModel)]=\"project.beforeScenarioPath\">\r\n          <option [ngValue]=\"null\">disabled</option>\r\n          <option *ngFor=\"let scenario of scenarioList\" [ngValue]=\"(scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code\">{{scenario.name}}</option>\r\n        </select>\r\n\r\n        <label>After scenario</label>\r\n        <select class=\"form-control\" title=\"Before scenario\" [(ngModel)]=\"project.afterScenarioPath\">\r\n          <option [ngValue]=\"null\">disabled</option>\r\n          <option *ngFor=\"let scenario of scenarioList\" [ngValue]=\"(scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code\">{{scenario.name}}</option>\r\n        </select>\r\n\r\n        <label>Project code</label>\r\n        <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.code\"/>\r\n\r\n        <hr/>\r\n        <label style=\"margin-top: 8px;\">\r\n          <input type=\"checkbox\" style=\"height: 25px;\" title=\"\" [(ngModel)]=\"project.useRandomTestId\"/>\r\n          Use random testId\r\n        </label>\r\n        <div class=\"clearfix\"></div>\r\n\r\n        <label>testId header name</label>\r\n        <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.testIdHeaderName\"/>\r\n\r\n        <hr/>\r\n        <label>AMQP broker</label>\r\n        <div *ngIf=\"project.amqpBroker\">\r\n          <label>Broker type</label>\r\n          <div class=\"input-group-btn\">\r\n            <select class=\"form-control\" title=\"AMQP broker type\" [(ngModel)]=\"project.amqpBroker.mqService\">\r\n              <option [ngValue]=\"null\"></option>\r\n              <option [ngValue]=\"'ACTIVE_MQ'\">Active MQ</option>\r\n              <option [ngValue]=\"'RABBIT_MQ'\">Rabbit MQ</option>\r\n            </select>\r\n          </div>\r\n\r\n          <label>Host</label>\r\n          <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.amqpBroker.host\"/>\r\n\r\n          <label>Port</label>\r\n          <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.amqpBroker.port\"/>\r\n\r\n          <label>Username</label>\r\n          <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.amqpBroker.username\"/>\r\n\r\n          <label>Password</label>\r\n          <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.amqpBroker.password\"/>\r\n\r\n          <button class=\"btn btn-default\" (click)=\"removeAmqpBroker()\"><span class=\"glyphicon glyphicon-minus\"></span> Remove broker</button>\r\n        </div>\r\n        <div *ngIf=\"!project.amqpBroker\">\r\n          <button class=\"btn btn-default\" (click)=\"addAmqpBroker()\"><span class=\"glyphicon glyphicon-plus\"></span> Add broker</button>\r\n        </div>\r\n      </div>\r\n      <!--div [style.display]=\"tab == 'groups' ? '' : 'none'\">\r\n        <div *ngFor=\"let scenarioGroup of project.scenarioGroups\">\r\n          <label>Group name</label>\r\n          <div class=\"input-group\">\r\n            <input class=\"form-control\" title=\"\" [(ngModel)]=\"scenarioGroup.name\"/>\r\n            <span class=\"input-group-btn\">\r\n              <button class=\"btn btn-default\" (click)=\"removeScenarioGroup(scenarioGroup)\"><span class=\"glyphicon glyphicon-minus\"></span> Remove</button>\r\n            </span>\r\n          </div>\r\n        </div>\r\n        <button class=\"btn btn-default\" style=\"margin-top: 7px;\" (click)=\"addScenarioGroup()\"><span class=\"glyphicon glyphicon-plus\"></span> Add scenario group</button>\r\n      </div-->\r\n      <div [style.display]=\"tab == 'stand' ? '' : 'none'\">\r\n        <div style=\"border: none;\" class=\"list-group-item\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>Service URL</label>\r\n              <div class=\"input-group\">\r\n                <input placeholder=\"Service URL\" class=\"form-control\" title=\"\" [(ngModel)]=\"project.stand.serviceUrl\"/>\r\n              </div>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-4\">\r\n              <input placeholder=\"Data base URL\" class=\"form-control\" title=\"\" [(ngModel)]=\"project.stand.dbUrl\"/>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <input placeholder=\"Data base User\" class=\"form-control\" title=\"\" [(ngModel)]=\"project.stand.dbUser\"/>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <input placeholder=\"Data base Password\" type=\"password\" class=\"form-control\" title=\"\" [(ngModel)]=\"project.stand.dbPassword\"/>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <input placeholder=\"WireMock URL\" class=\"form-control\" title=\"\" [(ngModel)]=\"project.stand.wireMockUrl\"/>\r\n            </div>\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <div [style.display]=\"tab == 'json' ? '' : 'none'\">\r\n        <pre>{{project | json}}</pre>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/project-settings/project-settings.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__model_amqp_broker__ = __webpack_require__("../../../../../src/app/model/amqp-broker.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectSettingsComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ProjectSettingsComponent = (function () {
    function ProjectSettingsComponent(projectService, route, customToastyService) {
        this.projectService = projectService;
        this.route = route;
        this.customToastyService = customToastyService;
        this.tab = 'details';
    }
    ProjectSettingsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.paramMap
            .switchMap(function (params) { return _this.projectService.findOne(params.get('projectCode')); })
            .subscribe(function (value) {
            _this.project = value;
            _this.projectService.findScenariosByProject(_this.project.code)
                .subscribe(function (scenarioList) { return _this.scenarioList = scenarioList; });
        });
    };
    ProjectSettingsComponent.prototype.save = function () {
        var _this = this;
        var toasty = this.customToastyService.saving('Сохранение...', 'Сохранение может занять некоторое время...');
        this.projectService.save(this.project)
            .subscribe(function (value) {
            _this.project = value;
            _this.customToastyService.success('Сохранено', 'Параметры проекта сохранены');
        }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    ProjectSettingsComponent.prototype.selectTab = function (tabName) {
        this.tab = tabName;
        return false;
    };
    ProjectSettingsComponent.prototype.selectAsDefaultStand = function (stand) {
        this.project.stand = stand;
    };
    ProjectSettingsComponent.prototype.addAmqpBroker = function () {
        this.project.amqpBroker = new __WEBPACK_IMPORTED_MODULE_4__model_amqp_broker__["a" /* AmqpBroker */]();
    };
    ProjectSettingsComponent.prototype.removeAmqpBroker = function () {
        if (confirm('Confirm: remove AMQP broker')) {
            this.project.amqpBroker = null;
        }
    };
    return ProjectSettingsComponent;
}());
ProjectSettingsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-project-settings',
        template: __webpack_require__("../../../../../src/app/project-settings/project-settings.component.html"),
        styles: [
            '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
            '.row {margin-bottom: 4px;}',
            'input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _c || Object])
], ProjectSettingsComponent);

var _a, _b, _c;
//# sourceMappingURL=project-settings.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-detail/scenario-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<div>\r\n  <ol class=\"breadcrumb\" *ngIf=\"scenario\">\r\n    <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">Projects</a></li>\r\n    <li class=\"breadcrumb-item\"><a [routerLink]=\"['/project', scenario.projectCode]\">{{scenario.projectCode}}. {{scenario.projectName}}</a></li>\r\n    <li class=\"breadcrumb-item\" *ngIf=\"scenario.scenarioGroup\">\r\n      <a [routerLink]=\"['/project', scenario.projectCode]\" [queryParams]=\"{scenarioGroup: scenario.scenarioGroup}\">{{scenario.scenarioGroup}}</a>\r\n    </li>\r\n    <li class=\"breadcrumb-item active\">{{scenario.code}}. {{scenario.name}}</li>\r\n  </ol>\r\n\r\n  <div *ngIf=\"scenario\">\r\n    <div style=\"margin-bottom: 10px;\">\r\n      <h3>{{scenario.name}}</h3>\r\n      <a [routerLink]=\"['/project/' + projectCode + '/scenario/' + (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code + '/settings']\">Settings</a>\r\n    </div>\r\n    <app-scenario-list-item [scenario]=\"scenario\" [projectCode]=\"projectCode\"></app-scenario-list-item>\r\n  </div>\r\n  <hr/>\r\n  <div class=\"help-block\" *ngIf=\"!stepList\">\r\n    <span class=\"glyphicon glyphicon-time\"></span>\r\n    Loading...\r\n  </div>\r\n  <div *ngIf=\"scenario && stepList\">\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-success\" (click)=\"saveSteps()\">Save steps</button>\r\n    </div>\r\n\r\n    <div *ngFor=\"let step of stepList\">\r\n      <div style=\"margin-bottom: 15px;\" class=\"col-sm-offset-11 col-sm-1 text-right\">\r\n        <button style=\"white-space: normal;\" class=\"btn btn-xs btn-block btn-default\" (click)=\"addStepBefore(step)\"><span class=\"glyphicon glyphicon-hand-left\"></span> Insert step</button>\r\n      </div>\r\n      <app-step-item\r\n        [step]=\"step\"\r\n        [showUpDownDeleteCloneButtons]=\"true\"\r\n        (onDeleteClick)=\"onDeleteClick(step)\"\r\n        (onUpClick)=\"onUpClick(step)\"\r\n        (onDownClick)=\"onDownClick(step)\"\r\n        (onCloneClick)=\"onCloneClick(step)\"\r\n      ></app-step-item>\r\n    </div>\r\n\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-default\" (click)=\"addStep()\"><span class=\"glyphicon glyphicon-plus\"></span> Add step</button>\r\n    </div>\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-success\" (click)=\"saveSteps()\">Save steps</button>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/scenario-detail/scenario-detail.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_step__ = __webpack_require__("../../../../../src/app/model/step.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_switchMap__ = __webpack_require__("../../../../rxjs/add/operator/switchMap.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_switchMap___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_switchMap__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioDetailComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var ScenarioDetailComponent = (function () {
    function ScenarioDetailComponent(route, scenarioService, stepService, customToastyService) {
        this.route = route;
        this.scenarioService = scenarioService;
        this.stepService = stepService;
        this.customToastyService = customToastyService;
    }
    ScenarioDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            _this.projectCode = params['projectCode'];
            _this.scenarioService
                .findOne(_this.projectCode, params['scenarioGroup'], params['scenarioCode'])
                .subscribe(function (value) { return _this.scenario = value; });
            _this.scenarioService
                .findScenarioSteps(_this.projectCode, params['scenarioGroup'], params['scenarioCode'])
                .subscribe(function (value) { return _this.stepList = value; });
        });
    };
    ScenarioDetailComponent.prototype.saveSteps = function () {
        var _this = this;
        if (this.scenario && this.stepList) {
            var toasty_1 = this.customToastyService.saving();
            this.scenarioService.saveStepList(this.projectCode, this.scenario, this.stepList)
                .subscribe(function (savedStepList) {
                _this.customToastyService.success('Сохранено', 'Шаги сохранены');
                _this.stepList = savedStepList;
            }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty_1); });
        }
    };
    ScenarioDetailComponent.prototype.addStep = function () {
        if (!this.stepList) {
            this.stepList = [];
        }
        this.stepList.push(new __WEBPACK_IMPORTED_MODULE_1__model_step__["a" /* Step */]());
    };
    ScenarioDetailComponent.prototype.onDeleteClick = function (step) {
        if (confirm('Confirm: delete step')) {
            var indexToRemove = this.stepList.indexOf(step);
            if (indexToRemove > -1) {
                this.stepList.splice(indexToRemove, 1);
            }
        }
    };
    ScenarioDetailComponent.prototype.addStepBefore = function (step) {
        var addAfterIndex = this.stepList.indexOf(step);
        if (addAfterIndex > -1) {
            this.stepList.splice(addAfterIndex, 0, new __WEBPACK_IMPORTED_MODULE_1__model_step__["a" /* Step */]());
        }
    };
    ScenarioDetailComponent.prototype.onUpClick = function (step) {
        var index = this.stepList.indexOf(step);
        if (index > 0) {
            var tmpStep = this.stepList[index];
            this.stepList[index] = this.stepList[index - 1];
            this.stepList[index - 1] = tmpStep;
        }
    };
    ScenarioDetailComponent.prototype.onDownClick = function (step) {
        var index = this.stepList.indexOf(step);
        if (index > -1 && index < this.stepList.length - 1) {
            var tmpStep = this.stepList[index];
            this.stepList[index] = this.stepList[index + 1];
            this.stepList[index + 1] = tmpStep;
        }
    };
    ScenarioDetailComponent.prototype.onCloneClick = function (step) {
        var _this = this;
        if (confirm('Confirm: Clone step')) {
            var toasty_2 = this.customToastyService.saving('Создание клона шага...', 'Создание может занять некоторое время...');
            this.stepService.cloneStep(this.projectCode, this.scenario.scenarioGroup, this.scenario.code, step)
                .subscribe(function (clonedStep) {
                _this.stepList.push(clonedStep);
                _this.customToastyService.success('Сохранено', 'Шаг склонирован');
            }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty_2); });
        }
    };
    return ScenarioDetailComponent;
}());
ScenarioDetailComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-detail',
        template: __webpack_require__("../../../../../src/app/scenario-detail/scenario-detail.component.html")
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_5__service_step_service__["a" /* StepService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__service_step_service__["a" /* StepService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_6__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _d || Object])
], ScenarioDetailComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=scenario-detail.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-list-item/scenario-list-item.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\" *ngIf=\"scenario\">\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-5\">\r\n      <label>\r\n        <input type=\"checkbox\" title=\"Select\" [(ngModel)]=\"scenario._selected\" />\r\n        {{scenario.name}}\r\n      </label>\r\n    </div>\r\n    <div class=\"col-sm-1\"></div>\r\n    <div class=\"col-sm-1\">\r\n      <div *ngIf=\"state != 'executing' && scenario.failed\" style=\"color: red\">Failed</div>\r\n      <div *ngIf=\"state == 'executing'\" style=\"color: gray\">...</div>\r\n    </div>\r\n    <div class=\"col-sm-2\">\r\n      <button class=\"btn btn-primary\" *ngIf=\"state != 'executing'\" (click)=\"runScenario()\">Run</button>\r\n      <button class=\"btn btn-warning\" *ngIf=\"state == 'executing'\" disabled>Executing...</button>\r\n    </div>\r\n    <div class=\"col-sm-2\">\r\n      <button class=\"btn btn-\" *ngIf=\"stepResultList\" (click)=\"resultDetailsToggle()\">Show results</button>\r\n    </div>\r\n    <div class=\"col-sm-1\">\r\n      <a class=\"btn btn-default\" [routerLink]=\"['/project/' + projectCode + '/scenario/' + (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code]\">Edit</a>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\" *ngIf=\"showResultDetails && stepResultList\">\r\n    <ng-container *ngFor=\"let stepResult of stepResultList\">\r\n      <app-step-result-item [stepResult]=\"stepResult\"></app-step-result-item>\r\n    </ng-container>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/scenario-list-item/scenario-list-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_scenario__ = __webpack_require__("../../../../../src/app/model/scenario.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioListItemComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ScenarioListItemComponent = (function () {
    function ScenarioListItemComponent(scenarioService) {
        this.scenarioService = scenarioService;
        this.onStateChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.state = 'none';
        this.showResultDetails = false;
    }
    ScenarioListItemComponent.prototype.ngOnInit = function () {
        this.scenario._selected = false;
    };
    ScenarioListItemComponent.prototype.stateChanged = function () {
        this.onStateChange.emit({ state: this.state });
    };
    ScenarioListItemComponent.prototype.runScenario = function () {
        var _this = this;
        if (this.state !== 'executing') {
            this.state = 'executing';
            this.stateChanged();
            this.scenarioService.run(this.projectCode, this.scenario)
                .subscribe(function (value) {
                _this.stepResultList = value;
                _this.state = 'finished';
                _this.stateChanged();
                _this.scenario.failed = value.filter(function (value2) { return value2.result === 'Fail'; }).length > 0;
            });
        }
    };
    ScenarioListItemComponent.prototype.resultDetailsToggle = function () {
        this.showResultDetails = !this.showResultDetails;
    };
    return ScenarioListItemComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_scenario__["a" /* Scenario */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_scenario__["a" /* Scenario */]) === "function" && _a || Object)
], ScenarioListItemComponent.prototype, "scenario", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], ScenarioListItemComponent.prototype, "projectCode", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], ScenarioListItemComponent.prototype, "onStateChange", void 0);
ScenarioListItemComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-list-item',
        template: __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.html"),
        styles: [' input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }']
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */]) === "function" && _b || Object])
], ScenarioListItemComponent);

var _a, _b;
//# sourceMappingURL=scenario-list-item.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-settings/scenario-settings.component.html":
/***/ (function(module, exports) {

module.exports = "<ol class=\"breadcrumb\">\r\n  <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">Projects</a></li>\r\n  <li *ngIf=\"project\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project', project.code]\">{{project.code}}. {{project.name}}</a></li>\r\n  <li class=\"breadcrumb-item\" *ngIf=\"scenario && scenario.scenarioGroup && project\">\r\n    <a [routerLink]=\"['/project', scenario.projectCode]\" [queryParams]=\"{scenarioGroup: scenario.scenarioGroup}\">{{scenario.scenarioGroup}}</a>\r\n  </li>\r\n  <li *ngIf=\"scenario\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project/' + projectCode + '/scenario', (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code]\">{{scenario.code}}. {{scenario.name}}</a></li>\r\n  <li class=\"breadcrumb-item active\">Settings</li>\r\n</ol>\r\n\r\n<div *ngIf=\"scenario\">\r\n  <button style=\"margin: 15px;\" class=\"btn btn-success\" (click)=\"save()\">Save scenario settings</button>\r\n\r\n  <div class=\"container-fluid\">\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>Name</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <input class=\"form-control\" title=\"Scenario name\" [(ngModel)]=\"scenario.name\"/>\r\n      </div>\r\n    </div>\r\n\r\n    <!--div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>Scenario group</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <select class=\"form-control\" title=\"Scenario group\" *ngIf=\"project\" [(ngModel)]=\"scenario.scenarioGroup\">\r\n          <option [ngValue]=\"null\">[no group]</option>\r\n          <option *ngFor=\"let scenarioGroup of scenarioGroupList\" [ngValue]=\"scenarioGroup\">{{scenarioGroup}}</option>\r\n        </select>\r\n      </div>\r\n    </div-->\r\n\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>Before scenario ignore</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <input type=\"checkbox\" title=\"\" [(ngModel)]=\"scenario.beforeScenarioIgnore\"/>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>After scenario ignore</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n          <input type=\"checkbox\" title=\"\" [(ngModel)]=\"scenario.afterScenarioIgnore\"/>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/scenario-settings/scenario-settings.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioSettingsComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ScenarioSettingsComponent = (function () {
    function ScenarioSettingsComponent(route, scenarioService, projectService, customToastyService) {
        this.route = route;
        this.scenarioService = scenarioService;
        this.projectService = projectService;
        this.customToastyService = customToastyService;
    }
    ScenarioSettingsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            _this.projectCode = params['projectCode'];
            _this.scenarioService
                .findOne(_this.projectCode, params['scenarioGroup'], params['scenarioCode'])
                .subscribe(function (value) { return _this.scenario = value; });
            _this.projectService
                .findOne(_this.projectCode)
                .subscribe(function (project) { return _this.project = project; });
        });
    };
    ScenarioSettingsComponent.prototype.save = function () {
        var _this = this;
        var toasty = this.customToastyService.saving();
        this.scenarioService.saveOne(this.project.code, this.scenario)
            .subscribe(function (value) {
            _this.scenario = value;
            _this.customToastyService.success('Сохранено', 'Сценарий сохранен');
        }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    return ScenarioSettingsComponent;
}());
ScenarioSettingsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-settings',
        template: __webpack_require__("../../../../../src/app/scenario-settings/scenario-settings.component.html"),
        styles: [
            'input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }',
            '.row { margin-bottom: 7px; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__["a" /* ScenarioService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_project_service__["a" /* ProjectService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _d || Object])
], ScenarioSettingsComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=scenario-settings.component.js.map

/***/ }),

/***/ "../../../../../src/app/service/custom-toasty.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ng2_toasty__ = __webpack_require__("../../../../ng2-toasty/index.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CustomToastyService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var CustomToastyService = (function () {
    function CustomToastyService(toastyService) {
        this.toastyService = toastyService;
    }
    CustomToastyService.prototype.saving = function (title, msg) {
        this.toastyService.clearAll();
        this.toastyService.warning({
            title: title ? title : 'Сохранение',
            msg: msg ? msg : 'Сохранение может занять некоторое время...',
            timeout: 100000,
            showClose: true,
            theme: 'bootstrap'
        });
        return this.toastyService.uniqueCounter;
    };
    CustomToastyService.prototype.success = function (title, msg) {
        this.toastyService.success({
            title: title,
            msg: msg,
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap'
        });
        return this.toastyService.uniqueCounter;
    };
    CustomToastyService.prototype.error = function (title, msg) {
        this.toastyService.error({
            title: title,
            msg: msg,
            timeout: 500000,
            showClose: true,
            theme: 'bootstrap'
        });
        return this.toastyService.uniqueCounter;
    };
    CustomToastyService.prototype.clear = function (id) {
        this.toastyService.clear(id);
    };
    return CustomToastyService;
}());
CustomToastyService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_ng2_toasty__["b" /* ToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_ng2_toasty__["b" /* ToastyService */]) === "function" && _a || Object])
], CustomToastyService);

var _a;
//# sourceMappingURL=custom-toasty.service.js.map

/***/ }),

/***/ "../../../../../src/app/service/project.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ProjectService = (function () {
    function ProjectService(globals, http) {
        this.globals = globals;
        this.http = http;
        this.serviceUrl = '/rest/projects';
        this.headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Headers */]({ 'Content-Type': 'application/json' });
    }
    ProjectService.prototype.findAll = function () {
        return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl)
            .map(function (value) { return value.json(); });
    };
    ProjectService.prototype.save = function (project) {
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + project.code, project, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ProjectService.prototype.findOne = function (projectCode) {
        return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode)
            .map(function (value) { return value.json(); });
    };
    ProjectService.prototype.findScenariosByProject = function (projectCode) {
        return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios')
            .map(function (value) { return value.json(); });
    };
    ProjectService.prototype.createScenario = function (project, scenario) {
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + project.code + '/scenarios', scenario, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    return ProjectService;
}());
ProjectService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]) === "function" && _b || Object])
], ProjectService);

var _a, _b;
//# sourceMappingURL=project.service.js.map

/***/ }),

/***/ "../../../../../src/app/service/scenario.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ScenarioService = (function () {
    function ScenarioService(globals, http) {
        this.globals = globals;
        this.http = http;
        this.serviceUrl = '/rest/projects';
        this.headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Headers */]({ 'Content-Type': 'application/json' });
    }
    ScenarioService.prototype.run = function (projectCode, scenario) {
        var scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/exec', {}, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.findOne = function (projectCode, scenarioGroup, scenarioCode) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http
            .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath)
            .map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.findScenarioSteps = function (projectCode, scenarioGroup, scenarioCode) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http
            .get(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps')
            .map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.saveStepList = function (projectCode, scenario, stepList) {
        var scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps', stepList, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.saveOne = function (projectCode, scenario) {
        var scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath, scenario, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    return ScenarioService;
}());
ScenarioService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]) === "function" && _b || Object])
], ScenarioService);

var _a, _b;
//# sourceMappingURL=scenario.service.js.map

/***/ }),

/***/ "../../../../../src/app/service/step.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var StepService = (function () {
    function StepService(globals, http) {
        this.globals = globals;
        this.http = http;
        this.serviceUrl = '/rest/projects';
        this.headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Headers */]({ 'Content-Type': 'application/json' });
    }
    StepService.prototype.saveStep = function (projectCode, scenarioGroup, scenarioCode, step) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code, step, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    StepService.prototype.cloneStep = function (projectCode, scenarioGroup, scenarioCode, step) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code + '/clone', {}, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    return StepService;
}());
StepService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__globals__["a" /* Globals */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]) === "function" && _b || Object])
], StepService);

var _a, _b;
//# sourceMappingURL=step.service.js.map

/***/ }),

/***/ "../../../../../src/app/service/version.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return VersionService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var VersionService = (function () {
    function VersionService(globals, http) {
        this.globals = globals;
        this.http = http;
        this.serviceUrl = '/rest/version';
    }
    VersionService.prototype.getVersion = function () {
        return this.http.get(this.globals.serviceBaseUrl + this.serviceUrl)
            .map(function (value) { return value.json(); });
    };
    return VersionService;
}());
VersionService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__globals__["a" /* Globals */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]) === "function" && _b || Object])
], VersionService);

var _a, _b;
//# sourceMappingURL=version.service.js.map

/***/ }),

/***/ "../../../../../src/app/step-item/step-item.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\" style=\"padding-bottom: 30px;\">\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-11\">\r\n      <input style=\"font-weight: bold; border-width: 0; box-shadow: none;\" placeholder=\"Step description\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.stepComment\" />\r\n    </div>\r\n    <div class=\"col-sm-1 text-right\" *ngIf=\"showUpDownDeleteCloneButtons\">\r\n      <button class=\"btn btn-xs btn-block btn-default\" (click)=\"upStep()\"><span class=\"glyphicon glyphicon-hand-up\"></span> Up</button>\r\n      <div class=\"clearfix\"></div>\r\n      <button class=\"btn btn-xs btn-block btn-default\" (click)=\"downStep()\"><span class=\"glyphicon glyphicon-hand-down\"></span> Down</button>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <div class=\"input-group\">\r\n        <div class=\"input-group-btn\">\r\n          <select class=\"form-control\" title=\"Request method\" [(ngModel)]=\"step.requestMethod\">\r\n            <option [ngValue]=\"''\"></option>\r\n            <option [ngValue]=\"'POST'\">POST</option>\r\n            <option [ngValue]=\"'GET'\">GET</option>\r\n            <option [ngValue]=\"'PUT'\">PUT</option>\r\n            <option [ngValue]=\"'DELETE'\">DELETE</option>\r\n          </select>\r\n        </div>\r\n        <input placeholder=\"Relative url. Example: /relative/url?parameter=value\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.relativeUrl\" />\r\n        <div class=\"input-group-btn\">\r\n          <button class=\"btn btn-default\" title=\"Delete\" *ngIf=\"showUpDownDeleteCloneButtons\" (click)=\"deleteStep()\"><span class=\"glyphicon glyphicon-minus\"></span> Delete</button>\r\n          <button class=\"btn btn-default\" title=\"Disabled toggle\" *ngIf=\"!step.disabled\" (click)=\"disabledToggle()\"><span class=\"glyphicon glyphicon-flag\"></span> Disable</button>\r\n          <button class=\"btn btn-default\" title=\"Disabled toggle\" style=\"color: red;\" *ngIf=\"step.disabled\" (click)=\"disabledToggle()\"><span class=\"glyphicon glyphicon-flag\"></span> Disabled</button>\r\n          <button class=\"btn btn-success\" title=\"Clone\" *ngIf=\"showUpDownDeleteCloneButtons && step.code\" (click)=\"cloneStep()\"><span class=\"glyphicon glyphicon-paste\"></span> Clone</button>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <ul class=\"nav nav-tabs\">\r\n        <li [class.active]=\"tab == 'summary'\"><a href=\"#\" (click)=\"selectTab('summary')\">Summary</a></li>\r\n        <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">Details</a></li>\r\n        <li [class.active]=\"tab == 'savedValues'\">\r\n          <a href=\"#\" (click)=\"selectTab('savedValues')\">Saved values <span *ngIf=\"step.jsonXPath || (step.savedValuesCheck && Object.keys(step.savedValuesCheck).length)\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'headers'\">\r\n          <a href=\"#\" (click)=\"selectTab('headers')\">Headers <span *ngIf=\"step.requestHeaders\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'sql'\">\r\n          <a href=\"#\" (click)=\"selectTab('sql')\">Sql <span *ngIf=\"step.sql || step.sqlSavedParameter\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'mockServiceResponse'\">\r\n          <a href=\"#\" (click)=\"selectTab('mockServiceResponse')\">Mock responses <span *ngIf=\"step.mockServiceResponseList && step.mockServiceResponseList.length != 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'expectedServiceRequestList'\">\r\n          <a href=\"#\" (click)=\"selectTab('expectedServiceRequestList')\">Expected mock requests <span *ngIf=\"step.expectedServiceRequestList && step.expectedServiceRequestList.length != 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'mq'\">\r\n          <a href=\"#\" (click)=\"selectTab('mq')\">Message Queue <span *ngIf=\"step.mqName || step.mqMessage\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'polling'\">\r\n          <a href=\"#\" (click)=\"selectTab('polling')\">Polling <span *ngIf=\"step.usePolling\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'parameterSet'\">\r\n          <a href=\"#\" (click)=\"selectTab('parameterSet')\">Test cases <span *ngIf=\"step.stepParameterSetList && step.stepParameterSetList.length != 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">json</a></li>\r\n      </ul>\r\n      <div class=\"tab-content\" style=\"padding: 10px;\">\r\n        <div class=\"row\" *ngIf=\"tab == 'summary'\">\r\n          <div class=\"col-sm-6\">\r\n            <textarea title=\"Request body\" class=\"form-control\" rows=\"5\" [(ngModel)]=\"step.request\"></textarea>\r\n          </div>\r\n          <div class=\"col-sm-6\">\r\n            <textarea title=\"Expected response\" class=\"form-control\" rows=\"5\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n          </div>\r\n        </div>\r\n        <div class=\"row\" *ngIf=\"tab == 'details' || tab == 'all'\">\r\n          <div class=\"col-sm-6\">\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-12\">\r\n                <label>Request body type</label>\r\n                <select class=\"form-control\" title=\"Request body type\" [(ngModel)]=\"step.requestBodyType\">\r\n                  <option [ngValue]=\"'JSON'\">JSON request body (default)</option>\r\n                  <option [ngValue]=\"'FORM'\">FORM-data request body</option>\r\n                </select>\r\n              </div>\r\n            </div>\r\n            <label>Request body</label>\r\n            <div *ngIf=\"step.requestBodyType != 'FORM'\">\r\n              <textarea title=\"Request body\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.request\"></textarea>\r\n            </div>\r\n            <div *ngIf=\"step.requestBodyType == 'FORM'\">\r\n              <div *ngFor=\"let formData of step.formDataList\">\r\n                <div class=\"col-sm-4\">\r\n                  <input class=\"form-control\" placeholder=\"Field name\" title=\"\" [(ngModel)]=\"formData.fieldName\"/>\r\n                </div>\r\n                <div class=\"col-sm-3\">\r\n                  <select class=\"form-control\" title=\"Field type\" [(ngModel)]=\"formData.fieldType\">\r\n                    <option [ngValue]=\"'TEXT'\">Text (default)</option>\r\n                    <option [ngValue]=\"'FILE'\">File</option>\r\n                  </select>\r\n                </div>\r\n                <div class=\"col-sm-4\" *ngIf=\"formData.fieldType == 'FILE'\">\r\n                  <input class=\"form-control\" placeholder=\"File path\" title=\"\" [(ngModel)]=\"formData.filePath\"/>\r\n                </div>\r\n                <div class=\"col-sm-4\" *ngIf=\"formData.fieldType != 'FILE'\">\r\n                  <input class=\"form-control\" placeholder=\"Field value\" title=\"\" [(ngModel)]=\"formData.value\"/>\r\n                </div>\r\n                <div class=\"col-sm-1\">\r\n                  <button class=\"btn btn-default\" (click)=\"removeFormData(formData)\">-</button>\r\n                </div>\r\n              </div>\r\n              <button class=\"btn\" style=\"margin-top: 7px; margin-left: 7px;\" (click)=\"addFormData()\">Add field</button>\r\n            </div>\r\n          </div>\r\n          <div class=\"col-sm-6\">\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-4\" style=\"text-align: center; padding-top: 10px;\">\r\n                <label>\r\n                  <input title=\"\" type=\"checkbox\" [(ngModel)]=\"step.expectedResponseIgnore\"><br/>\r\n                  Ignore response\r\n                </label>\r\n              </div>\r\n              <div class=\"col-sm-4\">\r\n                <label>Compare mode</label>\r\n                <select class=\"form-control\" title=\"Request body type\" [(ngModel)]=\"step.responseCompareMode\">\r\n                  <option [ngValue]=\"'JSON'\">JSON (default)</option>\r\n                  <option [ngValue]=\"'FULL_MATCH'\">Full match</option>\r\n                  <option [ngValue]=\"'IGNORE_MASK'\">Mask *ignore*</option>\r\n                </select>\r\n              </div>\r\n              <div class=\"col-sm-4\">\r\n                <label>Expected status</label>\r\n                <input title=\"\" placeholder=\"Example: 200, 404, 500, [empty]\" class=\"form-control\" [(ngModel)]=\"step.expectedStatusCode\"/>\r\n              </div>\r\n            </div>\r\n            <label>Expected response</label>\r\n            <textarea title=\"Expected response\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'savedValues' || tab == 'all'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-6\">\r\n              <label>Expected response</label>\r\n              <textarea title=\"Expected response\" class=\"form-control\" rows=\"13\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n            </div>\r\n            <div class=\"col-sm-6\">\r\n              <label>Saved values (JSON XPath):</label>\r\n              <textarea placeholder=\"parameterName = $.element.items[2].title\" rows=\"13\" title=\"Request headers\" class=\"form-control\" [(ngModel)]=\"step.jsonXPath\"></textarea>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'headers' || tab == 'all'\">\r\n          <label>Request headers:</label>\r\n          <textarea placeholder=\"HeaderName: headerValue\" rows=\"5\" title=\"Request headers\" class=\"form-control\" [(ngModel)]=\"step.requestHeaders\"></textarea>\r\n        </div>\r\n        <div *ngIf=\"tab == 'sql' || tab == 'all'\">\r\n          <label>Sql parameters</label>\r\n          <input placeholder=\"paramNameFirst, paramNameSecond\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.sqlSavedParameter\"/>\r\n          <label>Sql query</label>\r\n          <input placeholder=\"select fieldFirst, fieldSecond from myTable\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.sql\"/>\r\n        </div>\r\n        <div *ngIf=\"tab == 'mockServiceResponse' || tab == 'all'\">\r\n          <div class=\"row\" *ngFor=\"let mockServiceResponse of step.mockServiceResponseList\">\r\n            <div class=\"col-sm-1\">\r\n              <button class=\"btn btn-sm btn-default\" style=\"line-height: 1.9;\" (click)=\"removeMockServiceResponse(mockServiceResponse)\"><span class=\"glyphicon glyphicon-minus\"></span> Remove</button>\r\n            </div>\r\n            <div class=\"col-sm-11\">\r\n              <app-mock-service-response [mockServiceResponse]=\"mockServiceResponse\"></app-mock-service-response>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"addMockServiceResponse()\"><span class=\"glyphicon glyphicon-plus\"></span> Add mock</button>\r\n        </div>\r\n        <div *ngIf=\"tab == 'expectedServiceRequestList' || tab == 'all'\">\r\n          <div class=\"row\" style=\"margin-bottom: 40px;\" *ngFor=\"let expectedServiceRequest of step.expectedServiceRequestList\">\r\n            <div class=\"col-sm-12\">\r\n              <div class=\"input-group\" style=\"margin-bottom: 5px;\">\r\n                <input placeholder=\"Service name *\" class=\"form-control\" title=\"\" [style.border-color]=\"expectedServiceRequest.serviceName ? '' : 'red'\" [(ngModel)]=\"expectedServiceRequest.serviceName\"/>\r\n                <span class=\"input-group-btn\">\r\n                  <button class=\"btn\" (click)=\"removeExpectedServiceRequest(expectedServiceRequest)\"><span class=\"glyphicon glyphicon-minus\"></span> Remove</button>\r\n                </span>\r\n              </div>\r\n              <div class=\"row\">\r\n                <div class=\"col-sm-12\">\r\n                  <input class=\"form-control\" placeholder=\"Ignored tags\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"expectedServiceRequest.ignoredTags\"/>\r\n                </div>\r\n              </div>\r\n              <textarea class=\"form-control\" placeholder=\"Expected request\" title=\"\" rows=\"7\" [(ngModel)]=\"expectedServiceRequest.expectedServiceRequest\"></textarea>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-default\" (click)=\"addExpectedServiceRequest()\"><span class=\"glyphicon glyphicon-plus\"></span> Add expected mock request</button>\r\n        </div>\r\n        <div *ngIf=\"tab == 'mq' || tab == 'all'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>Queue name</label>\r\n              <input title=\"Queue name\" class=\"form-control\" [(ngModel)]=\"step.mqName\"/>\r\n            </div>\r\n            <div class=\"col-sm-12\">\r\n              <label>Queue message</label>\r\n              <textarea class=\"form-control\" placeholder=\"Queue message\" title=\"\" rows=\"7\" [(ngModel)]=\"step.mqMessage\"></textarea>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'polling' || tab == 'all'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-2\">\r\n              <label>\r\n                <input type=\"checkbox\" title=\"Use polling\" [(ngModel)]=\"step.usePolling\"/>\r\n                Use polling\r\n              </label>\r\n            </div>\r\n            <div class=\"col-sm-10\">\r\n              <label>Polling json xpath:</label>\r\n              <input placeholder=\"$.body.items\" class=\"form-control\" title=\"Polling json xpath\" [(ngModel)]=\"step.pollingJsonXPath\" />\r\n              <div class=\"help-block\">Waiting for json element exists. Example: $.body.items</div>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'parameterSet' || tab == 'all'\">\r\n          <app-step-parameter-set [stepParameterSetList]=\"step.stepParameterSetList\"></app-step-parameter-set>\r\n        </div>\r\n        <div *ngIf=\"tab == 'json'\"><pre>{{step | json}}</pre></div>\r\n        <div *ngIf=\"tab == 'savedValues' || tab == 'sql' || tab == 'all'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <hr/>\r\n              <label>Check saved values</label>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\" *ngFor=\"let checkedValueName of (step.savedValuesCheck ? Object.keys(step.savedValuesCheck) : [])\">\r\n            <div class=\"col-sm-4\" style=\"text-align: right;\">\r\n              <label>\r\n                <a href=\"#\" (click)=\"updateCheckedValueName(checkedValueName)\">{{checkedValueName}} <small><span class=\"glyphicon glyphicon-edit\"></span></small></a>\r\n              </label>\r\n            </div>\r\n            <div class=\"col-sm-8\">\r\n              <div class=\"input-group\">\r\n                <input placeholder=\"Checked value\" title=\"\" class=\"form-control\" [(ngModel)]=\"step.savedValuesCheck[checkedValueName]\"/>\r\n                <span class=\"input-group-btn\">\r\n                  <button class=\"btn btn-default\" (click)=\"removeCheckedValue(checkedValueName)\"><span class=\"glyphicon glyphicon-minus\"></span> Remove</button>\r\n                </span>\r\n              </div>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <button class=\"btn btn-default\" (click)=\"addCheckedValue()\"><span class=\"glyphicon glyphicon-plus\"></span> Add</button>\r\n            </div>\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/step-item/step-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_step__ = __webpack_require__("../../../../../src/app/model/step.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_mock_service_response__ = __webpack_require__("../../../../../src/app/model/mock-service-response.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__ = __webpack_require__("../../../../ng2-toasty/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__model_expected_service_request__ = __webpack_require__("../../../../../src/app/model/expected-service-request.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__model_form_data__ = __webpack_require__("../../../../../src/app/model/form-data.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepItemComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var StepItemComponent = (function () {
    function StepItemComponent(toastyService) {
        this.toastyService = toastyService;
        this.onDeleteClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onUpClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onDownClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onCloneClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.Object = Object;
        this.tab = 'summary';
        this.toastOptions = {
            title: 'Warning',
            msg: 'Checked variable already exists',
            showClose: true,
            timeout: 15000,
            theme: 'bootstrap'
        };
    }
    StepItemComponent.prototype.ngOnInit = function () {
    };
    StepItemComponent.prototype.selectTab = function (tabName) {
        this.tab = tabName;
        return false;
    };
    StepItemComponent.prototype.addMockServiceResponse = function () {
        if (!this.step.mockServiceResponseList) {
            this.step.mockServiceResponseList = [];
        }
        this.step.mockServiceResponseList.push(new __WEBPACK_IMPORTED_MODULE_2__model_mock_service_response__["a" /* MockServiceResponse */]());
    };
    StepItemComponent.prototype.removeMockServiceResponse = function (mockServiceResponse) {
        if (confirm('Confirm: remove mock response')) {
            var indexToRemove = this.step.mockServiceResponseList.indexOf(mockServiceResponse);
            if (indexToRemove > -1) {
                this.step.mockServiceResponseList.splice(indexToRemove, 1);
            }
        }
    };
    StepItemComponent.prototype.updateCheckedValueName = function (oldCheckedValueName) {
        var newName;
        if (newName = prompt('New parameter name', oldCheckedValueName)) {
            if (newName !== oldCheckedValueName) {
                if (!this.step.savedValuesCheck[newName]) {
                    Object.defineProperty(this.step.savedValuesCheck, newName, Object.getOwnPropertyDescriptor(this.step.savedValuesCheck, oldCheckedValueName));
                    delete this.step.savedValuesCheck[oldCheckedValueName];
                }
                else {
                    this.toastyService.warning(this.toastOptions);
                }
            }
        }
        return false;
    };
    StepItemComponent.prototype.addCheckedValue = function () {
        var newName;
        if (newName = prompt('New parameter name')) {
            if (!this.step.savedValuesCheck) {
                this.step.savedValuesCheck = {};
            }
            if (!this.step.savedValuesCheck[newName]) {
                this.step.savedValuesCheck[newName] = '';
            }
            else {
                this.toastyService.warning(this.toastOptions);
            }
        }
    };
    StepItemComponent.prototype.removeCheckedValue = function (checkedValueName) {
        delete this.step.savedValuesCheck[checkedValueName];
    };
    StepItemComponent.prototype.addExpectedServiceRequest = function () {
        if (!this.step.expectedServiceRequestList) {
            this.step.expectedServiceRequestList = [];
        }
        this.step.expectedServiceRequestList.push(new __WEBPACK_IMPORTED_MODULE_4__model_expected_service_request__["a" /* ExpectedServiceRequest */]());
    };
    StepItemComponent.prototype.removeExpectedServiceRequest = function (expectedServiceRequest) {
        if (confirm('Confirm: remove expected service request')) {
            var indexToRemove = this.step.expectedServiceRequestList.indexOf(expectedServiceRequest);
            if (indexToRemove > -1) {
                this.step.expectedServiceRequestList.splice(indexToRemove, 1);
            }
        }
    };
    StepItemComponent.prototype.deleteStep = function () {
        this.onDeleteClick.emit({ step: this.step });
    };
    StepItemComponent.prototype.disabledToggle = function () {
        this.step.disabled = !this.step.disabled;
    };
    StepItemComponent.prototype.upStep = function () {
        this.onUpClick.emit();
    };
    StepItemComponent.prototype.downStep = function () {
        this.onDownClick.emit();
    };
    StepItemComponent.prototype.cloneStep = function () {
        this.onCloneClick.emit();
    };
    StepItemComponent.prototype.removeFormData = function (formData) {
        var indexToRemove = this.step.formDataList.indexOf(formData);
        if (indexToRemove > -1) {
            this.step.formDataList.splice(indexToRemove, 1);
        }
    };
    StepItemComponent.prototype.addFormData = function () {
        this.step.formDataList.push(new __WEBPACK_IMPORTED_MODULE_5__model_form_data__["a" /* FormData */]());
    };
    return StepItemComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_step__["a" /* Step */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_step__["a" /* Step */]) === "function" && _a || Object)
], StepItemComponent.prototype, "step", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "showUpDownDeleteCloneButtons", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "onDeleteClick", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "onUpClick", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "onDownClick", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "onCloneClick", void 0);
StepItemComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-step-item',
        template: __webpack_require__("../../../../../src/app/step-item/step-item.component.html"),
        styles: [
            '.nav-tabs > li > a { padding-top: 3px; padding-bottom: 3px; }',
            '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
            '.row { margin-bottom: 5px; }',
            '.input-group-btn > select { padding: 0; width: 85px; border-top-left-radius: 5px; border-bottom-left-radius: 5px; border-right: 0; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */]) === "function" && _b || Object])
], StepItemComponent);

var _a, _b;
//# sourceMappingURL=step-item.component.js.map

/***/ }),

/***/ "../../../../../src/app/step-parameter-set/step-parameter-set.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"stepParameterSetList\">\n  <table class=\"table table-bordered\">\n    <thead>\n      <tr>\n        <th></th>\n        <th *ngFor=\"let parameterName of parameterNameList\">\n          <label><a href=\"#\" (click)=\"updateParameterName(parameterName)\">{{parameterName}} <small><span class=\"glyphicon glyphicon-edit\"></span></small></a></label>\n        </th>\n        <th>\n          <button class=\"btn btn-sm btn-default\" (click)=\"addParameterName()\"><span class=\"glyphicon glyphicon-plus\"></span></button>\n        </th>\n      </tr>\n    </thead>\n    <tbody>\n      <tr *ngFor=\"let stepParameterSet of stepParameterSetList\">\n        <th>\n          <input title=\"\" placeholder=\"Case description\" class=\"form-control no-border\" [(ngModel)]=\"stepParameterSet.description\" />\n        </th>\n        <td *ngFor=\"let parameterName of parameterNameList\">\n          <ng-container *ngIf=\"findParameter(stepParameterSet, parameterName)\">\n            <input title=\"\" class=\"form-control\" [(ngModel)]=\"findParameter(stepParameterSet, parameterName).value\"/>\n          </ng-container>\n        </td>\n        <td>\n          <button class=\"btn btn-sm btn-default\" (click)=\"removeParameterSet(stepParameterSet)\"><span class=\"glyphicon glyphicon-minus\"></span></button>\n        </td>\n      </tr>\n    </tbody>\n  </table>\n  <button class=\"btn btn-default\" (click)=\"addParameterSet()\"><span class=\"glyphicon glyphicon-plus\"></span> Add case</button>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/step-parameter-set/step-parameter-set.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_step_parameter_set__ = __webpack_require__("../../../../../src/app/model/step-parameter-set.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_step_parameter__ = __webpack_require__("../../../../../src/app/model/step-parameter.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__ = __webpack_require__("../../../../ng2-toasty/index.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepParameterSetComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var StepParameterSetComponent = (function () {
    function StepParameterSetComponent(toastyService) {
        this.toastyService = toastyService;
        this.toastOptions = {
            title: 'Warning',
            msg: 'Parameter name already exists',
            showClose: true,
            timeout: 15000,
            theme: 'bootstrap'
        };
    }
    StepParameterSetComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.parameterNameList = [];
        this.stepParameterSetList
            .forEach(function (parameterSet) { return parameterSet.stepParameterList
            .filter(function (parameter) { return !_this.parameterNameList.find(function (value) { return value === parameter.name; }) && parameter.name != null; })
            .forEach(function (parameter) { return _this.parameterNameList.push(parameter.name); }); });
        this.parameterNameList = this.parameterNameList.sort(function (a, b) { return a < b ? -1 : (a > b ? 1 : 0); });
    };
    StepParameterSetComponent.prototype.findParameter = function (stepParameterSet, parameterName) {
        if (!stepParameterSet.stepParameterList) {
            stepParameterSet.stepParameterList = [];
        }
        var filtered = stepParameterSet.stepParameterList.filter(function (parameter) { return parameter.name === parameterName; });
        return filtered.length > 0 ? filtered[0] : null;
    };
    StepParameterSetComponent.prototype.addParameterSet = function () {
        var newStepParameterSet = new __WEBPACK_IMPORTED_MODULE_1__model_step_parameter_set__["a" /* StepParameterSet */]();
        newStepParameterSet.stepParameterList = this.parameterNameList.map(function (parameterName) { return new __WEBPACK_IMPORTED_MODULE_2__model_step_parameter__["a" /* StepParameter */](parameterName); });
        this.stepParameterSetList.push(newStepParameterSet);
    };
    StepParameterSetComponent.prototype.addParameterName = function () {
        var newName;
        if (newName = prompt('New parameter name')) {
            if (this.isParameterNameExists(newName)) {
                this.toastyService.warning(this.toastOptions);
            }
            else {
                this.stepParameterSetList.forEach(function (value) { return value.stepParameterList.push(new __WEBPACK_IMPORTED_MODULE_2__model_step_parameter__["a" /* StepParameter */](newName)); });
                this.parameterNameList.push(newName);
                this.parameterNameList = this.parameterNameList.sort(function (a, b) { return a < b ? -1 : (a > b ? 1 : 0); });
            }
        }
    };
    StepParameterSetComponent.prototype.updateParameterName = function (oldParameterName) {
        var newName;
        if (newName = prompt('New parameter name', oldParameterName)) {
            if (this.isParameterNameExists(newName)) {
                this.toastyService.warning(this.toastOptions);
            }
            else {
                this.stepParameterSetList
                    .forEach(function (parameterSet) { return parameterSet.stepParameterList
                    .filter(function (parameter) { return parameter.name === oldParameterName; })
                    .forEach(function (value) { return value.name = newName; }); });
                this.parameterNameList = this.parameterNameList
                    .map(function (parameterName) { return parameterName === oldParameterName ? newName : parameterName; });
            }
        }
        return false;
    };
    StepParameterSetComponent.prototype.isParameterNameExists = function (newName) {
        return this.stepParameterSetList
            .find(function (parameterSet) { return parameterSet.stepParameterList
            .find(function (parameter) { return parameter.name === newName; }) != null; }) != null;
    };
    StepParameterSetComponent.prototype.removeParameterSet = function (stepParameterSet) {
        var indexToRemove = this.stepParameterSetList.indexOf(stepParameterSet);
        if (indexToRemove > -1) {
            this.stepParameterSetList.splice(indexToRemove, 1);
        }
    };
    return StepParameterSetComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Array)
], StepParameterSetComponent.prototype, "stepParameterSetList", void 0);
StepParameterSetComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-step-parameter-set',
        template: __webpack_require__("../../../../../src/app/step-parameter-set/step-parameter-set.component.html"),
        styles: [
            '.no-border { border: 0; box-shadow: none; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */]) === "function" && _a || Object])
], StepParameterSetComponent);

var _a;
//# sourceMappingURL=step-parameter-set.component.js.map

/***/ }),

/***/ "../../../../../src/app/step-result-item/step-result-item.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"form-group\" >\r\n  <div class=\"col-sm-1\">\r\n    <span class=\"glyphicon glyphicon-ok-circle\" style=\"color: green;\" *ngIf=\"stepResult.result == 'OK'\"></span>\r\n    <span class=\"glyphicon glyphicon-remove-circle\" style=\"color: red;\" *ngIf=\"stepResult.result != 'OK'\"></span>\r\n    {{stepResult.result}}\r\n  </div>\r\n  <div class=\"col-sm-11\">{{stepResult.step.stepComment}} {{stepResult.description}}</div>\r\n  <div class=\"clearfix\"></div>\r\n  <div style=\"color: gray;\" class=\"col-sm-11 col-sm-offset-1\">\r\n    {{stepResult.step.requestMethod}}\r\n    {{stepResult.requestUrl}}\r\n  </div>\r\n  <div class=\"clearfix\"></div>\r\n  <div class=\"col-sm-11 col-sm-offset-1\">\r\n    <ul class=\"nav nav-tabs\">\r\n      <li [class.active]=\"tab == 'summary'\"><a href=\"#\" (click)=\"selectTab('summary')\">Summary</a></li>\r\n      <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">Details</a></li>\r\n      <li [class.active]=\"tab == 'stepEdit'\"><a href=\"#\" (click)=\"selectTab('stepEdit')\">Edit step</a></li>\r\n      <li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">json</a></li>\r\n    </ul>\r\n    <div class=\"tab-content\" style=\"padding: 10px;\">\r\n      <div class=\"help-block\" *ngIf=\"tab == 'summary' || tab == 'all'\">\r\n        Test id: {{stepResult.testId}}\r\n      </div>\r\n      <div *ngIf=\"tab == 'details' || tab == 'all'\">\r\n        <div class=\"row\">\r\n          <div class=\"col-sm-12\" style=\"color: gray;\">\r\n            <div *ngIf=\"stepResult.pollingRetryCount > 1\">Polling retry count: {{stepResult.pollingRetryCount}}</div>\r\n            Saved parameters: {{stepResult.savedParameters}}\r\n          </div>\r\n          <div class=\"clearfix\"></div>\r\n          <div class=\"col-sm-12\">\r\n            <label>Request body</label>\r\n            <div class=\"form-control\" style=\"overflow: scroll; height: 180px; white-space: pre; background-color: #eee;\">{{stepResult.requestBody}}</div>\r\n          </div>\r\n          <div class=\"clearfix\"></div>\r\n          <div class=\"col-sm-6\">\r\n            <label>Actual</label>\r\n            <div class=\"form-control\" style=\"overflow: scroll; height: 180px; white-space: pre; background-color: #eee;\">{{stepResult.actual}}</div>\r\n          </div>\r\n          <div class=\"col-sm-6\">\r\n            <label>Expected</label>\r\n            <div class=\"form-control\" style=\"overflow: scroll; height: 180px; white-space: pre; background-color: #eee;\">{{stepResult.expected}}</div>\r\n          </div>\r\n          <div class=\"col-sm-12\" style=\"color: gray;\">\r\n            <label>Details</label>\r\n            <pre>{{stepResult.details}}</pre>\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <div *ngIf=\"tab == 'json' || tab == 'all'\">\r\n        <pre>{{stepResult | json}}</pre>\r\n      </div>\r\n      <div *ngIf=\"tab == 'stepEdit' || tab == 'all'\">\r\n        <app-step-item [step]=\"stepResult.step\"></app-step-item>\r\n        <button class=\"btn btn-xs btn-success\" (click)=\"saveStep()\">Save step</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/step-result-item/step-result-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_step_result__ = __webpack_require__("../../../../../src/app/model/step-result.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return StepResultItemComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var StepResultItemComponent = (function () {
    function StepResultItemComponent(route, stepService, customToastyService) {
        this.route = route;
        this.stepService = stepService;
        this.customToastyService = customToastyService;
        this.tab = 'summary';
    }
    StepResultItemComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            console.log(params);
            _this.projectCode = params['projectCode'];
            _this.scenarioGroup = params['scenarioGroup'];
            _this.scenarioCode = params['scenarioCode'];
        });
    };
    StepResultItemComponent.prototype.selectTab = function (tabName) {
        this.tab = tabName;
        return false;
    };
    StepResultItemComponent.prototype.saveStep = function () {
        var _this = this;
        var toasty = this.customToastyService.saving();
        this.stepService.saveStep(this.projectCode, this.scenarioGroup, this.scenarioCode, this.stepResult.step)
            .subscribe(function () {
            _this.customToastyService.success('Сохранено', 'Шаг сохранен');
        }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    return StepResultItemComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_step_result__["a" /* StepResult */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_step_result__["a" /* StepResult */]) === "function" && _a || Object)
], StepResultItemComponent.prototype, "stepResult", void 0);
StepResultItemComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-step-result-item',
        template: __webpack_require__("../../../../../src/app/step-result-item/step-result-item.component.html"),
        styles: [
            '.nav-tabs > li > a { padding-top: 3px; padding-bottom: 3px; }',
            '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
            '.row { margin-bottom: 5px; }',
            '.input-group-btn > select { padding: 0; width: 85px; border-top-left-radius: 5px; border-bottom-left-radius: 5px; border-right: 0; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_router__["b" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_2__service_step_service__["a" /* StepService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_step_service__["a" /* StepService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _d || Object])
], StepResultItemComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=step-result-item.component.js.map

/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
// The file contents for the current environment will overwrite these during build.
var environment = {
    production: false
};
//# sourceMappingURL=environment.js.map

/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/@angular/platform-browser-dynamic.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["a" /* enableProdMode */])();
}
__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 1:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[1]);
//# sourceMappingURL=main.bundle.js.map