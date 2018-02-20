# BSC WireMock #

Тут описана настройка и работа WireMock.

## Ссылки ##

* [WireMock](http://wiremock.org/)
* [WireMock API](http://wiremock.org/docs/api/)


## Настройка ##

В файле настроек Томката conf/catalina.properties необходимо указать в параметре shared.loader путь к директории с файлом настроек WireMock - velocity.properties.

Пример:

```
...
shared.loader=c:/projects/RC/conf/
...
```

Файл velocity.properties необходимо расположить в указанной выше директории (shared.loader). Содержимое файла:

```
userdirective=ru.bsc.wiremock.velocity.directive.XPathDirective,ru.bsc.wiremock.velocity.directive.GroovyDirective
resource.loader=file
 
# Sample: "${wiremock.mapping.path}/
file.resource.loader.path=c:/work/bsc-wire-mock-velocity
 
# Sample:
# "${wiremock.mapping.path}/mappings/velocity-at-mapping.json"
# "${wiremock.mapping.path}/__files/response1.xml"
wiremock.mapping.path=c:/work/bsc-wire-mock-mapping
```


#### Пример маппинга velocity-at-mapping.json ####

(файл **at-velocity.vm** должен быть расположен в папке **file.resource.loader.path**)

На POST запрос */mockBankAccountBalanceWebServiceSoap11* с http-заголовком *testIdHeader: testIdValue* будет возвращен ответ из файла, указанного в параметре **bodyFileName**.
 
В параметре **bodyFileName** могут быть указаны:

* Файл с шаблоном .vm, пример: at-velocity.vm. При этом шаблон должен быть расположен в папке file.resource.loader.path;
* Файл с ответом, расположенный в папке ${wiremock.mapping.path}/__files/

```
{
    "priority": 50,
    "request": {
        "method": "POST",
        "urlPattern": "/mockBankAccountBalanceWebServiceSoap11",
        "headers": {
            "testIdHeader": { "equalTo": "ololoshka" }
        }
    },
    "response": {
        "status": 200,
        "bodyFileName": "at-velocity.vm",
        "headers": {
            "Content-Type": "application/xop+xml"
        },
        "transformers": ["velocity-response-transformer"]
    }
}
```

#### Пример шаблона at-velocity.vm ####

В данном примере в тело ответа вставляется текст из xml-запроса из тега //accounts/account

```
#xpath($result $requestBody "//*[local-name()='accounts']/*[local-name()='account']")
<response>
    <testIdHeader>${requestHeadertestidheader}</testIdHeader>	
    <xpath>$result</xpath>
</response>
```


#### Использование Groovy в шаблонах ####

В шаблонах можно использовать Groovy-скрипты. Пример шаблона:

```
#groovy()
// document - распарсенный запрос
def document = new XmlParser().parseText(context.get("requestBody"))
 
// Сохранение переменной в контекст для дальнейшего использования в шаблоне
context.put("out", document.accounts.account[0].text())
#end
 
Account: $out
```

Для следующего запроса шаблон выведет значение: "Account: 1234567890":

```
<body>
    <accounts>
        <account>1234567890</account>
        <account>0987654321</account>
    </accounts>
</body>
```

## Работа ##

* Просмотреть все маппинги: GET [http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/mappings](http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/mappings)
* Просмотреть журнал запросов: GET [http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/requests](http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/requests)
* Очистить журнал запросов: POST [http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/requests/reset](http://piphagor.bscmsc.ru/bsc-wire-mock/__admin/requests/reset)

Документация по WireMock API доступна по ссылке: [http://wiremock.org/docs/api/](http://wiremock.org/docs/api/) 
