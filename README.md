#at-executor#
Модуль для выполнения сценариев

### Настройка ###
Для работы в конструктор необходимо передать реализацию интерфейсов:

* ScenarioRepository - для сохранения данных о последнем прохождении сценария и о количестве ошибок (т.к. это не всегда необходимо, возможно, нужно сделать этот функционал опциональным).
* ServiceResponseRepository - для работы с базой данных, используемой для обмена данными с [mock-manager](https://bitbucket.org/bscideas/soapui-mock-manager)

### Запуск сценариев ###
Запуск выполнения сценариев происходит с помощью метода executeScenarioList, в который передается объект Project (полученный их yaml-файла или БД) и список Scenario - список сценариев проекта для выполнения (список передается отдельным параметром, т.к. иногда нет необходимости запускать все тесты в проекте).

### Работа с заглушками ###
Работа с заглушками происходит через базу данных.
Автотест устанавливает необходимый ответ заглушки следующим образом:
- В базу данных записывает необходимый ответ заглушки (с дополнительными полями для идентификации проекта, вызываемого сервиса и уникального ID теста (последнее - для одновременного выполнения сценариев));
- Автотест выполняет запрос к тестируемому приложению;
- В SoapUI указан скрипт, который использует расширение mock-manager для получения необходимого ответа. MockManager обращается к базе данных для получения ответа;
- Тестируемое приложение получает необходимые ответы из заглушек и возвращает ответ, который проверяется Автотестом.

### Пример ###

Пример автотеста есть в проекте "bcs-rest-at" в ветке "new_at_executor": https://bitbucket.org/bscideas/bcs-rest-at/branch/new_at_executor