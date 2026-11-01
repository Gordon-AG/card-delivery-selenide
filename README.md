# Card Delivery — Selenide Tests
Автотесты формы заказа доставки карты для домашнего задания 2.2 (Selenide).
## Технологии
- Java 11
- Gradle (wrapper)
- JUnit Jupiter 5.10.2
- Selenide 6.19.1
## Локальный запуск
1. Запустить SUT:
```bash
java -jar artifacts/app-card-delivery.jar &
```
2. Запустить тесты:
```bash
./gradlew test -Dselenide.headless=true
```
## CI
[![Java CI with Gradle](
https://github.com/Gordon-AG/card-delivery-selenide/
actions/workflows/gradle.yml/badge.svg
)](https://github.com/Gordon-AG/card-delivery-selenide/
actions/workflows/gradle.yml)
