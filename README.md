# Дипломный проект по профессии «Тестировщик»

## Документы
* [План автоматизации](https://github.com/YuriKopshev/DiplomQA-Automation/blob/master/docs/plan.md)
* [Отчет по итогам тестирования](https://github.com/YuriKopshev/DiplomQA-Automation/blob/master/docs/report.md)
* [Отчет по итогам автоматизации](https://github.com/YuriKopshev/DiplomQA-Automation/blob/master/docs/summary.md)

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Описание приложения

### Бизнес часть

Приложение представляет из себя веб-сервис.

![](docs/pic/service.png)

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

## Запуск тестов

* склонировать репозиторий себе на ПК командой `git clone`
* открыть проект в IDEA
* для запуска контейнеров с MySql, PostgreSQL и Node.js запустить docker-compose.yml, использовав команду `docker-compose up`, далее в случае перезапуска приложения(контейнеров)
  или возникших проблем используем `docker-compose up -d --force-recreate` 
* запуск приложения **(для Docker Toolbox на Win 7)**:
    * для запуска под MySQL использовать команду 
    ```
    java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar aqa-shop.jar
    ```
    * для запуска под PostgreSQL использовать команду 
    ```
    java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar aqa-shop.jar
    ```
   * для запуска под MySQL использовать команду 
   ```
   gradlew -Ddb.url=jdbc:mysql://192.168.99.100:3306/app clean test
   ```
   * для запуска под PostgreSQL использовать команду 
   ```
   gradlew -Ddb.url=jdbc:postgresql://192.168.99.100:5432/app clean test
   ```
    *По умолчанию тесты запускаются для "http://localhost:8080/" ,чтобы изменить адрес, необходимо дополнительно указать -Dsut.url=...  
    *Чтобы использовать для подключения к БД логин и пароль отличные от указанных по умолчанию, необходимо дополнительно указать `-Ddb.user=...` и `-Ddb.password=...`
    
* для получения отчета (Allure) использовать команды:

`gradlew allureReport` – первый запуск для загрузки файлов allure

`gradlew allureServe` – открывает отчёт в браузере 

* после окончания тестов завершить работу приложения, и остановить контейнеры командой `docker-compose down`

## Сборка в Appveyor
Для проведения CI в Appveyor:
* генерируем файл appveyor.yml(все команды прописываем c localhost вместо 192.168.99.100)
* меняем в application.properties 192.168.99.100 на localhost
* запускаем сборку https://ci.appveyor.com

Сборка со статусом failing по причине падающих тестов!

[![Build status](https://ci.appveyor.com/api/projects/status/jxuhaw5dxqgriidl?svg=true)](https://ci.appveyor.com/project/YuriKopshev/diplomqa-automation)


