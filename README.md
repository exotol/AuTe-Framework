# BscWireMockUi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.2.0.

## Настройка

Настроить URL к серверу WireMock необходимо в файле src/service/wire-mock.service.ts:

```
public serviceUrl = 'http://piphagor.bscmsc.ru/bsc-wire-mock';
```

В файле index.html необходимо настроить URL к корню приложения:

```
<base href="/">
```

## Работа

При запуске сервера WireMock применяются все маппинги, которые настроены с помощью файлов на сервере. Новый маппинг будет работать сразу после его создания. Для сохранения всех созданных маппингов в файловой системе необходимо нажать кнопку "Save to back storage".

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.
