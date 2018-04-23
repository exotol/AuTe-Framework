webpackJsonp([1],{

/***/ "../../../../../package.json":
/***/ (function(module, exports) {

module.exports = {"name":"autotester-ui","version":"2.0.0","license":"MIT","scripts":{"ng":"ng","start":"ng serve --host 0.0.0.0 --disable-host-check --proxy-config proxy.conf.json","build":"ng build --env=prod","test":"ng test","lint":"ng lint","e2e":"ng e2e"},"private":true,"dependencies":{"@angular/animations":"^4.0.0","@angular/common":"^4.0.0","@angular/compiler":"^4.0.0","@angular/core":"^4.0.0","@angular/forms":"^4.0.0","@angular/http":"^4.0.0","@angular/platform-browser":"^4.0.0","@angular/platform-browser-dynamic":"^4.0.0","@angular/router":"^4.0.0","@ngx-translate/core":"^8.0.0","@ngx-translate/http-loader":"^0.1.0","bootstrap":"^3.3.7","core-js":"^2.4.1","diff":"^3.4.0","file-saver":"^1.3.3","jquery":"^3.2.1","ng2-toasty":"^4.0.3","ngx-bootstrap":"^1.9.3","ngx-textarea-autosize":"^1.1.1","rxjs":"^5.1.0","zone.js":"^0.8.4"},"devDependencies":{"@angular/cli":"1.2.0","@angular/compiler-cli":"^4.0.0","@angular/language-service":"^4.0.0","@types/jasmine":"~2.5.53","@types/jasminewd2":"~2.0.2","@types/node":"~6.0.60","codelyzer":"~3.0.1","jasmine-core":"~2.6.2","jasmine-spec-reporter":"~4.1.0","karma":"~1.7.0","karma-chrome-launcher":"~2.1.1","karma-cli":"~1.0.1","karma-coverage-istanbul-reporter":"^1.2.1","karma-jasmine":"~1.1.0","karma-jasmine-html-reporter":"^0.2.2","protractor":"~5.1.2","ts-node":"~3.0.4","tslint":"~5.3.2","typescript":"~2.3.3"}}

/***/ }),

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

module.exports = "<nav class=\"navbar navbar-default\">\r\n  <div class=\"container-fluid\">\r\n    <div class=\"navbar-header\">\r\n      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\r\n        <span class=\"sr-only\">Toggle navigation</span>\r\n        <span class=\"icon-bar\"></span>\r\n        <span class=\"icon-bar\"></span>\r\n        <span class=\"icon-bar\"></span>\r\n      </button>\r\n      <a class=\"navbar-brand\" href=\"#\">BSC AutoTester</a>\r\n    </div>\r\n    <div class=\"navbar-locale\">\r\n      <select [(ngModel)]=\"locale\" (change)=\"changeLocale()\">\r\n        <option *ngFor=\"let lang of langs\" [value]=\"lang\">{{lang}}</option>\r\n      </select>\r\n    </div>\r\n    <div id=\"navbar\" class=\"navbar-collapse collapse\">\r\n      <ul class=\"nav navbar-nav\">\r\n        <li [routerLinkActive]=\"'active'\"><a [routerLink]=\"['/']\">{{'Projects' | translate}}</a></li>\r\n        <li [routerLinkActive]=\"'active'\"><a [routerLink]=\"['/help']\">{{'Help' | translate}}</a></li>\r\n      </ul>\r\n    </div>\r\n  </div>\r\n</nav>\r\n\r\n<router-outlet></router-outlet>\r\n<a data-toggle=\"collapse\" href=\"#versions\" aria-expanded=\"false\" aria-controls=\"collapseExample\">\r\n  {{'Versions' | translate}}\r\n</a>\r\n<div class=\"collapse \" id=\"versions\">\r\n  <div class=\"help-block\" *ngIf=\"managerVersion\">\r\n    <b>ui: </b>\r\n    {{uiVersion}}\r\n  </div>\r\n  <div class=\"help-block\" *ngIf=\"managerVersion\">\r\n    <b>manager: </b>\r\n    {{managerVersion.implementationVersion}} {{managerVersion.implementationDate}}\r\n  </div>\r\n  <div class=\"help-block\" *ngIf=\"executorVersion\">\r\n    <b>executor: </b>\r\n    {{executorVersion.implementationVersion}} {{executorVersion.implementationDate}}\r\n  </div>\r\n  <div class=\"help-block\" *ngFor=\"let version of projectsWiremockVersions\">\r\n    <b>{{version.projectCode}} wiremock: </b>\r\n    {{version.version.implementationVersion}} {{version.version.implementationDate}}\r\n  </div>\r\n</div>\r\n<ng2-toasty [position]=\"'top-right'\"></ng2-toasty>\r\n"

/***/ }),

/***/ "../../../../../src/app/app.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".navbar {\n  margin-bottom: 0; }\n\n.navbar-locale {\n  float: right;\n  margin-top: 15px; }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_version_service__ = __webpack_require__("../../../../../src/app/service/version.service.ts");
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



var LANG_TOKEN = 'bsc_autotester_language';
var AppComponent = (function () {
    function AppComponent(versionService, translate) {
        this.versionService = versionService;
        this.translate = translate;
        this.langs = ['en', 'ru'];
        this.uiVersion = __webpack_require__("../../../../../package.json").version;
        translate.addLangs(this.langs);
        this.locale = localStorage.getItem(LANG_TOKEN) || 'en';
        translate.setDefaultLang(this.locale);
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.versionService.getManagerVersion().subscribe(function (version) { return _this.managerVersion = version; });
        this.versionService.getExecutorVersion().subscribe(function (version) { return _this.executorVersion = version; });
        this.versionService.getProjectsWiremockVersions().subscribe(function (version) { return _this.projectsWiremockVersions = version; });
    };
    AppComponent.prototype.changeLocale = function () {
        this.translate.use(this.locale);
        this.saveCurrentLangAsDefault();
    };
    AppComponent.prototype.saveCurrentLangAsDefault = function () {
        localStorage.setItem(LANG_TOKEN, this.locale);
    };
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-root',
        template: __webpack_require__("../../../../../src/app/app.component.html"),
        styles: [__webpack_require__("../../../../../src/app/app.component.scss")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__service_version_service__["a" /* VersionService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_version_service__["a" /* VersionService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__ngx_translate_core__["c" /* TranslateService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__ngx_translate_core__["c" /* TranslateService */]) === "function" && _b || Object])
], AppComponent);

var _a, _b;
//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ngx_translate_http_loader__ = __webpack_require__("../../../../@ngx-translate/http-loader/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ng2_toasty__ = __webpack_require__("../../../../ng2-toasty/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__project_list_project_list_component__ = __webpack_require__("../../../../../src/app/project-list/project-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__project_detail_project_detail_component__ = __webpack_require__("../../../../../src/app/project-detail/project-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__scenario_detail_scenario_detail_component__ = __webpack_require__("../../../../../src/app/scenario-detail/scenario-detail.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__shared_diff_diff_component__ = __webpack_require__("../../../../../src/app/shared/diff/diff.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__scenario_list_item_scenario_list_item_component__ = __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__step_result_item_step_result_item_component__ = __webpack_require__("../../../../../src/app/step-result-item/step-result-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__step_item_step_item_component__ = __webpack_require__("../../../../../src/app/step-item/step-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__mock_service_response_mock_service_response_component__ = __webpack_require__("../../../../../src/app/mock-service-response/mock-service-response.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__step_parameter_set_step_parameter_set_component__ = __webpack_require__("../../../../../src/app/step-parameter-set/step-parameter-set.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__project_settings_project_settings_component__ = __webpack_require__("../../../../../src/app/project-settings/project-settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_23__scenario_settings_scenario_settings_component__ = __webpack_require__("../../../../../src/app/scenario-settings/scenario-settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_24__service_version_service__ = __webpack_require__("../../../../../src/app/service/version.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_25__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_26__search_scenario_search_scenario_component__ = __webpack_require__("../../../../../src/app/search-scenario/search-scenario.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_27__service_search_scenario_service__ = __webpack_require__("../../../../../src/app/service/search-scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_28__shared_directives_sync_scroll_directive__ = __webpack_require__("../../../../../src/app/shared/directives/sync-scroll.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_29__shared_directives_text_select_directive__ = __webpack_require__("../../../../../src/app/shared/directives/text-select.directive.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_30__help_help_component__ = __webpack_require__("../../../../../src/app/help/help.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_31__mq_mock_response_mq_mock_response_component__ = __webpack_require__("../../../../../src/app/mq-mock-response/mq-mock-response.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_32_ngx_textarea_autosize__ = __webpack_require__("../../../../ngx-textarea-autosize/index.ts");
/* unused harmony export HttpLoaderFactory */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

































function HttpLoaderFactory(http) {
    return new __WEBPACK_IMPORTED_MODULE_3__ngx_translate_http_loader__["a" /* TranslateHttpLoader */](http);
}
var routes = [
    { path: '', component: __WEBPACK_IMPORTED_MODULE_7__project_list_project_list_component__["a" /* ProjectListComponent */] },
    { path: 'help', component: __WEBPACK_IMPORTED_MODULE_30__help_help_component__["a" /* HelpComponent */] },
    { path: 'project/:projectCode', component: __WEBPACK_IMPORTED_MODULE_8__project_detail_project_detail_component__["a" /* ProjectDetailComponent */] },
    { path: 'project/:projectCode/settings', component: __WEBPACK_IMPORTED_MODULE_22__project_settings_project_settings_component__["a" /* ProjectSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioCode/settings', component: __WEBPACK_IMPORTED_MODULE_23__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioGroup/:scenarioCode/settings', component: __WEBPACK_IMPORTED_MODULE_23__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioCode', component: __WEBPACK_IMPORTED_MODULE_9__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */] },
    { path: 'project/:projectCode/scenario/:scenarioGroup/:scenarioCode', component: __WEBPACK_IMPORTED_MODULE_9__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */] },
];
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["b" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_7__project_list_project_list_component__["a" /* ProjectListComponent */],
            __WEBPACK_IMPORTED_MODULE_8__project_detail_project_detail_component__["a" /* ProjectDetailComponent */],
            __WEBPACK_IMPORTED_MODULE_9__scenario_detail_scenario_detail_component__["a" /* ScenarioDetailComponent */],
            __WEBPACK_IMPORTED_MODULE_13__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */],
            __WEBPACK_IMPORTED_MODULE_16__step_result_item_step_result_item_component__["a" /* StepResultItemComponent */],
            __WEBPACK_IMPORTED_MODULE_17__step_item_step_item_component__["a" /* StepItemComponent */],
            __WEBPACK_IMPORTED_MODULE_19__mock_service_response_mock_service_response_component__["a" /* MockServiceResponseComponent */],
            __WEBPACK_IMPORTED_MODULE_20__step_parameter_set_step_parameter_set_component__["a" /* StepParameterSetComponent */],
            __WEBPACK_IMPORTED_MODULE_22__project_settings_project_settings_component__["a" /* ProjectSettingsComponent */],
            __WEBPACK_IMPORTED_MODULE_23__scenario_settings_scenario_settings_component__["a" /* ScenarioSettingsComponent */],
            __WEBPACK_IMPORTED_MODULE_10__shared_diff_diff_component__["a" /* DiffComponent */],
            __WEBPACK_IMPORTED_MODULE_28__shared_directives_sync_scroll_directive__["a" /* SyncScrollDirective */],
            __WEBPACK_IMPORTED_MODULE_26__search_scenario_search_scenario_component__["a" /* SearchComponent */],
            __WEBPACK_IMPORTED_MODULE_29__shared_directives_text_select_directive__["a" /* TextSelectDirective */],
            __WEBPACK_IMPORTED_MODULE_30__help_help_component__["a" /* HelpComponent */],
            __WEBPACK_IMPORTED_MODULE_31__mq_mock_response_mq_mock_response_component__["a" /* MqMockResponseComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_12__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_6__angular_router__["a" /* RouterModule */].forRoot(routes, { useHash: true }),
            __WEBPACK_IMPORTED_MODULE_5_ng2_toasty__["a" /* ToastyModule */].forRoot(),
            __WEBPACK_IMPORTED_MODULE_2__ngx_translate_core__["a" /* TranslateModule */].forRoot({
                loader: {
                    provide: __WEBPACK_IMPORTED_MODULE_2__ngx_translate_core__["b" /* TranslateLoader */],
                    useFactory: HttpLoaderFactory,
                    deps: [__WEBPACK_IMPORTED_MODULE_12__angular_http__["b" /* Http */]]
                }
            }),
            __WEBPACK_IMPORTED_MODULE_18__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_18__angular_forms__["b" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_32_ngx_textarea_autosize__["a" /* TextareaAutosizeModule */]
        ],
        providers: [__WEBPACK_IMPORTED_MODULE_11__service_project_service__["a" /* ProjectService */], __WEBPACK_IMPORTED_MODULE_14__service_scenario_service__["a" /* ScenarioService */], __WEBPACK_IMPORTED_MODULE_15__service_step_service__["a" /* StepService */], __WEBPACK_IMPORTED_MODULE_25__service_custom_toasty_service__["a" /* CustomToastyService */], __WEBPACK_IMPORTED_MODULE_24__service_version_service__["a" /* VersionService */], __WEBPACK_IMPORTED_MODULE_21__globals__["a" /* Globals */], __WEBPACK_IMPORTED_MODULE_27__service_search_scenario_service__["a" /* SearchScenarioService */]],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
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

/***/ "../../../../../src/app/help/help.component.html":
/***/ (function(module, exports) {

module.exports = "<pre style=\"word-wrap: normal; word-break: normal; white-space: pre-wrap;\">\r\nИнструкция по интерфейсу Автотестера:\r\n  <h4>1. Список проектов</h4>\r\n    Настройки проекта\r\n      Шаги \"перед\" и \"после\" сценариев.\r\n        В случаях, когда перед и после сценариев необходимо всегда выполнять одни и те же шаги, например, авторизация и логаут,\r\n        их можно вынести в отдельные сценарии, затем в настройках проекта указать их для выполнения перед и после каждого сценария.\r\n      Использование http-заголовока testId\r\n        Сервис заглушек должен иметь возможность отличать запросы, направляемые в рамках разных шагов, для их проверки и отправки заданных в шаге ответов.\r\n        Для этого к каждому запросу от Автотестера к тестируемому порталу добавляется http-заголовок со случайно генерируемым уникальным ID.\r\n        Портал должен пересылать указанный ID в сервис заглушек.\r\n        Название http-заголовка указывается в поле \"TestId header name\".\r\n      Стенд\r\n        На вкладке \"Стенд\" отображаются параметры стенда, заданные в env.yml.\r\n        Редактирование на форме недоступно. Изменить параметры можно только непосредственно в файле env.yml.\r\n      Группы\r\n        На вкладке \"Группы\" доступно переименование и создание новых групп.\r\n  <h4>2. Список сценариев в проекте</h4>\r\n    Настройки сценария:\r\n      В настройках сценария можно изменить его заголовок.\r\n      В случае, если необходимо отключить выполнение сценариев \"перед\" и \"после\", указанные в настройках проекта, необходимо отметить соответствующие чекбоксы.\r\n    Группы\r\n      Группы сценариев соответствуют директориям, вложенным в /&lt;project_dir>/scenarios/\r\n      Поддерживается один уровень вложенности.\r\n    Создание сценария\r\n      Для создания нового сценария необходимо указать его название и нажать кнопку \"Создать\". Сценарий будет создан в текущей выбранной группе.\r\n    Выполнение сценариев:\r\n      Для выполнения сценария необходимо нажать \"Выполнить\".\r\n      После выполнения можно просмотреть результат. В результатах отображаются статусы выполнения каждого шага с возможность просмотра тела запроса, фактического и ожидаемого результатов с выделением различающихся строк и детали с описанием возникшей ошибки.\r\n  <h4>3. Список шагов в сценарии</h4>\r\n    Один шаг сценария содержит следующий набор возможностей:\r\n    <h5>3.1 URL, http-headers</h5>\r\n      В поле адреса указывается относительный адрес возможность подстановки в него сохраненных значений, пример: /rest/items/&#123;itemId\t&#125;\r\n    <h5>3.2 Запрос, ответ и режим сравнения</h5>\r\n      Доступны два типа тела запроса:\r\n      - raw\r\n        Отправляется текстовое тело запроса\r\n      - form-data\r\n        Отправляется форма с текстовыми полями или с файлами. В случае отправки файла указывается путь к нему относительно директории с проектом.\r\n        Пример: файл расположен в директории /projects/&lt; project_dir >/files/img/photo.jpg, в форме необходимо указать: files/img/photo.jpg\r\n\r\n      Режим сравнения ответа с ожидаемым результатом:\r\n      - JSON (по умолчанию)\r\n        Сравнение двух JSON-объектов. Для гибкой настройки используются различные режимы:\r\n        - NON_EXTENSIBLE (По умолчанию. Не расширяемый, нестрогий порядок элементов в массивах)\r\n        - STRICT (Не расширяемый, строгий порядок элементов в массивах)\r\n        - LENIENT (Расширяемый, нестрогий порядок элементов в массивах)\r\n        - STRICT_ORDER (Расширяемый, строгий порядок элементов в массивах)\r\n      - Full match\r\n        Полное соответствие ответа ожидаемому результату\r\n      - Mask *ignore*\r\n        Сравнение как строк с возможностью игнорирования части строки.\r\n        Игнорируемая часть строки указывается ключевым словом *ignore*.\r\n        Пример:\r\n          Ожидаемый результат: &lt;xml>&lt;datetime>*ignore*&lt;/datetime>&lt;name>Item name&lt;/name>&lt;/xml>\r\n          Фактический результат: &lt;xml>&lt;datetime>2018-01-22 17:50:24&lt;/datetime>&lt;name>Item name&lt;/name>&lt;/xml>\r\n    <h5>3.3 Сохраняемые значения</h5>\r\n      После получения ответа поля из JSON можно сохранить для использования в последующих шагах, а также проверить их значение.\r\n      Обращение к полям происходит с помощью JSON XPath. Пример:\r\n      parameterName = $.element.items[2].title\r\n    <h5>3.4 SQL</h5>\r\n      Отправка SQL-запроса к базе данных, полученные поля соответственно записываются в сохраняемые значения.\r\n      В текст SQL-запроса можно вставлять сохраненные значения.\r\n      Пример:\r\n        Сохраняемые значения: tradeId, tradeSum\r\n        Запрос SQL: SELECT ID, CNT * PRICE FROM TRADES WHERE USER_ID = %userId% ORDER BY ID DESC\r\n      Полученные поля будут записаны в соответствующие сохраняемые значения.\r\n      Работает, если в env.yml указаны настройки подключения к базе данных тестируемого портала. Параметр \"dataBase\".\r\n      Запросы, изменяющие записи в базе данных, запрещены.\r\n    <h5>3.5 Моки</h5>\r\n      - Проверка запросов, отправляемых поратлом в заглушки.\r\n      - Указание ответов, которые заглушка возвращает на запросы портала.\r\n      Работает, если:\r\n      - В настройках проекта включен параметр \"Использовать случайный testId\" и указано название http-заголовка;\r\n      - Тестируемый портал \"пробрасывает\" http-заголовок в заглушки.\r\n    <h5>3.6 Очереди сообщений</h5>\r\n      Отправляется сообщение в очередь с указанными названиям и телом сообщения.\r\n      Работает, если в env.yml указаны настройки подключения к серверу: параметр \"amqpBroker\".\r\n    <h5>3.7 Поллинг</h5>\r\n      Выполнение запроса многократно до тех пор, пока указанный JSON-параметр не будет найден.\r\n      Запросы повторяются с периодом: 1 секунда, максимум: 50 раз.\r\n      Для указания искомого параметра используется JSON XPath. Пример: $.body.items\r\n    <h5>3.8 Тест-кейсы</h5>\r\n      Позволяет выполнять шаг многократно с различнымы наборами сохраненных значений. В колонках указываются названия переменных, в строках - значения.\r\n</pre>\r\n"

/***/ }),

/***/ "../../../../../src/app/help/help.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HelpComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var HelpComponent = (function () {
    function HelpComponent() {
    }
    HelpComponent.prototype.ngOnInit = function () {
    };
    return HelpComponent;
}());
HelpComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-help',
        template: __webpack_require__("../../../../../src/app/help/help.component.html")
    }),
    __metadata("design:paramtypes", [])
], HelpComponent);

//# sourceMappingURL=help.component.js.map

/***/ }),

/***/ "../../../../../src/app/mock-service-response/mock-service-response.component.html":
/***/ (function(module, exports) {

module.exports = "<div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-6\">\r\n      <input class=\"form-control\" placeholder=\"{{'Service URL' | translate}}\" title=\"\" [(ngModel)]=\"mockServiceResponse.serviceUrl\"/>\r\n    </div>\r\n    <div class=\"col-sm-3\">\r\n      <input class=\"form-control\" placeholder=\"HTTP-status: 200, 404, 500, [empty]\" title=\"\" [(ngModel)]=\"mockServiceResponse.httpStatus\"/>\r\n    </div>\r\n    <div class=\"col-sm-3\">\r\n      <input class=\"form-control\" placeholder=\"Content-Type: application/json, text/xml\" title=\"\" [(ngModel)]=\"mockServiceResponse.contentType\"/>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n      <div class=\"col-sm-3\" style=\"margin-top: 10px\">\r\n        <input class=\"form-control\" placeholder='Basic Authentication(username), [empty]' title=\"\" [(ngModel)]=\"mockServiceResponse.userName\"/>\r\n      </div>\r\n      <div class=\"col-sm-3\" style=\"margin-top: 10px\">\r\n        <input class=\"form-control\" placeholder='Basic Authentication(password), [empty]' title=\"\" [(ngModel)]=\"mockServiceResponse.password\"/>\r\n      </div>\r\n      <div class=\"col-sm-3\" style=\"margin-top: 10px\">\r\n        <input class=\"form-control\" placeholder=\"{{'XPath filter' | translate}}\" title=\"\" [(ngModel)]=\"mockServiceResponse.pathFilter\"/>\r\n      </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <label>{{'Response body' | translate}}</label>\r\n      <textarea class=\"form-control\" placeholder=\"{{'Response body' | translate}}\" title=\"\" rows=\"7\" [(ngModel)]=\"mockServiceResponse.responseBody\"></textarea>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

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

/***/ "../../../../../src/app/model/expected-mq-request.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ExpectedMqRequest; });
var ExpectedMqRequest = (function () {
    function ExpectedMqRequest() {
    }
    return ExpectedMqRequest;
}());

//# sourceMappingURL=expected-mq-request.js.map

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

/***/ "../../../../../src/app/model/mq-mock-response.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MqMockResponse; });
var MqMockResponse = (function () {
    function MqMockResponse() {
    }
    return MqMockResponse;
}());

//# sourceMappingURL=mq-mock-response.js.map

/***/ }),

/***/ "../../../../../src/app/model/name-value-property.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return NameValueProperty; });
var NameValueProperty = (function () {
    function NameValueProperty() {
    }
    return NameValueProperty;
}());

//# sourceMappingURL=name-value-property.js.map

/***/ }),

/***/ "../../../../../src/app/model/scenario-identity.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioIdentity; });
var ScenarioIdentity = (function () {
    function ScenarioIdentity(projectCode, group, code) {
        this.projectCode = projectCode;
        this.group = group;
        this.code = code;
    }
    return ScenarioIdentity;
}());

//# sourceMappingURL=scenario-identity.js.map

/***/ }),

/***/ "../../../../../src/app/model/scenario-variable-from-mq-request.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ScenarioVariableFromMqRequest; });
var ScenarioVariableFromMqRequest = (function () {
    function ScenarioVariableFromMqRequest() {
    }
    return ScenarioVariableFromMqRequest;
}());

//# sourceMappingURL=scenario-variable-from-mq-request.js.map

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

/***/ "../../../../../src/app/model/sql-data.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SqlData; });
var SqlData = (function () {
    function SqlData() {
    }
    return SqlData;
}());

//# sourceMappingURL=sql-data.js.map

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
        this.requestBodyType = 'JSON';
        this.savedValuesCheck = {};
        this.stepParameterSetList = [];
        this.responseCompareMode = 'JSON';
        this.formDataList = [];
        this.jsonCompareMode = 'NON_EXTENSIBLE';
        this.stepMode = 'REST';
    }
    return Step;
}());

//# sourceMappingURL=step.js.map

/***/ }),

/***/ "../../../../../src/app/mq-mock-response/mq-mock-response.component.html":
/***/ (function(module, exports) {

module.exports = "<div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-4\">\r\n      <input class=\"form-control\" placeholder=\"{{'Source queue name' | translate}}\" title=\"\" [(ngModel)]=\"mqMockResponse.sourceQueueName\"/>\r\n    </div>\r\n    <div class=\"col-sm-4\">\r\n      <input class=\"form-control\" placeholder=\"{{'Destination queue name' | translate}}\" title=\"\" [(ngModel)]=\"mqMockResponse.destinationQueueName\"/>\r\n    </div>\r\n    <div class=\"col-sm-4\">\r\n      <input class=\"form-control\" placeholder=\"{{'XPath message filter' | translate}}\" title=\"\" [(ngModel)]=\"mqMockResponse.xpath\"/>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <label>{{'Response body' | translate}}</label>\r\n      <textarea class=\"form-control\" placeholder=\"{{'Response body' | translate}}\" title=\"\" rows=\"7\" [(ngModel)]=\"mqMockResponse.responseBody\"></textarea>\r\n    </div>\r\n    <div class=\"col-sm-12\">\r\n      <label>{{'Http URL' | translate}}</label>\r\n      <input class=\"form-control\" placeholder=\"{{'Http url' | translate}}\" title=\"\" [(ngModel)]=\"mqMockResponse.httpUrl\"/>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/mq-mock-response/mq-mock-response.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_mq_mock_response__ = __webpack_require__("../../../../../src/app/model/mq-mock-response.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MqMockResponseComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MqMockResponseComponent = (function () {
    function MqMockResponseComponent() {
    }
    MqMockResponseComponent.prototype.ngOnInit = function () {
    };
    return MqMockResponseComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_mq_mock_response__["a" /* MqMockResponse */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_mq_mock_response__["a" /* MqMockResponse */]) === "function" && _a || Object)
], MqMockResponseComponent.prototype, "mqMockResponse", void 0);
MqMockResponseComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-mq-mock-response',
        template: __webpack_require__("../../../../../src/app/mq-mock-response/mq-mock-response.component.html")
    }),
    __metadata("design:paramtypes", [])
], MqMockResponseComponent);

var _a;
//# sourceMappingURL=mq-mock-response.component.js.map

/***/ }),

/***/ "../../../../../src/app/project-detail/project-detail.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "input.select-all { width: 24px; height: 24px; margin: 0; vertical-align: middle; }\r\n\r\n.breadcrumb-search {\r\n  position: relative;\r\n}\r\n.breadcrumb {\r\n  padding-right: 342px;\r\n}\r\n\r\napp-search {\r\n  position: absolute;\r\n\r\n  right: 40px;\r\n  top: 3px;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/project-detail/project-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<ng-container *ngIf=\"project\">\r\n  <div class=\"breadcrumb-search\">\r\n    <ol class=\"breadcrumb\">\r\n      <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">{{'Projects' | translate}}</a></li>\r\n      <li class=\"breadcrumb-item active\">{{project.code}}. {{project.name}}</li>\r\n    </ol>\r\n    <app-search [projectCode]=\"project.code\"></app-search>\r\n  </div>\r\n\r\n  <h4>{{project.name}}</h4>\r\n  <a [routerLink]=\"['/project/' + project.code + '/settings']\">{{'Settings' | translate}}</a>\r\n\r\n  <nav aria-label=\"Scenario groups\">\r\n    <ul class=\"pagination\">\r\n      <li [class.active]=\"!filter\">\r\n        <a href=\"#\" (click)=\"selectAllGroups()\">{{'All' | translate}}</a>\r\n      </li>\r\n      <li [class.active]=\"filter && !filter.scenarioGroup\">\r\n        <a href=\"#\" (click)=\"selectGroup()\">{{'Without group' | translate}}</a>\r\n      </li>\r\n      <li [class.active]=\"filter && group == filter.scenarioGroup\" *ngFor=\"let group of project.groupList\">\r\n        <a href=\"#\" (click)=\"selectGroup(group)\">{{group}}</a>\r\n      </li>\r\n    </ul>\r\n  </nav>\r\n\r\n  <div class=\"container-fluid\" style=\"background-color: #f5f5f5; padding-top: 10px;\">\r\n    <div class=\"row\">\r\n      <div style=\"height: 40px;\" class=\"col-sm-7\">\r\n        <label>\r\n          <input class=\"select-all\" type=\"checkbox\" title=\"{{'Select' | translate}}\" [checked]=\"selectAllFlag\" (click)=\"selectAll()\" />\r\n        </label>\r\n        <button class=\"btn btn-sm btn-default\" (click)=\"executeSelectedScenarios()\">{{'Execute selected scenarios' | translate}}</button>\r\n        <button class=\"btn btn-sm btn-default\" (click)=\"getReportsBySelectedScenarios()\">{{'Get report on selected scenarios' | translate}}</button>\r\n      </div>\r\n      <div class=\"col-sm-1\" style=\"font-weight: bold;\" [style.color]=\"failCount > 0 ? 'red' : ''\">\r\n        {{failCount}}\r\n      </div>\r\n      <div class=\"col-sm-4\">\r\n        <div class=\"progress\" style=\"background-color: #fff;\">\r\n          <div class=\"progress-bar progress-bar-striped\"\r\n               role=\"progressbar\"\r\n               [class.active]=\"executingStateExecuted != executingStateTotal\"\r\n               [style.display]=\"executingStateTotal > 0 ? '' : 'none'\"\r\n               [style.width]=\"(5 + executingStateExecuted / executingStateTotal * 95) + '%'\">\r\n            {{Math.round(executingStateExecuted / executingStateTotal * 100)}}%\r\n            {{executingStateExecuted > 0 ? '(' + executingStateExecuted + '/' + executingStateTotal + ')' : ''}}\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div style=\"min-height: 33px;\" [style.display]=\"isDisplayScenario(scenario) ? '' : 'none'\" *ngFor=\"let scenario of scenarioList\">\r\n    <app-scenario-list-item [scenario]=\"scenario\" [projectCode]=\"project.code\" (onStateChange)=\"onStateChange($event, scenario)\" (cbStateChange)=\"onCbStateChange($event, scenario)\" [isLinkTitleScenario] = \"true\"></app-scenario-list-item>\r\n  </div>\r\n\r\n  <div class=\"container\">\r\n    <div class=\"panel panel-default\">\r\n      <div class=\"panel-body\">\r\n        <label>{{'Create new scenario' | translate}}</label>\r\n        <div class=\"input-group\">\r\n          <input placeholder=\"{{'Scenario name' | translate}}\" class=\"form-control\" title=\"{{'Scenario name' | translate}}\" [(ngModel)]=\"newScenarioName\" />\r\n          <span class=\"input-group-btn\">\r\n            <button class=\"btn btn-success\" (click)=\"saveNewScenario()\"><span class=\"glyphicon glyphicon-plus\"></span> {{ 'Create' | translate}}</button>\r\n          </span>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n\r\n</ng-container>\r\n<div class=\"help-block\" *ngIf=\"!project\">\r\n  <span class=\"glyphicon glyphicon-time\"></span>\r\n  {{'Loading' | translate}}\r\n</div>\r\n"

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__scenario_list_item_scenario_list_item_component__ = __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_file_saver_FileSaver__ = __webpack_require__("../../../../file-saver/FileSaver.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_file_saver_FileSaver___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_file_saver_FileSaver__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__model_scenario_identity__ = __webpack_require__("../../../../../src/app/model/scenario-identity.ts");
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
    function ProjectDetailComponent(route, router, projectService, customToastyService, scenarioService, translate) {
        this.route = route;
        this.router = router;
        this.projectService = projectService;
        this.customToastyService = customToastyService;
        this.scenarioService = scenarioService;
        this.translate = translate;
        this.selectAllFlag = false;
        this.failCount = 0;
        this.newScenarioName = '';
        this.scenarioGroupList = [];
        this.executingStateExecuted = 0;
        this.executingStateTotal = 0;
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
    ProjectDetailComponent.prototype.selectGroup = function (group) {
        this.filter = new __WEBPACK_IMPORTED_MODULE_4__model_scenario__["a" /* Scenario */]();
        this.filter.scenarioGroup = group;
        this.router.navigate([], { queryParams: { scenarioGroup: group ? group : '' } });
        this.updateFailCountSum();
        this.updateSelectAllFlag();
        return false;
    };
    ProjectDetailComponent.prototype.selectAllGroups = function () {
        this.filter = null;
        this.router.navigate([]);
        this.updateFailCountSum();
        this.updateSelectAllFlag();
        return false;
    };
    ProjectDetailComponent.prototype.updateSelectAllFlag = function () {
        var _this = this;
        if (this.scenarioList) {
            this.selectAllFlag = this.scenarioList
                .filter(function (s) { return !_this.filter || s.scenarioGroup == _this.filter.scenarioGroup; })
                .filter(function (s) { return !s._selected; }).length === 0;
        }
        else {
            this.selectAllFlag = false;
        }
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
        this.scenarioComponentList
            .filter(function (item) { return _this.isDisplayScenario(item.scenario); })
            .forEach(function (item) { return item.scenario._selected = _this.selectAllFlag; });
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
        this.executingStateExecuted = this.scenarioComponentList
            .filter(function (item) { return item.state === 'executing' || item.state === 'finished' || item.state === 'starting'; })
            .filter(function (item) { return item.executedSteps; })
            .map(function (item) { return item.executedSteps - (item.state === 'finished' ? 0 : 1); })
            .reduce(function (a, b) { return a + b; }, 0);
        this.executingStateTotal = this.scenarioComponentList
            .filter(function (item) { return item.state === 'executing' || item.state === 'finished' || item.state === 'starting'; })
            .filter(function (item) { return item.totalSteps; })
            .map(function (item) { return item.totalSteps; })
            .reduce(function (a, b) { return a + b; }, 0);
    };
    // noinspection JSUnusedLocalSymbols
    ProjectDetailComponent.prototype.onStateChange = function (event, scenario) {
        this.updateExecutionStatus();
    };
    // noinspection JSUnusedLocalSymbols
    ProjectDetailComponent.prototype.onCbStateChange = function (event, scenario) {
        var _this = this;
        this.selectAllFlag = !scenario._selected && this.scenarioList
            .filter(function (s) { return !_this.filter || s.scenarioGroup == _this.filter.scenarioGroup; })
            .filter(function (s) { return s !== scenario && !s._selected; }).length === 0;
    };
    ProjectDetailComponent.prototype.saveNewScenario = function () {
        var _this = this;
        var newScenario = new __WEBPACK_IMPORTED_MODULE_4__model_scenario__["a" /* Scenario */]();
        newScenario.name = this.newScenarioName;
        if (this.filter && this.filter.scenarioGroup) {
            newScenario.scenarioGroup = this.filter.scenarioGroup;
        }
        var toasty = this.customToastyService.saving('Сохранение сценария...', 'Сохранение может занять некоторое время...');
        this.projectService.createScenario(this.project, newScenario)
            .subscribe(function (savedScenario) {
            _this.scenarioList.push(savedScenario);
            _this.newScenarioName = '';
            _this.customToastyService.success('Сохранено', 'Сценарий создан');
        }, function (error) { return _this.handleError(error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    ProjectDetailComponent.prototype.handleError = function (error) {
        var _this = this;
        var message = JSON.parse(error._body).message;
        console.log(message);
        this.translate.get(message).subscribe(function (value) {
            _this.customToastyService.error('Ошибка', value);
        });
    };
    ProjectDetailComponent.prototype.getReportsBySelectedScenarios = function () {
        var _this = this;
        var scenarioIdentities = [];
        this.scenarioComponentList
            .filter(function (item) { return item.scenario._selected && _this.isDisplayScenario(item.scenario) && item.scenario.hasResults; })
            .forEach(function (item) {
            var identity = new __WEBPACK_IMPORTED_MODULE_10__model_scenario_identity__["a" /* ScenarioIdentity */](item.projectCode, item.scenario.scenarioGroup, item.scenario.code);
            scenarioIdentities.push(identity);
        });
        this.scenarioService
            .downloadReport(scenarioIdentities)
            .subscribe(function (blobReport) {
            __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_6_file_saver_FileSaver__["saveAs"])(blobReport, 'reports.zip');
        });
    };
    return ProjectDetailComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_16" /* ViewChildren */])(__WEBPACK_IMPORTED_MODULE_5__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */]),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* QueryList */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* QueryList */]) === "function" && _a || Object)
], ProjectDetailComponent.prototype, "scenarioComponentList", void 0);
ProjectDetailComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-project-detail',
        template: __webpack_require__("../../../../../src/app/project-detail/project-detail.component.html"),
        styles: [__webpack_require__("../../../../../src/app/project-detail/project-detail.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_8__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8__service_scenario_service__["a" /* ScenarioService */]) === "function" && _f || Object, typeof (_g = typeof __WEBPACK_IMPORTED_MODULE_9__ngx_translate_core__["c" /* TranslateService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_9__ngx_translate_core__["c" /* TranslateService */]) === "function" && _g || Object])
], ProjectDetailComponent);

var _a, _b, _c, _d, _e, _f, _g;
//# sourceMappingURL=project-detail.component.js.map

/***/ }),

/***/ "../../../../../src/app/project-list/project-list.component.html":
/***/ (function(module, exports) {

module.exports = "<h4>{{'Projects' | translate}}</h4>\r\n<div class=\"help-block\" *ngIf=\"!projectList\">\r\n  <span class=\"glyphicon glyphicon-time\"></span>\r\n  {{'Loading' | translate}}\r\n</div>\r\n<table class=\"table table-condensed\" *ngIf=\"projectList\">\r\n  <tbody>\r\n    <tr *ngFor=\"let project of projectList\">\r\n      <td>\r\n        <a [routerLink]=\"['/project', project.code]\">{{project.name}}</a>\r\n      </td>\r\n      <td>\r\n        <ng-container *ngIf=\"project.stand\">\r\n          {{project.stand.serviceUrl}}\r\n        </ng-container>\r\n      </td>\r\n    </tr>\r\n  </tbody>\r\n</table>\r\n\r\n"

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
        this.displayImportProjectForm = false;
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

module.exports = "<ol class=\"breadcrumb\">\r\n  <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">{{'Projects' | translate}}</a></li>\r\n  <li *ngIf=\"project\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project', project.code]\">{{project.code}}. {{project.name}}</a></li>\r\n  <li class=\"breadcrumb-item active\">{{'Settings' | translate}}</li>\r\n</ol>\r\n\r\n<div class=\"container-fluid\" *ngIf=\"project\">\r\n  <h3>{{project.name}}</h3>\r\n  <button class=\"btn btn-success\" style=\"margin-bottom: 15px;\" (click)=\"save()\">{{'Save project settings' | translate}}</button>\r\n  <div>\r\n    <ul class=\"nav nav-tabs\">\r\n      <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">{{'Details' | translate}}</a></li>\r\n      <li [class.active]=\"tab == 'stand'\"><a href=\"#\" (click)=\"selectTab('stand')\">{{'Stand' | translate}}</a></li>\r\n      <li [class.active]=\"tab == 'groupList'\"><a href=\"#\" (click)=\"selectTab('groupList')\">{{'Groups' | translate}}</a></li>\r\n      <li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">{{'json' | translate}}</a></li>\r\n    </ul>\r\n    <div class=\"tab-content\" style=\"padding: 10px;\">\r\n      <div [style.display]=\"tab == 'details' ? '' : 'none'\">\r\n        <label>{{'Project name' | translate}}</label>\r\n        <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.name\"/>\r\n\r\n        <label>{{'Before scenario' | translate}}</label>\r\n        <select class=\"form-control\" title=\"{{'Before scenario' | translate}}\" [(ngModel)]=\"project.beforeScenarioPath\">\r\n          <option [ngValue]=\"null\">{{'disabled' | translate}}</option>\r\n          <option *ngFor=\"let scenario of scenarioList\" [ngValue]=\"(scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code\">{{scenario.name}}</option>\r\n        </select>\r\n\r\n        <label>{{'After scenario' | translate}}</label>\r\n        <select class=\"form-control\" title=\"{{'After scenario' | translate}}\" [(ngModel)]=\"project.afterScenarioPath\">\r\n          <option [ngValue]=\"null\">{{'disabled' | translate}}</option>\r\n          <option *ngFor=\"let scenario of scenarioList\" [ngValue]=\"(scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code\">{{scenario.name}}</option>\r\n        </select>\r\n\r\n        <label>{{'Project code' | translate}}</label>\r\n        <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.code\"/>\r\n\r\n        <hr/>\r\n        <label style=\"margin-top: 8px;\">\r\n          <input type=\"checkbox\" style=\"height: 25px;\" title=\"\" [(ngModel)]=\"project.useRandomTestId\"/>\r\n          {{'Use random testId' | translate}}\r\n        </label>\r\n        <div class=\"clearfix\"></div>\r\n\r\n        <label>{{'TestId header name' | translate}}</label>\r\n        <input class=\"form-control\" title=\"\" [(ngModel)]=\"project.testIdHeaderName\"/>\r\n      </div>\r\n      <div [style.display]=\"tab == 'groupList' ? '' : 'none'\">\r\n        <div *ngFor=\"let group of project.groupList; let i = index;\">\r\n          <div class=\"col-sm-4\" style=\"margin-bottom: 7px;\">\r\n            <a style=\"cursor: pointer; \" (click)=\"renameGroup(project.groupList[i]); false;\"><small><span class=\"glyphicon glyphicon-edit\"></span></small> <b>{{project.groupList[i]}}</b></a>\r\n            <span class=\"input-group-btn\">\r\n              <button class=\"btn btn-sm btn-default\" (click)=\"removeScenarioGroup(group)\"><span class=\"glyphicon glyphicon-minus\"></span>{{'Remove' | translate}}</button>\r\n            </span>\r\n          </div>\r\n        </div>\r\n        <div class=\"clearfix\"></div>\r\n        <button class=\"btn btn-success\" style=\"margin-top: 7px;\" (click)=\"addGroup()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add group' | translate}}</button>\r\n      </div>\r\n      <div [style.display]=\"tab == 'stand' ? '' : 'none'\">\r\n        <div style=\"border: none;\" class=\"list-group-item\" *ngIf=\"project.stand\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Service URL' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.serviceUrl\"/>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-4\">\r\n              <label>{{'Data base URL' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.dbUrl\"/>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <label>{{'Data base User' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.dbUser\"/>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <label>{{'Data base Password' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.dbPassword\"/>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'WireMock URL' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.wireMockUrl\"/>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'MqMocker URL' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.stand.mqMockUrl\"/>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <hr/>\r\n        <label>{{'AMQP broker' | translate}}</label>\r\n        <div style=\"border: none;\" class=\"list-group-item\" *ngIf=\"project.amqpBroker\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Broker type' | translate}}</label>\r\n              <div class=\"input-group-btn\">\r\n                <select disabled class=\"form-control\" title=\"{{'Broker type' | translate}}\" [ngModel]=\"project.amqpBroker.mqService\">\r\n                  <option [ngValue]=\"null\">{{'disabled' | translate}}</option>\r\n                  <option [ngValue]=\"'ACTIVE_MQ'\">Active MQ</option>\r\n                  <option [ngValue]=\"'RABBIT_MQ'\">Rabbit MQ</option>\r\n                </select>\r\n              </div>\r\n\r\n              <label>{{'Host' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.amqpBroker.host\"/>\r\n\r\n              <label>{{'Port' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.amqpBroker.port\"/>\r\n\r\n              <label>{{'Username' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.amqpBroker.username\"/>\r\n\r\n              <label>{{'Password' | translate}}</label>\r\n              <input disabled class=\"form-control\" title=\"\" [ngModel]=\"project.amqpBroker.password\"/>\r\n            </div>\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <div [style.display]=\"tab == 'json' ? '' : 'none'\">\r\n        <pre>{{project | json}}</pre>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/project-settings/project-settings.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
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
    function ProjectSettingsComponent(projectService, route, customToastyService, translate) {
        this.projectService = projectService;
        this.route = route;
        this.customToastyService = customToastyService;
        this.translate = translate;
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
    ProjectSettingsComponent.prototype.removeScenarioGroup = function (group) {
        var _this = this;
        this.translate.get('Remove scenario group').subscribe(function (msg) {
            if (confirm(msg)) {
                var indexToRemove = _this.project.groupList.indexOf(group);
                if (indexToRemove > -1) {
                    _this.project.groupList.splice(indexToRemove, 1);
                }
            }
        });
    };
    ProjectSettingsComponent.prototype.addGroup = function () {
        var _this = this;
        var newGroupName;
        if ((newGroupName = prompt('New group name')) && newGroupName && newGroupName.length > 0) {
            var toasty_1 = this.customToastyService.saving('Сохранение группы...', 'Сохранение может занять некоторое время...');
            this.projectService.addNewGroup(this.project.code, newGroupName)
                .subscribe(function (groupList) {
                _this.project.groupList = groupList;
                _this.customToastyService.success('Сохранено', 'Новая группа создана');
            }, function (error) { return _this.customToastyService.error('Ошибка', 'Возможно, директорая с таким названием уже существует <hr/>' + error); }, function () { return _this.customToastyService.clear(toasty_1); });
        }
    };
    ProjectSettingsComponent.prototype.renameGroup = function (oldGroupName) {
        var _this = this;
        var newGroupName;
        if ((newGroupName = prompt('Rename group', oldGroupName.toString())) && newGroupName && newGroupName.length > 0) {
            var toasty_2 = this.customToastyService.saving('Сохранение группы...', 'Сохранение может занять некоторое время...');
            this.projectService.renameGroup(this.project.code, oldGroupName, newGroupName)
                .subscribe(function (groupList) {
                _this.project.groupList = groupList;
                _this.customToastyService.success('Сохранено', 'Группа переименована');
            }, function (error) { return _this.customToastyService.error('Ошибка', 'Возможно, директорая с таким названием уже существует <hr/>' + error); }, function () { return _this.customToastyService.clear(toasty_2); });
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
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_project_service__["a" /* ProjectService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4__ngx_translate_core__["c" /* TranslateService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__ngx_translate_core__["c" /* TranslateService */]) === "function" && _d || Object])
], ProjectSettingsComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=project-settings.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-detail/scenario-detail.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".breadcrumb-search {\r\n  position: relative;\r\n}\r\n\r\n.breadcrumb {\r\n  padding-right: 342px;\r\n}\r\n\r\napp-search {\r\n  position: absolute;\r\n\r\n  right: 40px;\r\n  top: 3px;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/scenario-detail/scenario-detail.component.html":
/***/ (function(module, exports) {

module.exports = "<div>\r\n  <div class=\"breadcrumb-search\">\r\n    <ol class=\"breadcrumb\" *ngIf=\"scenario\">\r\n      <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">{{'Projects' | translate}}</a></li>\r\n      <li class=\"breadcrumb-item\"><a [routerLink]=\"['/project', scenario.projectCode]\">{{scenario.projectCode}}.\r\n        {{scenario.projectName}}</a></li>\r\n      <li class=\"breadcrumb-item\" *ngIf=\"scenario.scenarioGroup\">\r\n        <a [routerLink]=\"['/project', scenario.projectCode]\" [queryParams]=\"{scenarioGroup: scenario.scenarioGroup}\">{{scenario.scenarioGroup}}</a>\r\n      </li>\r\n      <li class=\"breadcrumb-item active\">{{scenario.code}}. {{scenario.name}}</li>\r\n    </ol>\r\n    <app-search [projectCode]=\"scenario?.projectCode\"></app-search>\r\n  </div>\r\n\r\n  <div *ngIf=\"scenario\">\r\n    <div style=\"margin-bottom: 10px;\">\r\n      <h3>{{scenario.name}}</h3>\r\n      <a [routerLink]=\"['/project/' + projectCode + '/scenario/' + (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code + '/settings']\">{{'Settings' | translate}}</a>\r\n    </div>\r\n    <app-scenario-list-item [scenario]=\"scenario\" [projectCode]=\"projectCode\" [isLinkTitleScenario]=\"false\"></app-scenario-list-item>\r\n  </div>\r\n  <hr/>\r\n  <div class=\"help-block\" *ngIf=\"!stepList\">\r\n    <span class=\"glyphicon glyphicon-time\"></span>\r\n    {{'Loading' | translate}}\r\n  </div>\r\n  <div *ngIf=\"scenario && stepList\">\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-success\" (click)=\"saveSteps()\">{{'Save steps' | translate}}</button>\r\n      <button class=\"btn btn-danger pull-right\" (click)=\"deleteScenario()\">{{'Delete' | translate}}</button>\r\n    </div>\r\n\r\n    <div *ngFor=\"let step of stepList\">\r\n      <div style=\"margin-bottom: 15px;\" class=\"col-sm-offset-11 col-sm-1 text-right\">\r\n        <button style=\"white-space: normal;\" class=\"btn btn-xs btn-block btn-default\" (click)=\"addStepBefore(step)\"><span class=\"glyphicon glyphicon-hand-left\"></span> {{'Insert step' | translate}}</button>\r\n      </div>\r\n      <app-step-item\r\n        [step]=\"step\"\r\n        [showUpDownDeleteCloneButtons]=\"true\"\r\n        (onDeleteClick)=\"onDeleteClick(step)\"\r\n        (onUpClick)=\"onUpClick(step)\"\r\n        (onDownClick)=\"onDownClick(step)\"\r\n        (onCloneClick)=\"onCloneClick(step)\"\r\n        (onChange)=\"onChangeItem(step)\"\r\n      ></app-step-item>\r\n    </div>\r\n\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-default\" (click)=\"addStep()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add step' | translate}}</button>\r\n    </div>\r\n    <div class=\"container-fluid\" style=\"margin-bottom: 20px;\">\r\n      <button class=\"btn btn-success\" (click)=\"saveSteps()\">{{'Save steps' | translate}}</button>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__scenario_list_item_scenario_list_item_component__ = __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
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
    function ScenarioDetailComponent(router, route, scenarioService, stepService, customToastyService, translate) {
        this.router = router;
        this.route = route;
        this.scenarioService = scenarioService;
        this.stepService = stepService;
        this.customToastyService = customToastyService;
        this.translate = translate;
    }
    ScenarioDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            _this.projectCode = params['projectCode'];
            _this.scenarioService
                .findOne(_this.projectCode, params['scenarioGroup'], params['scenarioCode'])
                .subscribe(function (value) { return _this.scenario = value; }, _this.handleError);
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
            }, function (error) {
                var message = JSON.parse(error._body).message;
                _this.translate.get(message).subscribe(function (value) {
                    _this.customToastyService.error('Ошибка', value);
                });
            }, function () { return _this.customToastyService.clear(toasty_1); });
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
                var index = _this.stepList.indexOf(step);
                _this.stepList.splice(index + 1, 0, clonedStep);
                _this.customToastyService.success('Сохранено', 'Шаг склонирован');
            }, function (error) {
                console.error(error);
                _this.customToastyService.error('Ошибка', error);
            }, function () { return _this.customToastyService.clear(toasty_2); });
        }
    };
    ScenarioDetailComponent.prototype.deleteScenario = function () {
        var _this = this;
        var initialRoute = this.router.url;
        if (confirm('Confirm: Delete scenario')) {
            var toasty_3 = this.customToastyService.deletion('Удаление сценария...');
            this.scenarioService.deleteOne(this.projectCode, this.scenario)
                .subscribe(function () {
                if (_this.router.url === initialRoute) {
                    _this.router.navigate(['/project', _this.scenario.projectCode], { replaceUrl: true });
                }
                _this.customToastyService.success('Удалено', 'Сценарий удалён');
            }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty_3); });
        }
    };
    ScenarioDetailComponent.prototype.handleError = function () {
        this.router.navigate(['/'], { replaceUrl: true });
    };
    ScenarioDetailComponent.prototype.onChangeItem = function (step) {
        this.scenarioListItemComponent.updateExecutedResults(step);
    };
    return ScenarioDetailComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_15" /* ViewChild */])(__WEBPACK_IMPORTED_MODULE_7__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */]),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_7__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7__scenario_list_item_scenario_list_item_component__["a" /* ScenarioListItemComponent */]) === "function" && _a || Object)
], ScenarioDetailComponent.prototype, "scenarioListItemComponent", void 0);
ScenarioDetailComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-detail',
        template: __webpack_require__("../../../../../src/app/scenario-detail/scenario-detail.component.html"),
        styles: [__webpack_require__("../../../../../src/app/scenario-detail/scenario-detail.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__angular_router__["c" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_router__["c" /* Router */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* ActivatedRoute */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_5__service_step_service__["a" /* StepService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__service_step_service__["a" /* StepService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_6__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _f || Object, typeof (_g = typeof __WEBPACK_IMPORTED_MODULE_8__ngx_translate_core__["c" /* TranslateService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8__ngx_translate_core__["c" /* TranslateService */]) === "function" && _g || Object])
], ScenarioDetailComponent);

var _a, _b, _c, _d, _e, _f, _g;
//# sourceMappingURL=scenario-detail.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-list-item/scenario-list-item.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "input[type=checkbox] {\r\n  width: 24px;\r\n  height: 24px;\r\n  margin: 0;\r\n  vertical-align: middle;\r\n}\r\n\r\n.failedScenario {\r\n  background: rgba(247, 186, 163, 0.5);\r\n}\r\n\r\n.passedScenario {\r\n  background: rgba(181, 241, 181, 0.5);\r\n}\r\n\r\n.container-fluid {\r\n  padding-top: 7px;\r\n  padding-bottom: 25px;\r\n}\r\n\r\n.row.result {\r\n  background: #fff;\r\n  margin: 10px;\r\n  padding: 10px 0;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/scenario-list-item/scenario-list-item.component.html":
/***/ (function(module, exports) {

module.exports = "<div style=\"padding-bottom: 10px;\" class=\"container-fluid\" *ngIf=\"scenario\" [ngClass] = \"getMapStyleForScenario()\">\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-7\">\r\n      <label>\r\n        <input type=\"checkbox\" title=\"Select\" [(ngModel)]=\"scenario._selected\" (click)=\"onClick()\"/>\r\n        <a [routerLink]=\"['/project/' + projectCode + '/scenario/' + (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code]\" *ngIf=\"isLinkTitleScenario\">{{scenario.name}}</a>\r\n        <span *ngIf=\"!isLinkTitleScenario\">{{scenario.name}}</span>\r\n      </label>\r\n    </div>\r\n    <div class=\"col-sm-1\">\r\n      <div *ngIf=\"state != 'executing' && scenario.failed\" style=\"color: red\">{{'Failed' | translate}}</div>\r\n      <div *ngIf=\"state == 'executing'\" style=\"color: gray\">...</div>\r\n    </div>\r\n    <div class=\"col-sm-2\">\r\n      <button style=\"padding-bottom: 1px; padding-top: 1px;\" class=\"btn btn-primary\" *ngIf=\"state != 'executing' && state != 'starting'\" (click)=\"runScenario()\">{{'Run' | translate}}</button>\r\n      <button style=\"padding-bottom: 1px; padding-top: 1px;\" class=\"btn btn-warning\" *ngIf=\"state == 'executing'\" (click)=\"stop()\">{{'Stop' | translate}}</button>\r\n      <button style=\"padding-bottom: 1px; padding-top: 1px;\" class=\"btn btn-warning\" *ngIf=\"state == 'starting'\" disabled>{{'Starting' | translate}}...</button>\r\n\r\n      <span class=\"help-block small\" style=\"float: right;\" *ngIf=\"scenario.hasResults\"><a href=\"\" target=\"_blank\" (click)=\"getReport()\">{{'Get report' | translate}}</a></span>\r\n      <button style=\"display: none;\" class=\"btn btn-xs\" (click)=\"checkState()\">{{state}}</button>\r\n    </div>\r\n    <div class=\"col-sm-2\">\r\n      <button style=\"padding-bottom: 1px; padding-top: 1px;\" class=\"btn\" *ngIf=\"scenario.hasResults\" (click)=\"resultDetailsToggle()\">{{'Results' | translate}} <ng-container *ngIf=\"executedSteps && totalSteps\">({{executedSteps}}/{{totalSteps}})</ng-container></button>\r\n    </div>\r\n  </div>\r\n  <div class=\"row result\" *ngIf=\"showResultDetails && stepResultList\">\r\n    <ng-container *ngFor=\"let stepResult of stepResultList\">\r\n      <app-step-result-item [stepResult]=\"stepResult\" [scenario]=\"scenario\" [stepList]=\"stepList\"></app-step-result-item>\r\n    </ng-container>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/scenario-list-item/scenario-list-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_scenario__ = __webpack_require__("../../../../../src/app/model/scenario.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_file_saver_FileSaver__ = __webpack_require__("../../../../file-saver/FileSaver.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_file_saver_FileSaver___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_file_saver_FileSaver__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__model_scenario_identity__ = __webpack_require__("../../../../../src/app/model/scenario-identity.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__step_result_item_step_result_item_component__ = __webpack_require__("../../../../../src/app/step-result-item/step-result-item.component.ts");
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
        this.isLinkTitleScenario = true;
        this.onStateChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.cbStateChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.state = 'none';
        this.showResultDetails = false;
        this.resultCheckTimeout = 500;
    }
    ScenarioListItemComponent.prototype.ngOnInit = function () {
        this.scenario._selected = false;
    };
    ScenarioListItemComponent.prototype.stateChanged = function () {
        this.onStateChange.emit({ state: this.state, executedSteps: this.executedSteps, totalSteps: this.totalSteps });
    };
    ScenarioListItemComponent.prototype.runScenario = function () {
        var _this = this;
        if (this.state !== 'executing') {
            this.executedSteps = 0;
            this.stepResultList = [];
            // this.totalSteps = 0;
            this.state = 'starting';
            this.stateChanged();
            this.scenarioService.run(this.projectCode, this.scenario)
                .subscribe(function (startScenarioInfo) {
                _this.startScenarioInfo = startScenarioInfo;
                _this.state = 'executing';
                _this.stateChanged();
                _this.checkState();
            });
        }
    };
    ScenarioListItemComponent.prototype.checkState = function () {
        var _this = this;
        if (this.startScenarioInfo) {
            this.scenarioService.executionStatus(this.startScenarioInfo.runningUuid)
                .subscribe(function (executionResult) {
                if (executionResult.scenarioResultList && executionResult.scenarioResultList[0]) {
                    _this.scenario.hasResults = true;
                    var scenarioResult = executionResult.scenarioResultList[0];
                    var allSteps = scenarioResult.stepResultList;
                    if (allSteps.length > 0) {
                        if (_this.stepResultList.length === 0) {
                            _this.stepResultList.push(allSteps[0]);
                        }
                        _this.stepResultList[_this.stepResultList.length - 1] = allSteps[_this.stepResultList.length - 1];
                        for (var i = _this.stepResultList.length; i < allSteps.length; i++) {
                            _this.stepResultList.push(allSteps[i]);
                        }
                    }
                    _this.scenario.failed = allSteps != null && allSteps.filter(function (result) { return result.result === 'Fail'; }).length > 0;
                    _this.executedSteps = allSteps.filter(function (stepResult) { return stepResult.editable; }).length;
                    _this.totalSteps = scenarioResult.totalSteps;
                    if (executionResult.finished) {
                        _this.state = 'finished';
                    }
                    else {
                        setTimeout(function () {
                            _this.checkState();
                        }, _this.resultCheckTimeout);
                    }
                    _this.stateChanged();
                }
                else {
                    setTimeout(function () {
                        _this.checkState();
                    }, _this.resultCheckTimeout);
                }
            }, function (error) {
                if (error.message && !error.message.contains('Unexpected end of JSON input')) {
                    setTimeout(function () {
                        _this.checkState();
                    }, _this.resultCheckTimeout);
                }
            });
        }
    };
    ScenarioListItemComponent.prototype.resultDetailsToggle = function () {
        var _this = this;
        if (!this.stepResultList) {
            var identity = new __WEBPACK_IMPORTED_MODULE_4__model_scenario_identity__["a" /* ScenarioIdentity */](this.projectCode, this.scenario.scenarioGroup, this.scenario.code);
            this.scenarioService.getResults(identity).subscribe(function (data) {
                _this.stepResultList = data;
                _this.totalSteps = _this.stepResultList.length;
                _this.executedSteps = _this.totalSteps;
                _this.showResultDetails = !_this.showResultDetails;
            });
        }
        else {
            this.showResultDetails = !this.showResultDetails;
        }
    };
    ScenarioListItemComponent.prototype.stop = function () {
        if (this.startScenarioInfo) {
            this.scenarioService
                .stop(this.startScenarioInfo.runningUuid)
                .subscribe();
        }
    };
    ScenarioListItemComponent.prototype.getMapStyleForScenario = function () {
        if (this.state !== 'executing' && this.state !== 'starting') {
            if (this.scenario.failed === true) {
                return 'failedScenario';
            }
            if (this.scenario.failed === false) {
                return 'passedScenario';
            }
        }
        return '';
    };
    ScenarioListItemComponent.prototype.getReport = function () {
        var scenarioIdentities = [];
        var identity = new __WEBPACK_IMPORTED_MODULE_4__model_scenario_identity__["a" /* ScenarioIdentity */](this.projectCode, this.scenario.scenarioGroup, this.scenario.code);
        scenarioIdentities.push(identity);
        this.scenarioService
            .downloadReport(scenarioIdentities)
            .subscribe(function (blobReport) {
            __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3_file_saver_FileSaver__["saveAs"])(blobReport, 'report.zip');
        });
        return false;
    };
    ScenarioListItemComponent.prototype.onClick = function (event) {
        this.cbStateChange.emit(event);
    };
    ScenarioListItemComponent.prototype.updateExecutedResults = function (step) {
        var _this = this;
        this.childrenComponents.forEach(function (comp) {
            if (comp.stepItem) {
                var s = comp.step;
                if (s.code === step.code && _this.stepList.indexOf(s) === -1) {
                    // setTimeout need remove exception in dev mode https://blog.angular-university.io/angular-debugging/
                    setTimeout(function () {
                        comp.changed = true;
                    });
                }
            }
        });
    };
    return ScenarioListItemComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_scenario__["a" /* Scenario */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_scenario__["a" /* Scenario */]) === "function" && _a || Object)
], ScenarioListItemComponent.prototype, "scenario", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", String)
], ScenarioListItemComponent.prototype, "projectCode", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], ScenarioListItemComponent.prototype, "isLinkTitleScenario", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Array)
], ScenarioListItemComponent.prototype, "stepList", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], ScenarioListItemComponent.prototype, "onStateChange", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], ScenarioListItemComponent.prototype, "cbStateChange", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_16" /* ViewChildren */])(__WEBPACK_IMPORTED_MODULE_5__step_result_item_step_result_item_component__["a" /* StepResultItemComponent */]),
    __metadata("design:type", typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* QueryList */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* QueryList */]) === "function" && _b || Object)
], ScenarioListItemComponent.prototype, "childrenComponents", void 0);
ScenarioListItemComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-list-item',
        template: __webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.html"),
        styles: [__webpack_require__("../../../../../src/app/scenario-list-item/scenario-list-item.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_scenario_service__["a" /* ScenarioService */]) === "function" && _c || Object])
], ScenarioListItemComponent);

var _a, _b, _c;
//# sourceMappingURL=scenario-list-item.component.js.map

/***/ }),

/***/ "../../../../../src/app/scenario-settings/scenario-settings.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "input[type=checkbox] { width: 24px; height: 24px; margin: 0; vertical-align: middle; }\r\n.row { margin-bottom: 7px; }\r\n\r\n.breadcrumb-search {\r\n  position: relative;\r\n}\r\n\r\n.breadcrumb {\r\n  padding-right: 342px;\r\n}\r\n\r\napp-search {\r\n  position: absolute;\r\n\r\n  right: 40px;\r\n  top: 3px;\r\n}\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/scenario-settings/scenario-settings.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"breadcrumb-search\">\r\n  <ol class=\"breadcrumb\">\r\n    <li class=\"breadcrumb-item\"><a [routerLink]=\"'/'\">{{'Projects' | translate}}</a></li>\r\n    <li *ngIf=\"project\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project', project.code]\">{{project.code}}. {{project.name}}</a></li>\r\n    <li class=\"breadcrumb-item\" *ngIf=\"scenario && scenario.scenarioGroup && project\">\r\n      <a [routerLink]=\"['/project', scenario.projectCode]\" [queryParams]=\"{scenarioGroup: scenario.scenarioGroup}\">{{scenario.scenarioGroup}}</a>\r\n    </li>\r\n    <li *ngIf=\"scenario\" class=\"breadcrumb-item\"><a [routerLink]=\"['/project/' + projectCode + '/scenario', (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code]\">{{scenario.code}}. {{scenario.name}}</a></li>\r\n    <li class=\"breadcrumb-item active\">{{'Settings' | translate}}</li>\r\n  </ol>\r\n  <app-search [projectCode]=\"projectCode\"></app-search>\r\n</div>\r\n\r\n<div *ngIf=\"scenario\">\r\n  <button style=\"margin: 15px;\" class=\"btn btn-success\" (click)=\"save()\">{{'Save scenario settings' | translate}}</button>\r\n\r\n  <div class=\"container-fluid\">\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>{{'Name' | translate}}</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <input class=\"form-control\" title=\"{{'Scenario name' | translate}}\" [(ngModel)]=\"scenario.name\"/>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>{{'Scenario group' | translate}}</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <select class=\"form-control\" title=\"{{'Scenario group' | translate}}\" *ngIf=\"project\" [(ngModel)]=\"scenario.scenarioGroup\">\r\n          <option [ngValue]=\"null\">{{'[no group]' | translate}}</option>\r\n          <option  *ngFor=\"let scenarioGroup of project.groupList\" [ngValue]=\"scenarioGroup\">{{scenarioGroup}}</option>\r\n        </select>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>{{'Before scenario ignore' | translate}}</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n        <input type=\"checkbox\" title=\"\" [(ngModel)]=\"scenario.beforeScenarioIgnore\"/>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"row\">\r\n      <div class=\"col-sm-3\">\r\n        <label>{{'After scenario ignore' | translate}}</label>\r\n      </div>\r\n      <div class=\"col-sm-9\">\r\n          <input type=\"checkbox\" title=\"\" [(ngModel)]=\"scenario.afterScenarioIgnore\"/>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/scenario-settings/scenario-settings.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__ = __webpack_require__("../../../../../src/app/service/scenario.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_project_service__ = __webpack_require__("../../../../../src/app/service/project.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__ngx_translate_core__ = __webpack_require__("../../../../@ngx-translate/core/index.js");
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
    function ScenarioSettingsComponent(router, route, scenarioService, projectService, customToastyService, translate) {
        this.router = router;
        this.route = route;
        this.scenarioService = scenarioService;
        this.projectService = projectService;
        this.customToastyService = customToastyService;
        this.translate = translate;
    }
    ScenarioSettingsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            _this.projectCode = params['projectCode'];
            _this.scenarioService
                .findOne(_this.projectCode, params['scenarioGroup'], params['scenarioCode'])
                .subscribe(function (value) {
                _this.scenario = value;
                _this.sGroup = _this.scenario.scenarioGroup;
            });
            _this.projectService
                .findOne(_this.projectCode)
                .subscribe(function (project) { return _this.project = project; });
        });
    };
    ScenarioSettingsComponent.prototype.save = function () {
        var _this = this;
        var toasty = this.customToastyService.saving();
        this.scenarioService.saveOne(this.project.code, this.scenario, this.sGroup)
            .subscribe(function (value) {
            _this.scenario = value;
            var scenarioPath = _this.scenario.scenarioGroup ? _this.scenario.scenarioGroup : '';
            _this.router.navigate([
                '/project', _this.scenario.projectCode,
                'scenario', scenarioPath, _this.scenario.code,
                'settings'
            ], { replaceUrl: false });
            _this.sGroup = _this.scenario.scenarioGroup;
            _this.customToastyService.success('Сохранено', 'Сценарий сохранен');
        }, function (error) { return _this.handleError(error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    ScenarioSettingsComponent.prototype.handleError = function (error) {
        var _this = this;
        var message = JSON.parse(error._body).message;
        this.translate.get(message).subscribe(function (value) {
            _this.customToastyService.error('Ошибка', value);
        });
    };
    return ScenarioSettingsComponent;
}());
ScenarioSettingsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-scenario-settings',
        template: __webpack_require__("../../../../../src/app/scenario-settings/scenario-settings.component.html"),
        styles: [__webpack_require__("../../../../../src/app/scenario-settings/scenario-settings.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["c" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* ActivatedRoute */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__["a" /* ScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__service_scenario_service__["a" /* ScenarioService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_3__service_project_service__["a" /* ProjectService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_project_service__["a" /* ProjectService */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_5__ngx_translate_core__["c" /* TranslateService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__ngx_translate_core__["c" /* TranslateService */]) === "function" && _f || Object])
], ScenarioSettingsComponent);

var _a, _b, _c, _d, _e, _f;
//# sourceMappingURL=scenario-settings.component.js.map

/***/ }),

/***/ "../../../../../src/app/search-scenario/search-scenario.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".search-block {\r\n  position: relative;\r\n}\r\n\r\n.search-block input {\r\n  padding: 3px 5px;\r\n  width: 300px;\r\n}\r\n\r\n.search-result {\r\n  position: absolute;\r\n\r\n  top: 34px;\r\n  right: 0;\r\n  border: 1px solid #eeeeee;\r\n  border-top-width: 0;\r\n\r\n  box-shadow: 1px 1px 2px 2px rgba(238, 238, 238, 0.9);\r\n  background-color: #f5f5f5;\r\n  z-index: 5;\r\n\r\n  max-height: 400px;\r\n  width: 350px;\r\n  overflow-y: scroll;\r\n  overflow-wrap: break-word;\r\n}\r\n\r\n.search-result .error {\r\n  margin: 0;\r\n  padding: 3px;\r\n  text-align: center;\r\n  color: #ff4038;\r\n}\r\n.search-result ol {\r\n  padding: 0;\r\n  margin: 0;\r\n}\r\n\r\n.search-result ol li {\r\n  list-style: none;\r\n  background: url(" + __webpack_require__("../../../../../src/app/shared/style/img/icon.png") + ") no-repeat 2px 6px;\r\n  font-size: 12px;\r\n  padding: 3px;\r\n  padding-left: 22px;\r\n\r\n  cursor: pointer;\r\n}\r\n\r\n.search-result ol li:hover {\r\n  color: #ffffff;\r\n  background-color: #6699cc;\r\n}\r\n\r\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/search-scenario/search-scenario.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"search-block\" *ngIf=\"projectCode\">\r\n  <input #queryInput [formControl] =\"queryField\" placeholder=\"{{'Search by method name' | translate}}\"/>\r\n  <div class=\"search-result\" #searchResult *ngIf=\"scenarioList || errorFlag\">\r\n    <ol *ngIf=\"scenarioList\">\r\n      <li *ngFor=\"let scenario of scenarioList\" [routerLink]=\"['/project/' + projectCode + '/scenario/' + (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code]\">{{scenario.name}}</li>\r\n    </ol>\r\n    <p class=\"error\" *ngIf=\"errorFlag\">{{'Nothing found' | translate}}</p>\r\n  </div>\r\n</div>\r\n\r\n\r\n"

/***/ }),

/***/ "../../../../../src/app/search-scenario/search-scenario.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__ = __webpack_require__("../../../../rxjs/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_search_scenario_service__ = __webpack_require__("../../../../../src/app/service/search-scenario.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SearchComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var SearchComponent = (function () {
    function SearchComponent(searchScenarioService) {
        this.searchScenarioService = searchScenarioService;
    }
    SearchComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.queryField = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["c" /* FormControl */]();
        this.queryField.valueChanges
            .debounceTime(400)
            .distinctUntilChanged()
            .subscribe(function (query) {
            if (query === '') {
                _this.hiddenResultBlock();
                return;
            }
            _this.search(query);
        });
    };
    SearchComponent.prototype.search = function (query) {
        var _this = this;
        this.errorFlag = false;
        this.searchScenarioService.searchByMethod(this.projectCode, query)
            .subscribe(function (result) {
            if (!result.length) {
                _this.hiddenResultBlock(true);
                return;
            }
            _this.scenarioList = result;
        }, function () {
            _this.hiddenResultBlock(true);
        });
    };
    SearchComponent.prototype.hiddenResultBlock = function (stateErrorFlag) {
        if (stateErrorFlag === void 0) { stateErrorFlag = false; }
        this.errorFlag = stateErrorFlag;
        this.scenarioList = null;
    };
    SearchComponent.prototype.onMouseDown = function (target) {
        var isShowSearchResult = (!this.searchResult || this.searchResult.nativeElement.contains(target))
            || this.queryInput.nativeElement.contains(target);
        if (isShowSearchResult) {
            return;
        }
        this.hiddenResultBlock();
    };
    return SearchComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], SearchComponent.prototype, "projectCode", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_15" /* ViewChild */])('searchResult'),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _a || Object)
], SearchComponent.prototype, "searchResult", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_15" /* ViewChild */])('queryInput'),
    __metadata("design:type", typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _b || Object)
], SearchComponent.prototype, "queryInput", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('window:mousedown', ['$event.target']),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], SearchComponent.prototype, "onMouseDown", null);
SearchComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-search',
        template: __webpack_require__("../../../../../src/app/search-scenario/search-scenario.component.html"),
        styles: [__webpack_require__("../../../../../src/app/search-scenario/search-scenario.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_3__service_search_scenario_service__["a" /* SearchScenarioService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_search_scenario_service__["a" /* SearchScenarioService */]) === "function" && _c || Object])
], SearchComponent);

var _a, _b, _c;
//# sourceMappingURL=search-scenario.component.js.map

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
    CustomToastyService.prototype.deletion = function (title, msg) {
        this.toastyService.clearAll();
        this.toastyService.warning({
            title: title ? title : 'Удаление',
            msg: msg ? msg : 'Удаление может занять некоторое время...',
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
    ProjectService.prototype.addNewGroup = function (projectCode, groupName) {
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/group', groupName, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ProjectService.prototype.renameGroup = function (projectCode, oldGroupName, newGroupName) {
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/group', {
            oldGroupName: oldGroupName,
            newGroupName: newGroupName
        }, { headers: this.headers }).map(function (value) { return value.json(); });
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
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/start', {}, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.executionStatus = function (runningUuid) {
        return this.http.get(this.globals.serviceBaseUrl + '/rest/execution/' + runningUuid + '/status').map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.stop = function (runningUuid) {
        return this.http.post(this.globals.serviceBaseUrl + '/rest/execution/' + runningUuid + '/stop', {}, { headers: this.headers });
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
    ScenarioService.prototype.saveOne = function (projectCode, scenario, scenarioGroup) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenario.code;
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath, scenario, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    ScenarioService.prototype.deleteOne = function (projectCode, scenario) {
        var scenarioPath = (scenario.scenarioGroup ? scenario.scenarioGroup + '/' : '') + scenario.code;
        return this.http.delete(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath);
    };
    ScenarioService.prototype.getResults = function (identity) {
        return this.http.post(this.globals.serviceBaseUrl + '/rest/execution/results', identity)
            .map(function (data) { return data.json(); });
    };
    ScenarioService.prototype.downloadReport = function (identities) {
        return this.http.post(this.globals.serviceBaseUrl + '/rest/execution/report', identities, { responseType: __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* ResponseContentType */].Blob }).map(function (data) { return data.blob(); });
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

/***/ "../../../../../src/app/service/search-scenario.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__globals__ = __webpack_require__("../../../../../src/app/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SearchScenarioService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var SearchScenarioService = (function () {
    function SearchScenarioService(globals, http) {
        this.globals = globals;
        this.http = http;
        this.serviceUrl = '/rest/projects';
        this.headers = new __WEBPACK_IMPORTED_MODULE_2__angular_http__["c" /* Headers */]({ 'Content-Type': 'application/json' });
    }
    SearchScenarioService.prototype.searchByMethod = function (projectCode, queryString) {
        var url = this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/search ';
        return this.http.post(url, { 'relativeUrl': queryString }, { headers: this.headers })
            .map(function (value) { return value.json(); });
    };
    return SearchScenarioService;
}());
SearchScenarioService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__globals__["a" /* Globals */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__globals__["a" /* Globals */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */]) === "function" && _b || Object])
], SearchScenarioService);

var _a, _b;
//# sourceMappingURL=search-scenario.service.js.map

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
        this.replacer = function (name, val) {
            if (name === 'stepMode' && !val) {
                return 'REST';
            }
            return val;
        };
    }
    StepService.prototype.saveStep = function (projectCode, scenarioGroup, scenarioCode, step) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http.put(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code, step, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    StepService.prototype.cloneStep = function (projectCode, scenarioGroup, scenarioCode, step) {
        var scenarioPath = (scenarioGroup ? scenarioGroup + '/' : '') + scenarioCode;
        return this.http.post(this.globals.serviceBaseUrl + this.serviceUrl + '/' + projectCode + '/scenarios/' + scenarioPath + '/steps/' + step.code + '/clone', {}, { headers: this.headers }).map(function (value) { return value.json(); });
    };
    StepService.prototype.copyStep = function (step) {
        return Object.assign({}, step);
    };
    StepService.prototype.equals = function (s1, s2) {
        return JSON.stringify(s1, this.replacer) === JSON.stringify(s2, this.replacer);
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
        this.managerServiceUrl = '/rest/version/manager';
        this.executorServiceUrl = '/rest/version/executor';
        this.wiremockServiceUrl = '/rest/version/wiremock';
    }
    VersionService.prototype.getManagerVersion = function () {
        return this.getVersion(this.managerServiceUrl);
    };
    VersionService.prototype.getExecutorVersion = function () {
        return this.getVersion(this.executorServiceUrl);
    };
    VersionService.prototype.getProjectsWiremockVersions = function () {
        return this.http.get(this.globals.serviceBaseUrl + this.wiremockServiceUrl)
            .map(function (value) { return value.json(); });
    };
    VersionService.prototype.getVersion = function (url) {
        return this.http.get(this.globals.serviceBaseUrl + url).map(function (value) { return value.json(); });
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

/***/ "../../../../../src/app/shared/diff/diff.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"col-sm-6\">\r\n  <label>{{'Actual' | translate}}</label>\r\n  <div class=\"form-control\"\r\n       #actual\r\n       appTextSelect\r\n       [appSyncScroll]=\"syncScroll && expected\">\r\n    <div *ngFor=\"let item of actualDiff\" [class.added-row]=\"item.added\">\r\n      <ng-container *ngIf=\"item.rowDiff\">\r\n        <span\r\n          *ngFor=\"let rowItem of item.rowDiff\"\r\n          class=\"diff-content\"\r\n          [class.added]=\"rowItem.added\">{{rowItem.value}}</span>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!item.rowDiff\">\r\n        <span class=\"diff-content\" [class.added]=\"item.added\">{{item.value}}</span>\r\n      </ng-container>\r\n    </div>\r\n  </div>\r\n</div>\r\n\r\n<div class=\"col-sm-6\">\r\n  <label>{{'Expected' | translate}}</label>\r\n  <div class=\"form-control\"\r\n       #expected\r\n       appTextSelect\r\n       [appSyncScroll]=\"syncScroll && actual\">\r\n    <div *ngFor=\"let item of expectedDiff\" [class.removed-row]=\"item.removed\">\r\n      <ng-container *ngIf=\"item.rowDiff\">\r\n        <span\r\n          *ngFor=\"let rowItem of item.rowDiff\"\r\n          class=\"diff-content\"\r\n          [class.removed]=\"rowItem.removed\">{{rowItem.value}}</span>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!item.rowDiff\">\r\n        <span class=\"diff-content\" [class.removed]=\"item.removed\">{{item.value}}</span>\r\n      </ng-container>\r\n    </div>\r\n  </div>\r\n</div>\r\n\r\n<div class=\"col-sm-12\">\r\n  <label>\r\n    <input type=\"checkbox\" [(ngModel)]=\"syncScroll\"> {{'Synchronize scrolling' | translate}}\r\n  </label>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/shared/diff/diff.component.scss":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".form-control {\n  overflow: auto;\n  height: 600px;\n  background-color: #eee; }\n  .form-control .diff-content {\n    white-space: pre-wrap; }\n  .form-control .added-row {\n    background-color: #dfd; }\n  .form-control .removed-row {\n    background-color: #fdd; }\n  .form-control .added {\n    background-color: #afa;\n    font-weight: bold; }\n  .form-control .removed {\n    background-color: #fbb;\n    font-weight: bold; }\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/diff/diff.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_diff__ = __webpack_require__("../../../../diff/dist/diff.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_diff___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_diff__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DiffComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var DiffComponent = DiffComponent_1 = (function () {
    function DiffComponent() {
        this.syncScroll = true;
    }
    DiffComponent.prepareStringForComparison = function (str) {
        if (!str) {
            return '';
        }
        var resultObject = DiffComponent_1.tryToParseAsJSON(str);
        return resultObject ?
            JSON.stringify(resultObject, null, 2) :
            str.replace(/\r/g, '').replace(/\t/g, '  ').trim();
    };
    DiffComponent.tryToParseAsJSON = function (str) {
        try {
            return JSON.parse(str);
        }
        catch (e) {
            return null;
        }
    };
    DiffComponent.prototype.ngOnInit = function () {
        this.formDiff();
    };
    DiffComponent.prototype.formDiff = function () {
        var actualResultStr = DiffComponent_1.prepareStringForComparison(this.actual);
        var expectedResultStr = DiffComponent_1.prepareStringForComparison(this.expected);
        var diff = __WEBPACK_IMPORTED_MODULE_1_diff__["diffLines"](expectedResultStr, actualResultStr);
        diff.forEach(function (item, i, arr) {
            if (item.removed && arr[i + 1] && arr[i + 1].added) {
                item.rowDiff = arr[i + 1].rowDiff = __WEBPACK_IMPORTED_MODULE_1_diff__["diffWordsWithSpace"](item.value, arr[i + 1].value);
            }
        });
        this.expectedDiff = diff.filter(function (item) { return !item.added; }).map(function (item) {
            if (item.rowDiff) {
                item.rowDiff = item.rowDiff.filter(function (rowDiffItem) { return !rowDiffItem.added; });
            }
            return item;
        });
        this.actualDiff = diff.filter(function (item) { return !item.removed; }).map(function (item) {
            if (item.rowDiff) {
                item.rowDiff = item.rowDiff.filter(function (rowDiffItem) { return !rowDiffItem.removed; });
            }
            return item;
        });
    };
    return DiffComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])('expected'),
    __metadata("design:type", String)
], DiffComponent.prototype, "expected", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])('actual'),
    __metadata("design:type", String)
], DiffComponent.prototype, "actual", void 0);
DiffComponent = DiffComponent_1 = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-diff',
        template: __webpack_require__("../../../../../src/app/shared/diff/diff.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/diff/diff.component.scss")]
    })
], DiffComponent);

var DiffComponent_1;
//# sourceMappingURL=diff.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/directives/sync-scroll.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SyncScrollDirective; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SyncScrollDirective = (function () {
    function SyncScrollDirective(ref) {
        this.ref = ref;
    }
    SyncScrollDirective.prototype.allowScroll = function () {
        this.ref.nativeElement.disableScrollEvent = false;
    };
    SyncScrollDirective.prototype.synchronizeScroll = function () {
        if (this.boundElement && !this.ref.nativeElement.disableScrollEvent
            && this.elemScrollDistance > 0 && this.boundElemScrollDistance > 0) {
            var scrolled = this.ref.nativeElement.scrollTop / this.elemScrollDistance;
            this.boundElement.scrollTop = scrolled * this.boundElemScrollDistance;
            this.boundElement.disableScrollEvent = true;
        }
    };
    SyncScrollDirective.prototype.ngAfterViewInit = function () {
        this.elemScrollDistance = this.ref.nativeElement.scrollHeight - this.ref.nativeElement.clientHeight;
        this.boundElemScrollDistance = this.boundElement.scrollHeight - this.boundElement.clientHeight;
    };
    return SyncScrollDirective;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])('appSyncScroll'),
    __metadata("design:type", Object)
], SyncScrollDirective.prototype, "boundElement", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('mouseenter'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], SyncScrollDirective.prototype, "allowScroll", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('scroll'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], SyncScrollDirective.prototype, "synchronizeScroll", null);
SyncScrollDirective = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* Directive */])({
        selector: '[appSyncScroll]',
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _a || Object])
], SyncScrollDirective);

var _a;
//# sourceMappingURL=sync-scroll.directive.js.map

/***/ }),

/***/ "../../../../../src/app/shared/directives/text-select.directive.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TextSelectDirective; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var TextSelectDirective = (function () {
    function TextSelectDirective(ref) {
        this.ref = ref;
        this.ref.nativeElement.contentEditable = true;
    }
    TextSelectDirective.prototype.disableTextChangingEvents = function (e) {
        if (e.ctrlKey && 'zxv'.includes(e.key) || [8, 46].includes(e.keyCode)) {
            e.preventDefault();
        }
    };
    TextSelectDirective.prototype.disableKeypress = function (e) {
        e.preventDefault();
    };
    TextSelectDirective.prototype.disableInput = function (e) {
        this.ref.nativeElement.innerHTML = this.content;
    };
    TextSelectDirective.prototype.ngAfterViewInit = function () {
        this.content = this.ref.nativeElement.innerHTML;
    };
    return TextSelectDirective;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('keydown', ['$event']),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TextSelectDirective.prototype, "disableTextChangingEvents", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('keypress', ['$event']),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TextSelectDirective.prototype, "disableKeypress", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_2" /* HostListener */])('input'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TextSelectDirective.prototype, "disableInput", null);
TextSelectDirective = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* Directive */])({
        selector: '[appTextSelect]',
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _a || Object])
], TextSelectDirective);

var _a;
//# sourceMappingURL=text-select.directive.js.map

/***/ }),

/***/ "../../../../../src/app/shared/style/img/icon.png":
/***/ (function(module, exports) {

module.exports = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYxIDY0LjE0MDk0OSwgMjAxMC8xMi8wNy0xMDo1NzowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNS4xIE1hY2ludG9zaCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo4MTUwOTVGNjBFN0UxMUUxODI5MDg1RjQ1MDkzN0Q0MiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo4MTUwOTVGNzBFN0UxMUUxODI5MDg1RjQ1MDkzN0Q0MiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkVBNzI4QkVCMEU3QTExRTE4MjkwODVGNDUwOTM3RDQyIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkVBNzI4QkVDMEU3QTExRTE4MjkwODVGNDUwOTM3RDQyIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+sPdjMQAAAfNJREFUeNqMUs9LVFEU/u6P+REzKIi7/gBX0qrWFQTpIjRoEBRCok2zjP4AW7doMbSrNi20Ql0oJEYouBncuNJlILiRwCmK1Hn3ns65975xnil44HDeve+c737nO0cREcSevd4iTx7kHeROgeA4eu+hJLLfvnF98vH46DL6TZLFn776RpfZl/Z3Our8orl3G/R+ZaeZ14jrPqQQjk8y/Dk+Ref3CX50/uLg8Gfweu0aXkzdwv7hUevl281mXtYH4OOFUbBao2TYrUa1UoZRfGdt8OeNmxgaqLTefG43CwCUACRZM4C1CuWSQbVcCqDhnzHBZ8dHsb130JI7e0YgtqA0A/C3Vhqe60gTszGY/7qLsjWwzGygXoVOLZ8BwMUWmAHTYPUFJPzAwzsjyDKPU/bMZRgerOHDarsIoBLip6V1PijE8YrSjKKiyM5TAJ5p3I8PFRgkDR5N3sNF5pzPBxUfxLkWVIofl9cDbXlACvKFkgTiLmWxphtjQewCQD7GxsT/DEIzPjKgHg1/HiD+mF9c6/UX0qRIknkkYdX5PMMMdEqxxXeAB2N3ee7ogRBF4URA5xy6LuYZfUkLXRbLJA2S/KFQxtgVgK7k1XgD6WIGg/UKrmK5FH1T8HgytxBxFKWpSOTNlDNTko3mRQzt5Sr9E2AAefgPadRh5OYAAAAASUVORK5CYII="

/***/ }),

/***/ "../../../../../src/app/step-item/step-item.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\" style=\"padding-bottom: 30px;\">\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-10\">\r\n      <textarea autosize rows=\"1\" style=\"font-weight: bold; border-width: 0; box-shadow: none;\" placeholder=\"{{'Step description' | translate}}\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.stepComment\" ></textarea>\r\n    </div>\r\n    <div class=\"col-sm-1\">\r\n      <input style=\"padding: 6px 1px;\" placeholder=\"{{'Timeout (ms)' | translate}}\" class=\"form-control\" title=\"\" [(ngModel)]=\"step.timeoutMs\" />\r\n    </div>\r\n    <div class=\"col-sm-1 text-right\" *ngIf=\"showUpDownDeleteCloneButtons\">\r\n      <button class=\"btn btn-xs btn-block btn-default\" (click)=\"upStep()\"><span class=\"glyphicon glyphicon-hand-up\"></span> {{'Up' | translate}}</button>\r\n      <div class=\"clearfix\"></div>\r\n      <button class=\"btn btn-xs btn-block btn-default\" (click)=\"downStep()\"><span class=\"glyphicon glyphicon-hand-down\"></span> {{'Down' | translate}}</button>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <div class=\"input-group\">\r\n        <div class=\"input-group-btn\">\r\n          <select class=\"form-control\" title=\"{{'Request method' | translate}}\" [(ngModel)]=\"step.stepMode\">\r\n            <option [ngValue]=\"'REST'\">REST</option>\r\n            <option [ngValue]=\"'JMS'\">JMS</option>\r\n          </select>\r\n        </div>\r\n        <div class=\"input-group-btn\" *ngIf=\"step.stepMode == 'REST'\">\r\n          <select class=\"form-control\" title=\"{{'Request method' | translate}}\" [(ngModel)]=\"step.requestMethod\">\r\n            <option [ngValue]=\"''\"></option>\r\n            <option [ngValue]=\"'POST'\">POST</option>\r\n            <option [ngValue]=\"'GET'\">GET</option>\r\n            <option [ngValue]=\"'PUT'\">PUT</option>\r\n            <option [ngValue]=\"'DELETE'\">DELETE</option>\r\n          </select>\r\n        </div>\r\n        <input\r\n          placeholder=\"{{'Relative url. Example' | translate}}: /relative/url?parameter=value\"\r\n          class=\"form-control\"\r\n          title=\"\"\r\n          [(ngModel)]=\"step.relativeUrl\"\r\n          *ngIf=\"step.stepMode == 'REST'\" />\r\n        <input\r\n          placeholder=\"{{'Output queue name' | translate}}\"\r\n          class=\"form-control\"\r\n          style=\"width: 50%;\"\r\n          title=\"\"\r\n          [(ngModel)]=\"step.mqOutputQueueName\"\r\n          *ngIf=\"step.stepMode == 'JMS'\" />\r\n        <input\r\n          placeholder=\"{{'Input queue name' | translate}}\"\r\n          class=\"form-control\"\r\n          style=\"width: 50%;\"\r\n          title=\"\"\r\n          [(ngModel)]=\"step.mqInputQueueName\"\r\n          *ngIf=\"step.stepMode == 'JMS'\" />\r\n        <div class=\"input-group-btn\">\r\n          <button class=\"btn btn-default\" title=\"{{'Delete' | translate}}\" *ngIf=\"showUpDownDeleteCloneButtons\" (click)=\"deleteStep()\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Delete' | translate}}</button>\r\n          <button class=\"btn btn-default\" title=\"{{'Disabled toggle' | translate}}\" *ngIf=\"!step.disabled\" (click)=\"disabledToggle()\"><span class=\"glyphicon glyphicon-flag\"></span> {{'Disable' | translate}}</button>\r\n          <button class=\"btn btn-default\" title=\"{{'Disabled toggle' | translate}}\" style=\"color: red;\" *ngIf=\"step.disabled\" (click)=\"disabledToggle()\"><span class=\"glyphicon glyphicon-flag\"></span> {{'Disabled' | translate}}</button>\r\n          <button class=\"btn btn-success\" title=\"{{'Clone' | translate}}\" *ngIf=\"showUpDownDeleteCloneButtons && step.code\" (click)=\"cloneStep()\"><span class=\"glyphicon glyphicon-paste\"></span> {{'Clone' | translate}}</button>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"row\">\r\n    <div class=\"col-sm-12\">\r\n      <ul class=\"nav nav-tabs\">\r\n        <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">{{'Details' | translate}}</a></li>\r\n        <li [class.active]=\"tab == 'scenarioVariables'\">\r\n          <a href=\"#\" (click)=\"selectTab('scenarioVariables')\">{{'Scenario variables' | translate}} <span *ngIf=\"step.jsonXPath || (step.savedValuesCheck && Object.keys(step.savedValuesCheck).length)\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'headers'\">\r\n          <a href=\"#\" (click)=\"selectTab('headers')\">{{'Headers' | translate}} <span *ngIf=\"step.requestHeaders\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'sql'\">\r\n          <a href=\"#\" (click)=\"selectTab('sql')\">Sql <span *ngIf=\"step.sqlDataList?.length > 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'mqResponses'\">\r\n          <a href=\"#\" (click)=\"selectTab('mqResponses')\">{{'Mock responses' | translate}} <span *ngIf=\"step.mockServiceResponseList?.length > 0 || (step.mqName || step.mqMessage) || step.mqMockResponseList?.length > 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'mqRequests'\">\r\n          <a href=\"#\" (click)=\"selectTab('mqRequests')\">{{'Mock requests' | translate}} <span *ngIf=\"step.expectedServiceRequestList?.length > 0 || step.parseMockRequestUrl || (step.expectedMqRequestList?.length > 0 || step.scenarioVariableFromMqRequestList?.length > 0)\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'polling'\">\r\n          <a href=\"#\" (click)=\"selectTab('polling')\">{{'Polling' | translate}} <span *ngIf=\"step.usePolling\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'parameterSet'\">\r\n          <a href=\"#\" (click)=\"selectTab('parameterSet')\">{{'Test cases' | translate}} <span *ngIf=\"step.stepParameterSetList && step.stepParameterSetList.length != 0\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'script'\">\r\n          <a href=\"#\" (click)=\"selectTab('script')\">{{'Script' | translate}} <span *ngIf=\"step.script\" class=\"glyphicon glyphicon-tag\"></span></a>\r\n        </li>\r\n        <li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">json</a></li>\r\n      </ul>\r\n      <div class=\"tab-content\" style=\"padding: 10px;\">\r\n        <div *ngIf=\"tab == 'details'\" class=\"row\">\r\n          <div class=\"col-sm-6\">\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'REST'\">\r\n                <label>{{'Request body type' | translate}}:</label>\r\n              </div>\r\n              <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'JMS'\">\r\n                <label>{{'Wait response timeout' | translate}}:</label>\r\n              </div>\r\n              <div class=\"col-sm-3\">\r\n                <label>{{'Number of repetitions' | translate}}:</label>\r\n              </div>\r\n              <div class=\"col-sm-3\">\r\n                <label>{{'Ignore response' | translate}}:</label>\r\n              </div>\r\n            </div>\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'REST'\">\r\n                <select class=\"form-control\" title=\"{{'Request body type' | translate}}\" [(ngModel)]=\"step.requestBodyType\">\r\n                  <option [ngValue]=\"'JSON'\">{{'JSON request body (default)' | translate}}</option>\r\n                  <option [ngValue]=\"'FORM'\">{{'FORM-data request body' | translate}}</option>\r\n                </select>\r\n              </div>\r\n              <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'JMS'\">\r\n                <input title=\"\" placeholder=\"N or variable (ms, 1000 default)\" class=\"form-control\" [(ngModel)]=\"step.mqTimeoutMs\"/>\r\n              </div>\r\n              <div class=\"col-sm-3\">\r\n                <input title=\"\" placeholder=\"N or variable\" class=\"form-control\" [(ngModel)]=\"step.numberRepetitions\"/>\r\n              </div>\r\n              <div class=\"col-sm-3\" style=\"text-align: center; padding-top: 10px;\">\r\n                <input title=\"\" type=\"checkbox\" [(ngModel)]=\"step.expectedResponseIgnore\"><br/>\r\n              </div>\r\n            </div>\r\n          </div>\r\n          <div class=\"col-sm-6\">\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-4\" *ngIf=\"step.stepMode == 'REST'\">\r\n                <label>{{'Expected status' | translate}}:</label>\r\n              </div>\r\n              <div class=\"col-sm-4\">\r\n                <label>{{'Compare mode' | translate}}:</label>\r\n              </div>\r\n              <div class=\"col-sm-4\" *ngIf=\"step.responseCompareMode == 'JSON'\">\r\n                <label>{{'JSON compare mode' | translate}}:</label>\r\n              </div>\r\n            </div>\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-4\" *ngIf=\"step.stepMode == 'REST'\">\r\n                <input title=\"\" placeholder=\"Example: 200, 404, 500, [empty]\" class=\"form-control\" [(ngModel)]=\"step.expectedStatusCode\"/>\r\n              </div>\r\n              <div class=\"col-sm-4\">\r\n                <select class=\"form-control\" title=\"{{'Compare mode' | translate}}\" [(ngModel)]=\"step.responseCompareMode\">\r\n                  <option [ngValue]=\"'JSON'\">JSON (default)</option>\r\n                  <option [ngValue]=\"'FULL_MATCH'\">Full match</option>\r\n                  <option [ngValue]=\"'IGNORE_MASK'\">Mask *ignore*</option>\r\n                </select>\r\n              </div>\r\n              <div class=\"col-sm-4\" *ngIf=\"step.responseCompareMode == 'JSON'\">\r\n                <select class=\"form-control\" title=\"{{'JSON compare mode' | translate}}\" [(ngModel)]=\"step.jsonCompareMode\">\r\n                  <option [ngValue]=\"'NON_EXTENSIBLE'\">NON_EXTENSIBLE (Default. Not extensible, non-strict array ordering)</option>\r\n                  <option [ngValue]=\"'STRICT'\">STRICT (Not extensible, strict array ordering)</option>\r\n                  <option [ngValue]=\"'LENIENT'\">LENIENT (Extensible, non-strict array ordering)</option>\r\n                  <option [ngValue]=\"'STRICT_ORDER'\">STRICT_ORDER (Extensible, strict array ordering)</option>\r\n                </select>\r\n              </div>\r\n            </div>\r\n          </div>\r\n          <div class=\"clearfix\"></div>\r\n          <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'REST'\">\r\n            <label>{{'Request body' | translate}}:</label>\r\n            <div *ngIf=\"step.requestBodyType != 'FORM'\">\r\n              <textarea title=\"{{'Request body' | translate}}\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.request\"></textarea>\r\n            </div>\r\n            <div *ngIf=\"step.requestBodyType == 'FORM'\">\r\n              <label>\r\n                <input title=\"\" type=\"checkbox\" [(ngModel)]=\"step.multipartFormData\">\r\n                {{'multipart/form-data' | translate}}\r\n              </label>\r\n              <div class=\"clearfix\"></div>\r\n              <div *ngFor=\"let formData of step.formDataList\" class=\"request-body-field\">\r\n                <input class=\"form-control request-body-field__name\" placeholder=\"{{'Field name' | translate}}\" title=\"\" [(ngModel)]=\"formData.fieldName\"/>\r\n                <select class=\"form-control request-body-field__type\" title=\"{{'Field type' | translate}}\" [(ngModel)]=\"formData.fieldType\">\r\n                  <option [ngValue]=\"'TEXT'\">{{'Text (default)' | translate}}</option>\r\n                  <option [ngValue]=\"'FILE'\">{{'File' | translate}}</option>\r\n                </select>\r\n                <ng-container *ngIf=\"formData.fieldType == 'FILE'\">\r\n                  <input class=\"form-control\" placeholder=\"{{'File path' | translate}}\" title=\"\" [(ngModel)]=\"formData.filePath\"/>\r\n                  <input class=\"form-control\" placeholder=\"{{'MIME type' | translate}}\" title=\"\" [(ngModel)]=\"formData.mimeType\"/>\r\n                </ng-container>\r\n                <input class=\"form-control\" *ngIf=\"formData.fieldType != 'FILE'\" placeholder=\"{{'Field value' | translate}}\" title=\"\" [(ngModel)]=\"formData.value\"/>\r\n                <button class=\"btn btn-default request-body-field__remove\" (click)=\"removeFormData(formData)\">-</button>\r\n              </div>\r\n              <button class=\"btn\" style=\"margin-top: 7px; margin-left: 7px;\" (click)=\"addFormData()\">{{'Add field' | translate}}</button>\r\n            </div>\r\n          </div>\r\n          <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'REST'\">\r\n            <label>{{'Expected response' | translate}}:</label>\r\n            <textarea title=\"{{'Expected response' | translate}}\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n          </div>\r\n          <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'JMS'\">\r\n            <label>{{'Request JMS body' | translate}}:</label>\r\n            <textarea title=\"{{'Request body' | translate}}\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.request\"></textarea>\r\n          </div>\r\n          <div class=\"col-sm-6\" *ngIf=\"step.stepMode == 'JMS'\">\r\n            <label>{{'Expected JMS response' | translate}}:</label>\r\n            <textarea title=\"{{'Expected response' | translate}}\" class=\"form-control\" rows=\"10\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'scenarioVariables'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-6\">\r\n              <label>{{'Expected response' | translate}}</label>\r\n              <textarea title=\"{{'Expected response' | translate}}\" class=\"form-control\" rows=\"13\" [(ngModel)]=\"step.expectedResponse\"></textarea>\r\n            </div>\r\n            <div class=\"col-sm-6\">\r\n              <label>{{'Scenario variables' | translate}} (JSON XPath):</label>\r\n              <textarea placeholder=\"parameterName = $.element.items[2].title\" rows=\"13\" title=\"{{'Request headers' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.jsonXPath\"></textarea>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'sql'\">\r\n          <div *ngFor=\"let sqlData of step.sqlDataList\">\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-12\">\r\n                <label>{{'Sql parameters' | translate}}</label>\r\n                <input placeholder=\"paramNameFirst, paramNameSecond\" class=\"form-control\" title=\"\" [(ngModel)]=\"sqlData.sqlSavedParameter\"/>\r\n                <label>{{'Sql query' | translate}}</label>\r\n                <input placeholder=\"select fieldFirst, fieldSecond from myTable\" class=\"form-control\" title=\"\" [(ngModel)]=\"sqlData.sql\"/>\r\n                <label>{{'Sql return type' | translate}}</label>\r\n                <select class=\"form-control\" title=\"{{'Sql return type' | translate}}\" [(ngModel)]=\"sqlData.sqlReturnType\" style=\"width: auto\">\r\n                  <option [ngValue]=\"'MAP'\">{{'Map (default)' | translate}}</option>\r\n                  <option [ngValue]=\"'OBJECT'\">{{'Object' | translate}}</option>\r\n                  <option [ngValue]=\"'LIST'\">{{'List' | translate}}</option>\r\n                  <option [ngValue]=\"'ROW'\">{{'Row' | translate}}</option>\r\n                </select>\r\n              </div>\r\n            </div>\r\n            <div class=\"row\">\r\n              <div class=\"col-sm-12\">\r\n                <button class=\"btn btn-default\" (click)=\"removeSqlData(sqlData)\">\r\n                  <span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}\r\n                </button>\r\n              </div>\r\n            </div>\r\n            <hr/>\r\n          </div>\r\n          <button class=\"btn btn-default\" (click)=\"addSqlData()\">\r\n            <span class=\"glyphicon glyphicon-plus\"></span> {{'Add' | translate}}\r\n          </button>\r\n        </div>\r\n        <div *ngIf=\"tab == 'scenarioVariables' || tab == 'sql'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <hr/>\r\n              <label>{{'Check scenario variables' | translate}}</label>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\" *ngFor=\"let checkedValueName of (step.savedValuesCheck ? Object.keys(step.savedValuesCheck) : [])\">\r\n            <div class=\"col-sm-4\" style=\"text-align: right;\">\r\n              <label>\r\n                <a href=\"#\" (click)=\"updateCheckedValueName(checkedValueName)\">{{checkedValueName}} <small><span class=\"glyphicon glyphicon-edit\"></span></small></a>\r\n              </label>\r\n            </div>\r\n            <div class=\"col-sm-8\">\r\n              <div class=\"input-group\">\r\n                <input placeholder=\"{{'Checked value' | translate}}\" title=\"\" class=\"form-control\" [(ngModel)]=\"step.savedValuesCheck[checkedValueName]\"/>\r\n                <span class=\"input-group-btn\">\r\n                  <button class=\"btn btn-default\" (click)=\"removeCheckedValue(checkedValueName)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n                </span>\r\n              </div>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <button class=\"btn btn-default\" (click)=\"addCheckedValue()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add' | translate}}</button>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'headers'\">\r\n          <label>{{'Request headers' | translate}}:</label>\r\n          <textarea placeholder=\"HeaderName: headerValue\" rows=\"5\" title=\"{{'Request headers' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.requestHeaders\"></textarea>\r\n        </div>\r\n        <div *ngIf=\"tab == 'mqResponses'\">\r\n          <h4>{{'REST mock responses' | translate}}</h4>\r\n          <div class=\"row\" *ngFor=\"let mockServiceResponse of step.mockServiceResponseList\">\r\n            <div class=\"col-sm-1\">\r\n              <button class=\"btn btn-sm btn-default\" style=\"line-height: 1.9;\" (click)=\"removeMockServiceResponse(mockServiceResponse)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n            </div>\r\n            <div class=\"col-sm-11\">\r\n              <app-mock-service-response [mockServiceResponse]=\"mockServiceResponse\"></app-mock-service-response>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"addMockServiceResponse()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add REST-mock' | translate}}</button>\r\n\r\n          <hr/>\r\n          <h4>{{'Message Queue' | translate}}</h4>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Queue name' | translate}}</label>\r\n              <input title=\"{{'Queue name' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.mqName\"/>\r\n            </div>\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Queue message' | translate}}</label>\r\n              <textarea class=\"form-control\" placeholder=\"{{'Queue message' | translate}}\" title=\"\" rows=\"7\" [(ngModel)]=\"step.mqMessage\"></textarea>\r\n            </div>\r\n            <ng-container *ngIf=\"step.mqPropertyList?.length > 0\">\r\n              <div class=\"col-sm-12\">\r\n                <label>{{'Mq properties' | translate}}</label>\r\n              </div>\r\n              <div class=\"col-sm-5\">\r\n                <label>{{'Name' | translate}}</label>\r\n              </div>\r\n              <div class=\"col-sm-5\">\r\n                <label>{{'Value' | translate}}</label>\r\n              </div>\r\n            </ng-container>\r\n            <ng-container *ngFor=\"let property of step.mqPropertyList\">\r\n              <div class=\"col-sm-5\" >\r\n                <input title=\"{{'Name' | translate}}\" placeholder=\"messageId, correlationId, replyTo, timestamp, contentType, contentEncoding, ...\" class=\"form-control\" [(ngModel)]=\"property.name\"/>\r\n              </div>\r\n              <div class=\"col-sm-6\">\r\n                <input title=\"{{'Value' | translate}}\" [placeholder]=\"property.name == 'timestamp' ? 'dd-MM-yyyy,HH:mm:ss aaa' : ''\" class=\"form-control\" [(ngModel)]=\"property.value\"/>\r\n              </div>\r\n              <div class=\"col-sm-1\">\r\n                <button class=\"btn btn-sm btn-default\" (click)=\"removeMqProperty(property)\"><span class=\"glyphicon glyphicon-minus\"></span></button>\r\n              </div>\r\n            </ng-container>\r\n            <div class=\"col-sm-12\">\r\n              <button class=\"btn btn-sm btn-default\" (click)=\"addMqProperty()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add property' | translate}}</button>\r\n            </div>\r\n          </div>\r\n\r\n          <hr/>\r\n          <h4>{{'MQ mock responses' | translate}}</h4>\r\n          <div class=\"row\" *ngFor=\"let mqMockResponse of step.mqMockResponseList\">\r\n            <div class=\"col-sm-1\">\r\n              <button class=\"btn btn-sm btn-default\" style=\"line-height: 1.9;\" (click)=\"removeMqMockResponse(mqMockResponse)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n            </div>\r\n            <div class=\"col-sm-11\">\r\n              <app-mq-mock-response [mqMockResponse]=\"mqMockResponse\"></app-mq-mock-response>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"addMqMockResponse()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add MQ mock' | translate}}</button>\r\n        </div>\r\n        <div *ngIf=\"tab == 'mqRequests'\">\r\n          <h4>{{'Expected REST requests' | translate}}</h4>\r\n          <div class=\"row\" style=\"margin-bottom: 40px;\" *ngFor=\"let expectedServiceRequest of step.expectedServiceRequestList\">\r\n            <div class=\"col-sm-12\">\r\n              <div class=\"input-group\" style=\"margin-bottom: 5px;\">\r\n                <input placeholder=\"{{'Service name' | translate}} *\" class=\"form-control\" title=\"\" [style.border-color]=\"expectedServiceRequest.serviceName ? '' : 'red'\" [(ngModel)]=\"expectedServiceRequest.serviceName\"/>\r\n                <span class=\"input-group-btn\">\r\n                  <button class=\"btn\" (click)=\"removeExpectedServiceRequest(expectedServiceRequest)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n                </span>\r\n              </div>\r\n              <div class=\"row\">\r\n                <div class=\"col-sm-12\">\r\n                  <input class=\"form-control\" placeholder=\"{{'Ignored tags' | translate}}\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"expectedServiceRequest.ignoredTags\"/>\r\n                </div>\r\n              </div>\r\n              <textarea class=\"form-control\" placeholder=\"{{'Expected request' | translate}}\" title=\"\" rows=\"7\" [(ngModel)]=\"expectedServiceRequest.expectedServiceRequest\"></textarea>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm  btn-default\" (click)=\"addExpectedServiceRequest()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add expected mock request' | translate}}</button>\r\n\r\n          <hr/>\r\n          <h4>{{'Save scenario variables from REST request' | translate}}</h4>\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Mock URL' | translate}}</label>\r\n              <input title=\"{{'Mock URL' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.parseMockRequestUrl\"/>\r\n            </div>\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'XML XPath' | translate}}</label>\r\n              <input title=\"{{'XML XPath' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.parseMockRequestXPath\"/>\r\n            </div>\r\n            <div class=\"col-sm-12\">\r\n              <label>{{'Scenario variable name' | translate}}</label>\r\n              <input title=\"{{'Scenario variable name' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.parseMockRequestScenarioVariable\"/>\r\n            </div>\r\n          </div>\r\n\r\n          <hr/>\r\n          <h4>{{'Save scenario variables from MQ request' | translate}}</h4>\r\n          <div class=\"row\" *ngIf=\"step.scenarioVariableFromMqRequestList?.length > 0\">\r\n            <div class=\"col-sm-3\">\r\n              <label>{{'Source queue name' | translate}}</label>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <label>{{'XPath' | translate}}</label>\r\n            </div>\r\n            <div class=\"col-sm-3\">\r\n              <label>{{'Variable name' | translate}}</label>\r\n            </div>\r\n          </div>\r\n          <div class=\"row\" *ngFor=\"let variableFromMq of step.scenarioVariableFromMqRequestList\">\r\n            <div class=\"col-sm-3\">\r\n              <input placeholder=\"{{'Source queue name' | translate}} *\" class=\"form-control\" title=\"\" [(ngModel)]=\"variableFromMq.sourceQueue\"/>\r\n            </div>\r\n            <div class=\"col-sm-4\">\r\n              <input class=\"form-control\"  placeholder=\"{{'XPath' | translate}}\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"variableFromMq.xpath\"/>\r\n            </div>\r\n            <div class=\"col-sm-3\">\r\n              <input class=\"form-control\" placeholder=\"{{'Variable name' | translate}}\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"variableFromMq.variableName\"/>\r\n            </div>\r\n            <div class=\"col-sm-2\">\r\n              <button class=\"btn\" (click)=\"removeVariableFromMq(variableFromMq)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"addVariableFromMq()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add scenario variable' | translate}}</button>\r\n          <hr/>\r\n          <h4>{{'Expected MQ requests' | translate}}</h4>\r\n          <div class=\"row\" style=\"margin-bottom: 40px;\" *ngFor=\"let expectedMqRequest of step.expectedMqRequestList\">\r\n            <div class=\"col-sm-12\">\r\n              <div class=\"input-group\" style=\"margin-bottom: 5px;\">\r\n                <input placeholder=\"{{'Source queue name' | translate}} *\" class=\"form-control\" title=\"\" [style.border-color]=\"expectedMqRequest.sourceQueue ? '' : 'red'\" [(ngModel)]=\"expectedMqRequest.sourceQueue\"/>\r\n                <span class=\"input-group-btn\">\r\n                  <button class=\"btn\" (click)=\"removeExpectedMqRequest(expectedMqRequest)\"><span class=\"glyphicon glyphicon-minus\"></span> {{'Remove' | translate}}</button>\r\n                </span>\r\n              </div>\r\n              <div class=\"row\">\r\n                <div class=\"col-sm-8\">\r\n                  <input class=\"form-control\" placeholder=\"{{'Ignored tags' | translate}}\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"expectedMqRequest.ignoredTags\"/>\r\n                </div>\r\n                <div class=\"col-sm-4\">\r\n                  <input class=\"form-control\" placeholder=\"{{'Repeat count' | translate}}\" style=\"margin-bottom: 5px;\" title=\"\" [(ngModel)]=\"expectedMqRequest.count\"/>\r\n                </div>\r\n              </div>\r\n              <textarea class=\"form-control\" placeholder=\"{{'Expected MQ request' | translate}}\" title=\"\" rows=\"7\" [(ngModel)]=\"expectedMqRequest.requestBody\"></textarea>\r\n            </div>\r\n          </div>\r\n          <button class=\"btn btn-sm  btn-default\" (click)=\"addExpectedMqRequest()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add expected mock MQ request' | translate}}</button>\r\n        </div>\r\n        <div *ngIf=\"tab == 'polling'\">\r\n          <div class=\"row\">\r\n            <div class=\"col-sm-2\">\r\n              <label>\r\n                <input type=\"checkbox\" title=\"Use polling\" [(ngModel)]=\"step.usePolling\"/>\r\n                {{'Use polling' | translate}}\r\n              </label>\r\n            </div>\r\n            <div class=\"col-sm-10\">\r\n              <label>{{'Polling' | translate}} json xpath:</label>\r\n              <input placeholder=\"$.body.items\" class=\"form-control\" title=\"{{'Polling' | translate}} json xpath\" [(ngModel)]=\"step.pollingJsonXPath\" />\r\n              <div class=\"help-block\">{{'Waiting for json element exists. Example' | translate}}: $.body.items</div>\r\n            </div>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'parameterSet'\">\r\n          <app-step-parameter-set [stepParameterSetList]=\"step.stepParameterSetList\"></app-step-parameter-set>\r\n        </div>\r\n        <div *ngIf=\"tab == 'script'\">\r\n          <label>{{'Script' | translate}}:</label>\r\n          <textarea placeholder=\"\" rows=\"6\" title=\"{{'Script' | translate}}\" class=\"form-control\" [(ngModel)]=\"step.script\"></textarea>\r\n          <div class=\"help-block\">\r\n            <pre>stepStatus.exception - String. Если поле заполнено, то возникнет ошибка с указанным в поле текстом.<br/>scenarioVariables - Map. Список всех переменных сценария, доступны чтение и запись.<br/>response.statusCode - int. Код ответа.<br/>response.content - String. Содержимое ответа.<br/>response.headers - Map. Заголовки ответа. Пример: response.headers['header-name'][0]<br/>Пример сохранения заголовка в переменную:<br/>scenarioVariables.MY_VARIABLE = response.headers.get('header-name')[0];</pre>\r\n          </div>\r\n        </div>\r\n        <div *ngIf=\"tab == 'json'\"><pre>{{step | json}}</pre></div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__model_mq_mock_response__ = __webpack_require__("../../../../../src/app/model/mq-mock-response.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__model_expected_mq_request__ = __webpack_require__("../../../../../src/app/model/expected-mq-request.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__model_sql_data__ = __webpack_require__("../../../../../src/app/model/sql-data.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__model_scenario_variable_from_mq_request__ = __webpack_require__("../../../../../src/app/model/scenario-variable-from-mq-request.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__model_name_value_property__ = __webpack_require__("../../../../../src/app/model/name-value-property.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
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
    function StepItemComponent(toastyService, stepService) {
        this.toastyService = toastyService;
        this.stepService = stepService;
        this.onDeleteClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onUpClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onDownClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onCloneClick = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.onChange = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["F" /* EventEmitter */]();
        this.Object = Object;
        this.tab = 'details';
        this.toastOptions = {
            title: 'Warning',
            msg: 'Checked variable already exists',
            showClose: true,
            timeout: 15000,
            theme: 'bootstrap'
        };
    }
    StepItemComponent.prototype.ngOnInit = function () {
        if (this.step && !this.step.stepMode) {
            this.step.stepMode = 'REST';
        }
        this.oldStep = this.stepService.copyStep(this.step);
    };
    StepItemComponent.prototype.selectTab = function (tabName) {
        if (this.tab === tabName) {
            this.tab = 'none';
        }
        else {
            this.tab = tabName;
        }
        return false;
    };
    StepItemComponent.prototype.addMockServiceResponse = function () {
        if (!this.step.mockServiceResponseList) {
            this.step.mockServiceResponseList = [];
        }
        this.step.mockServiceResponseList.push(new __WEBPACK_IMPORTED_MODULE_2__model_mock_service_response__["a" /* MockServiceResponse */]());
    };
    StepItemComponent.prototype.addMqMockResponse = function () {
        if (!this.step.mqMockResponseList) {
            this.step.mqMockResponseList = [];
        }
        this.step.mqMockResponseList.push(new __WEBPACK_IMPORTED_MODULE_6__model_mq_mock_response__["a" /* MqMockResponse */]());
    };
    StepItemComponent.prototype.removeMockServiceResponse = function (mockServiceResponse) {
        if (confirm('Confirm: remove mock response')) {
            var indexToRemove = this.step.mockServiceResponseList.indexOf(mockServiceResponse);
            if (indexToRemove > -1) {
                this.step.mockServiceResponseList.splice(indexToRemove, 1);
            }
        }
    };
    StepItemComponent.prototype.addSqlData = function () {
        if (!this.step.sqlDataList) {
            this.step.sqlDataList = [];
        }
        var sqlData = new __WEBPACK_IMPORTED_MODULE_8__model_sql_data__["a" /* SqlData */]();
        sqlData.sqlReturnType = 'MAP';
        this.step.sqlDataList.push(sqlData);
    };
    StepItemComponent.prototype.removeSqlData = function (data) {
        if (confirm('Confirm: remove sql data')) {
            var indexToRemove = this.step.sqlDataList.indexOf(data);
            if (indexToRemove > -1) {
                this.step.sqlDataList.splice(indexToRemove, 1);
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
    StepItemComponent.prototype.addExpectedMqRequest = function () {
        if (!this.step.expectedMqRequestList) {
            this.step.expectedMqRequestList = [];
        }
        this.step.expectedMqRequestList.push(new __WEBPACK_IMPORTED_MODULE_7__model_expected_mq_request__["a" /* ExpectedMqRequest */]());
    };
    StepItemComponent.prototype.removeExpectedServiceRequest = function (expectedServiceRequest) {
        if (confirm('Confirm: remove expected service request')) {
            var indexToRemove = this.step.expectedServiceRequestList.indexOf(expectedServiceRequest);
            if (indexToRemove > -1) {
                this.step.expectedServiceRequestList.splice(indexToRemove, 1);
            }
        }
    };
    StepItemComponent.prototype.removeExpectedMqRequest = function (expectedMqRequest) {
        if (confirm('Confirm: remove expected MQ request')) {
            var indexToRemove = this.step.expectedMqRequestList.indexOf(expectedMqRequest);
            if (indexToRemove > -1) {
                this.step.expectedMqRequestList.splice(indexToRemove, 1);
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
        if (!this.step.formDataList) {
            this.step.formDataList = [];
        }
        this.step.formDataList.push(new __WEBPACK_IMPORTED_MODULE_5__model_form_data__["a" /* FormData */]());
    };
    StepItemComponent.prototype.removeMqMockResponse = function (mqMockResponse) {
        if (confirm('Confirm: remove MQ mock response')) {
            var indexToRemove = this.step.mqMockResponseList.indexOf(mqMockResponse);
            if (indexToRemove > -1) {
                this.step.mqMockResponseList.splice(indexToRemove, 1);
            }
        }
    };
    StepItemComponent.prototype.removeVariableFromMq = function (variableFromMq) {
        var indexToRemove = this.step.scenarioVariableFromMqRequestList.indexOf(variableFromMq);
        if (indexToRemove > -1) {
            this.step.scenarioVariableFromMqRequestList.splice(indexToRemove, 1);
        }
    };
    StepItemComponent.prototype.addVariableFromMq = function () {
        if (!this.step.scenarioVariableFromMqRequestList) {
            this.step.scenarioVariableFromMqRequestList = [];
        }
        this.step.scenarioVariableFromMqRequestList.push(new __WEBPACK_IMPORTED_MODULE_9__model_scenario_variable_from_mq_request__["a" /* ScenarioVariableFromMqRequest */]());
    };
    StepItemComponent.prototype.addMqProperty = function () {
        if (!this.step.mqPropertyList) {
            this.step.mqPropertyList = [];
        }
        this.step.mqPropertyList.push(new __WEBPACK_IMPORTED_MODULE_10__model_name_value_property__["a" /* NameValueProperty */]());
    };
    StepItemComponent.prototype.removeMqProperty = function (property) {
        var indexToRemove = this.step.mqPropertyList.indexOf(property);
        if (indexToRemove > -1) {
            this.step.mqPropertyList.splice(indexToRemove, 1);
        }
    };
    StepItemComponent.prototype.ngDoCheck = function () {
        if (!this.stepService.equals(this.step, this.oldStep)) {
            this.onChange.emit(this.step);
        }
    };
    StepItemComponent.prototype.resetChangeState = function () {
        this.oldStep = this.stepService.copyStep(this.step);
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
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_1" /* Output */])(),
    __metadata("design:type", Object)
], StepItemComponent.prototype, "onChange", void 0);
StepItemComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_0" /* Component */])({
        selector: 'app-step-item',
        template: __webpack_require__("../../../../../src/app/step-item/step-item.component.html"),
        styles: [
            '.nav-tabs > li > a { padding: 3px 7px; }',
            '.tab-content { border: 1px solid #ddd; border-top-width: 0;}',
            '.row { margin-bottom: 5px; }',
            '.input-group-btn > select { padding: 0; width: 85px; border-right: 0; }',
            '.input-group-btn:first-child > select { border-top-left-radius: 5px; border-bottom-left-radius: 5px; }',
            '.request-body-field { display: flex; margin-bottom: 10px; }',
            '.request-body-field > * { margin: 0 5px; min-width: 0; }',
            '.request-body-field > .request-body-field__name { margin-left: 0; min-width: 25%; flex: 0 0; }',
            '.request-body-field > .request-body-field__type { min-width: 80px; flex: 0 0; }',
            '.request-body-field > .request-body-field__remove { margin-right: 0; flex: 0 0; }'
        ]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3_ng2_toasty__["b" /* ToastyService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_11__service_step_service__["a" /* StepService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_11__service_step_service__["a" /* StepService */]) === "function" && _c || Object])
], StepItemComponent);

var _a, _b, _c;
//# sourceMappingURL=step-item.component.js.map

/***/ }),

/***/ "../../../../../src/app/step-parameter-set/step-parameter-set.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"stepParameterSetList\">\r\n  <table class=\"table table-bordered\">\r\n    <thead>\r\n      <tr>\r\n        <th></th>\r\n        <th *ngFor=\"let parameterName of parameterNameList\">\r\n          <label><a href=\"#\" (click)=\"updateParameterName(parameterName)\">{{parameterName}} <small><span class=\"glyphicon glyphicon-edit\"></span></small></a></label>\r\n        </th>\r\n        <th>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"addParameterName()\"><span class=\"glyphicon glyphicon-plus\"></span></button>\r\n        </th>\r\n      </tr>\r\n    </thead>\r\n    <tbody>\r\n      <tr *ngFor=\"let stepParameterSet of stepParameterSetList\">\r\n        <th>\r\n          <input title=\"\" placeholder=\"{{'Case description' | translate}}\" class=\"form-control no-border\" [(ngModel)]=\"stepParameterSet.description\" />\r\n        </th>\r\n        <td *ngFor=\"let parameterName of parameterNameList\">\r\n          <ng-container *ngIf=\"findParameter(stepParameterSet, parameterName)\">\r\n            <input title=\"\" class=\"form-control\" [(ngModel)]=\"findParameter(stepParameterSet, parameterName).value\"/>\r\n          </ng-container>\r\n        </td>\r\n        <td>\r\n          <button class=\"btn btn-sm btn-default\" (click)=\"removeParameterSet(stepParameterSet)\"><span class=\"glyphicon glyphicon-minus\"></span></button>\r\n        </td>\r\n      </tr>\r\n    </tbody>\r\n  </table>\r\n  <button class=\"btn btn-default\" (click)=\"addParameterSet()\"><span class=\"glyphicon glyphicon-plus\"></span> {{'Add case' | translate}}</button>\r\n</div>\r\n"

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

module.exports = "<div class=\"form-group\" >\r\n  <div class=\"col-sm-1\">\r\n    <span class=\"glyphicon glyphicon-ok-circle\" style=\"color: green;\" *ngIf=\"stepResult.result == 'OK'\"></span>\r\n    <span class=\"glyphicon glyphicon-time\" style=\"color: orange;\" *ngIf=\"stepResult.result != 'OK' && stepResult.result != 'Fail'\"></span>\r\n    <span class=\"glyphicon glyphicon-remove-circle\" style=\"color: red;\" *ngIf=\"stepResult.result == 'Fail'\"></span>\r\n    {{stepResult.result}}\r\n  </div>\r\n  <div class=\"col-sm-11\">{{stepResult.step.stepComment}} {{stepResult.description}}</div>\r\n  <div class=\"clearfix\"></div>\r\n  <div style=\"color: gray;\" class=\"col-sm-11 col-sm-offset-1\">\r\n    {{stepResult.step.requestMethod}}\r\n    {{stepResult.requestUrl}}\r\n  </div>\r\n  <div class=\"col-sm-11 col-sm-offset-1\">\r\n    <a href=\"#\" style=\"text-decoration: none; border-bottom: 1px dashed;\" (click)=\"displayDetails = !displayDetails; false;\">{{'Details' | translate}} <span class=\"glyphicon\" [class.glyphicon-chevron-right]=\"!displayDetails\" [class.glyphicon-chevron-down]=\"displayDetails\"></span></a>\r\n  </div>\r\n  <div class=\"clearfix\"></div>\r\n  <div class=\"col-sm-11 col-sm-offset-1\" *ngIf=\"displayDetails\">\r\n    <ul class=\"nav nav-tabs\">\r\n      <li [class.active]=\"tab == 'details'\"><a href=\"#\" (click)=\"selectTab('details')\">{{'Details' | translate}}</a></li>\r\n      <li *ngIf=\"stepResult.editable\" [class.active]=\"tab == 'stepEdit'\"><a href=\"#\" (click)=\"selectTab('stepEdit')\">{{'Edit step' | translate}}</a></li>\r\n      <li [class.active]=\"tab == 'requests'\"><a href=\"#\" (click)=\"selectTab('requests')\">{{'Requests' | translate}} ({{stepResult.requestDataList?.length}})</a></li>\r\n      <!--li [class.active]=\"tab == 'json'\"><a href=\"#\" (click)=\"selectTab('json')\">json</a></li-->\r\n    </ul>\r\n    <div class=\"tab-content\" style=\"padding: 10px;\">\r\n      <div *ngIf=\"tab == 'requests' || tab == 'all'\">\r\n        <div class=\"col-sm-12\" style=\"color: gray;\">\r\n          {{'Total' | translate}}: {{stepResult.requestDataList?.length}}\r\n        </div>\r\n        <div class=\"row\" *ngFor=\"let requestData of stepResult.requestDataList; let i = index;\">\r\n          <div class=\"clearfix\"></div>\r\n          <div class=\"col-sm-6\">\r\n            <label>{{i + 1}}. {{'Request' | translate}}</label>\r\n            <div class=\"form-control\"\r\n                 style=\"overflow: scroll; height: 180px; background-color: #eee;\">\r\n              {{requestData.requestBody}}\r\n            </div>\r\n          </div>\r\n          <div class=\"col-sm-6\">\r\n            <label>{{'Response' | translate}}</label>\r\n            <div class=\"form-control\"\r\n                 style=\"overflow: scroll; height: 180px; background-color: #eee;\">\r\n              {{requestData.responseBody}}\r\n            </div>\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <div *ngIf=\"tab == 'details' || tab == 'all'\">\r\n        <div class=\"row\">\r\n          <div class=\"col-sm-12\" style=\"color: gray;\">\r\n            <div *ngIf=\"stepResult.pollingRetryCount > 1\">{{'Polling retry count' | translate}}: {{stepResult.pollingRetryCount}}</div>\r\n            {{'Scenario variables' | translate}}: {{stepResult.savedParameters}}<br/>\r\n            {{'Cookies' | translate}}: {{stepResult.cookies}}<br/>\r\n            <div>\r\n              {{'Sql' | translate}}:<br/>\r\n              <ng-container *ngFor=\"let query of stepResult.sqlQueryList\">\r\n                {{query}}<br/>\r\n              </ng-container>\r\n            </div>\r\n            Test id: {{stepResult.testId}}\r\n          </div>\r\n          <div class=\"clearfix\"></div>\r\n          <div class=\"col-sm-12\">\r\n            <label>{{'Request body' | translate}}</label>\r\n            <div class=\"form-control\" style=\"overflow: scroll; height: 180px; white-space: pre; background-color: #eee;\">{{stepResult.requestBody}}</div>\r\n          </div>\r\n          <div class=\"clearfix\"></div>\r\n            <div class=\"col-sm-6\">\r\n              <label>Actual</label>\r\n              <div class=\"form-control\"\r\n                   #actualResult\r\n                   style=\"overflow: scroll; height: 600px; background-color: #eee;\"\r\n                   [appSyncScroll]=\"expectedResult\" innerHTML=\"<pre class='pretransparent'>{{actualDiff}}</pre>\">\r\n            </div>\r\n            </div>\r\n            <div class=\"col-sm-6\">\r\n              <label>Expected</label>\r\n              <div class=\"form-control\"\r\n                   #expectedResult\r\n                   style=\"overflow: scroll; height: 600px; background-color: #eee;\"\r\n                   [appSyncScroll]=\"actualResult\" innerHTML=\"<pre class='pretransparent'>{{expectedDiff}}</pre>\">\r\n            </div>\r\n          </div>\r\n          <div class=\"col-sm-12\" style=\"color: gray;\">\r\n            <label>{{'Details' | translate}}</label>\r\n            <pre>{{stepResult.details}}</pre>\r\n          </div>\r\n        </div>\r\n      </div>\r\n      <div *ngIf=\"tab == 'json' || tab == 'all'\">\r\n        <pre>{{stepResult | json}}</pre>\r\n      </div>\r\n      <div *ngIf=\"stepResult.editable && (tab == 'stepEdit' || tab == 'all')\">\r\n        <app-step-item [step]=\"step\"></app-step-item>\r\n        <div style=\"display: inline-flex\">\r\n        <button class=\"btn btn-xs btn-success\" (click)=\"saveStep()\">{{'Save step' | translate}}</button>\r\n        <div *ngIf=\"changed\">\r\n            <span class=\"form-control\"  style=\"font-weight: bold; border-width: 0; box-shadow: none; color: #ff7018\">{{'Step diff' | translate}}</span>\r\n        </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/step-result-item/step-result-item.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_step_result__ = __webpack_require__("../../../../../src/app/model/step-result.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_step_service__ = __webpack_require__("../../../../../src/app/service/step.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__ = __webpack_require__("../../../../../src/app/service/custom-toasty.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__model_scenario__ = __webpack_require__("../../../../../src/app/model/scenario.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__step_item_step_item_component__ = __webpack_require__("../../../../../src/app/step-item/step-item.component.ts");
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







var StepResultItemComponent = StepResultItemComponent_1 = (function () {
    function StepResultItemComponent(route, stepService, customToastyService) {
        this.route = route;
        this.stepService = stepService;
        this.customToastyService = customToastyService;
        this.tab = 'details';
        this.displayDetails = false;
        this.changed = false;
    }
    StepResultItemComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            _this.projectCode = params['projectCode'];
            _this.formatText();
        });
        this.step = this.stepResult.step;
        if (this.stepList) {
            var foundStep = this.stepList.find(function (s) { return s.code === _this.step.code; });
            if (this.stepService.equals(this.step, foundStep)) {
                this.step = foundStep;
            }
        }
    };
    StepResultItemComponent.prototype.formatText = function () {
        var diffs = this.stepResult.diff;
        if (!diffs) {
            return;
        }
        this.expectedDiff = '';
        this.actualDiff = '';
        // выделяем запись в ожидаемых, в случае если прерации EQUAL-INSERT-EQUAL и rownum записей не отличаются
        var expectedChanges = [];
        var prevOps = '';
        var prevText = '';
        var rowNum = 0;
        var prevRowNum = 0;
        var insertText = '';
        var pattern_amp = /&/g;
        var pattern_lt = /</g;
        var pattern_gt = />/g;
        for (var x = 0; x < diffs.length; x++) {
            var op = diffs[x].operation; // Operation (insert, delete, equal)
            var text = diffs[x].text;
            text = text.replace(pattern_amp, '&amp;').replace(pattern_lt, '&lt;').replace(pattern_gt, '&gt;');
            switch (op) {
                case 'INSERT':
                    {
                        this.actualDiff = this.actualDiff.concat(StepResultItemComponent_1.wrapChanged(text, "added"));
                        prevOps = prevOps + 'I';
                        insertText = text;
                        break;
                    }
                case 'DELETE':
                    {
                        rowNum = rowNum + text.split('\n').length - 1;
                        this.expectedDiff = this.expectedDiff.concat(StepResultItemComponent_1.wrapChanged(text, "removed"));
                        prevOps = '';
                        break;
                    }
                case 'EQUAL':
                    {
                        var splitted = text.split('\n');
                        rowNum = rowNum + splitted.length - 1;
                        this.actualDiff = this.actualDiff.concat(text);
                        this.expectedDiff = this.expectedDiff.concat(text);
                        var iLength = insertText.split('\n').filter(function (v) { return v.trim() != ''; }).length;
                        // последняя строка предпоследнего изменения
                        var lastPrev = prevText.split("\n").pop().trim();
                        var firstCurrent = text.split("\n")[0];
                        // нам надо выделить строку
                        if (prevOps == 'EI' && iLength > 1 && !this.actualDiff.includes(lastPrev + firstCurrent)) {
                            expectedChanges.push(prevRowNum);
                        }
                        insertText = '';
                        prevOps = 'E';
                        prevText = text;
                        prevRowNum = rowNum;
                        break;
                    }
            }
        }
        this.actualDiff = this.wrapChangedLines(this.actualDiff, "added", "added-row", []);
        this.expectedDiff = this.wrapChangedLines(this.expectedDiff, "removed", "removed-row", expectedChanges);
    };
    /** оборачиваем строку */
    StepResultItemComponent.wrapChanged = function (text, classToWrap) {
        var wrapped;
        if (text.endsWith('\n')) {
            wrapped = '<span class="' + classToWrap + '">' + text.substr(0, text.length - 1) + '</span>' + '\n';
        }
        else {
            wrapped = '<span class="' + classToWrap + '">' + text + '</span>';
        }
        return wrapped;
    };
    /** оборачиваем строку */
    StepResultItemComponent.wrapToDiv = function (span) {
        return '<div class="unchanged-row">' + span + '</div>';
    };
    /** оборачиваем строку, если в ней есть изменения */
    StepResultItemComponent.prototype.wrapChangedLines = function (text, classToWrap, classWrap, expectedChanges) {
        var beginPattern = '<span class="' + classToWrap + '">';
        var endPattern = '</span>';
        var lines = text.split("\n");
        var results = [];
        var resultsToDiv = [];
        var hasChanges = function (line) {
            return line.indexOf(beginPattern) != -1 && line.indexOf(beginPattern) != -1;
        };
        var hasUnclosedStart = function (line) {
            var index = 0;
            var closed = false;
            var beginPatternIndex = line.indexOf(beginPattern, index);
            var endPatternIndex = line.indexOf(endPattern, index);
            while (beginPatternIndex != -1 || endPatternIndex != -1) {
                if (beginPatternIndex != -1) {
                    closed = false;
                    index = beginPatternIndex + beginPattern.length;
                }
                if (endPatternIndex == -1 && !closed) {
                    closed = true;
                }
                else if (endPatternIndex != -1) {
                    index = endPatternIndex + endPattern.length;
                }
                beginPatternIndex = line.indexOf(beginPattern, index);
                endPatternIndex = line.indexOf(endPattern, index);
            }
            return closed;
        };
        var hasClosedEnd = function (line) {
            var index = 0;
            var closed = false;
            var beginPatternIndex = line.indexOf(beginPattern, index);
            var endPatternIndex = line.indexOf(endPattern, index);
            while (beginPatternIndex != -1 || endPatternIndex != -1) {
                if (beginPatternIndex != -1) {
                    closed = false;
                    index = beginPatternIndex + beginPattern.length;
                }
                endPatternIndex = line.indexOf(endPattern, index);
                if (endPatternIndex != -1) {
                    closed = true;
                    index = endPatternIndex + endPattern.length;
                }
                beginPatternIndex = line.indexOf(beginPattern, index);
            }
            return closed;
        };
        var wasChanges = false;
        var _loop_1 = function (i) {
            var line = lines[i];
            // появилась строка с изменениями, но до этого были строки без изменений
            var changed = hasChanges(line);
            var unclosedStart = hasUnclosedStart(line);
            var closedEnd = hasClosedEnd(line);
            var oneStringDiff = changed && !unclosedStart && !closedEnd;
            if (changed && !wasChanges) {
                if (resultsToDiv.length > 0) {
                    results.push(StepResultItemComponent_1.wrapToDiv(StepResultItemComponent_1.wrapChanged(resultsToDiv.join('\n'), "diff-content")));
                    resultsToDiv = [];
                }
                wasChanges = false;
            }
            if (unclosedStart) {
                wasChanges = true;
            }
            if (oneStringDiff) {
                if (resultsToDiv.length > 0) {
                    results.push(StepResultItemComponent_1.wrapToDiv(StepResultItemComponent_1.wrapChanged(resultsToDiv.join('\n'), wasChanges ? classWrap : "diff-content")));
                    resultsToDiv = [];
                }
                wasChanges = true;
            }
            //noinspection TypeScriptValidateTypes
            if (!changed && expectedChanges.find(function (x) { return x == i; })) {
                resultsToDiv.push(StepResultItemComponent_1.wrapChanged(line, "removed-row"));
            }
            else {
                resultsToDiv.push(line);
            }
            if (oneStringDiff || closedEnd) {
                if (resultsToDiv.length > 0) {
                    results.push(StepResultItemComponent_1.wrapToDiv(StepResultItemComponent_1.wrapChanged(resultsToDiv.join('\n'), classWrap)));
                    resultsToDiv = [];
                }
                wasChanges = false;
            }
        };
        for (var i = 0; i < lines.length; i++) {
            _loop_1(i);
        }
        if (resultsToDiv.length > 0) {
            results.push(StepResultItemComponent_1.wrapToDiv(StepResultItemComponent_1.wrapChanged(resultsToDiv.join('\n'), wasChanges ? classWrap : "diff-content")));
        }
        return results.join('\n');
    };
    StepResultItemComponent.prototype.selectTab = function (tabName) {
        this.tab = tabName;
        return false;
    };
    StepResultItemComponent.prototype.saveStep = function () {
        var _this = this;
        var toasty = this.customToastyService.saving();
        this.stepService.saveStep(this.projectCode, this.scenario.scenarioGroup, this.scenario.code, this.stepResult.step)
            .subscribe(function () {
            _this.refreshStepList();
            _this.stepItem.resetChangeState();
            setTimeout(function () {
                _this.changed = false;
            });
            _this.customToastyService.success('Сохранено', 'Шаг сохранен');
        }, function (error) { return _this.customToastyService.error('Ошибка', error); }, function () { return _this.customToastyService.clear(toasty); });
    };
    StepResultItemComponent.prototype.refreshStepList = function () {
        var _this = this;
        if (this.stepList) {
            var index = this.stepList.findIndex(function (s) { return s.code === _this.step.code; });
            if (index >= 0) {
                this.stepList[index] = this.stepService.copyStep(this.step);
            }
        }
    };
    return StepResultItemComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__model_step_result__["a" /* StepResult */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__model_step_result__["a" /* StepResult */]) === "function" && _a || Object)
], StepResultItemComponent.prototype, "stepResult", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_5__model_scenario__["a" /* Scenario */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__model_scenario__["a" /* Scenario */]) === "function" && _b || Object)
], StepResultItemComponent.prototype, "scenario", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Array)
], StepResultItemComponent.prototype, "stepList", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_15" /* ViewChild */])(__WEBPACK_IMPORTED_MODULE_6__step_item_step_item_component__["a" /* StepItemComponent */]),
    __metadata("design:type", typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_6__step_item_step_item_component__["a" /* StepItemComponent */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__step_item_step_item_component__["a" /* StepItemComponent */]) === "function" && _c || Object)
], StepResultItemComponent.prototype, "stepItem", void 0);
StepResultItemComponent = StepResultItemComponent_1 = __decorate([
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
    __metadata("design:paramtypes", [typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_4__angular_router__["b" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_router__["b" /* ActivatedRoute */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_2__service_step_service__["a" /* StepService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__service_step_service__["a" /* StepService */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__service_custom_toasty_service__["a" /* CustomToastyService */]) === "function" && _f || Object])
], StepResultItemComponent);

var StepResultItemComponent_1, _a, _b, _c, _d, _e, _f;
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