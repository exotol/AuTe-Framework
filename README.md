# rest-at-manager #

Приложение для редактирования сценариев автотестов, тестов и просмотра результатов.

### 1. Хранение данных и настройка ###

Проекты и сценарии хранятся в формате YAML.
Путь к проектам настраивается в файле `environment.properties`, который расположен в одной директории с `autotester-3.0.0-SNAPSHOT.jar`.
Параметр `projects.directory.path` указывает на путь к рабочей директории, в которой находятся проекты.

Структура рабочей директории:

```
/projects.directory.path
    /PROJECT_CODE
        /main.yml
    /BCS_PROJECT
        /main.yml
```

Пример файла настроек `environment.properties`:

```
projects.directory.path=c:\\projects\\bcs-test\\
```

### 2. Запуск ###

Для работы необходимо запустить `autotester-4.0.0-SNAPSHOT.jar`.

Запускать в Windows удобней с помощью файла bat. Пример `run.bat`:

```
java -Dloader.path=lib/ -Dfile.encoding=UTF-8 -jar autotester-4.0.0-SNAPSHOT.jar --server.port=8080
```

Параметр `--server.port` указывает, какой порт используется для работы.

Параметр `-Dloader.path` указывает на относительный путь к библиотекам для ibm mq
com.ibm.mq.allclient.jar
jms.jar


После запуска приложения необходимо открыть в браузере: http://localhost:8080/

### Дополнения ###

Для выполнения тестов используется библиотека [at-executor](https://bitbucket.org/bscideas/rest-at-executor)