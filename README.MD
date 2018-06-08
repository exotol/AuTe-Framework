# Auto Test Framework (aka ATF) #

## GIT configuration ##

Configure git before commiting.
Execute following commands from command line:
```
git config user.name "John Doe"
git config user.email johndoe@example.com
```

Check configuration with `git config -l`

## Build and run ##

### Requirements ###
 * JDK 1.8
 * Apache Maven 3
 * NodeJS
 * npm

1. Clone project
2. Build project with install nodeJS dependencies `mvn clean package -P npm-install`
3. Run application `java -Dloader.path=lib/ -jar atf-application-<version>.jar` or using run.bat

### IBM MQ mocking
For mocking IBM MQ you have to find and download
* com.ibm.mq.allclient.jar
* jms.jar
and put it in `/lib` folder or another. And run application with ` java -Dloader.path=lib/ -jar atf-application-<version>.jar -Dloader.path=../lib/` command.

You can find ibm libs in fix pack [Fix Pack 8.0.0.5 for WebSphere MQ v8](http://www-01.ibm.com/support/docview.wss?uid=swg21982686) or copy it from installed IBM product.
                                  

