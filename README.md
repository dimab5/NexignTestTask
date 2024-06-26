# Генератор CDR и UDR файлов для сотового оператора
## Описание задачи
Данная задача предполагает разработку программы, которая имитирует работу коммутатора сотового оператора. Она генерирует файлы CDR (Call Detail Records) и агрегирует данные из этих файлов в файлы UDR (Usage Data Report).

CDR файлы содержат записи о звонках абонентов, включая тип вызова (исходящий или входящий), номер абонента, дату и время начала и окончания звонка. UDR файлы агрегируют данные по каждому абоненту, суммируя длительность вызовов разного типа.

## Решение задачи
## Задача 1: Генерация CDR файлов
Для генерации CDR файлов был создан сервис, эмулирующий работу коммутатора (CdrService и соответствующий ему интерфейс, чтобы код был расширяемым). Каждый CDR файл представляет собой данные за один месяц. Данные в CDR файлах генерируются случайным образом: количество и длительность звонков. Список абонентов (не менее 10) хранится в локальной базе данных (таблица **abonents**). После генерации CDR файлов, данные о транзакциях пользователя помещаются в таблицу **transactions**.

## Задача 2: Генерация UDR файлов
Данные из CDR файлов передаются в сервис генерации UDR (UdrService и соответствующий ему интерфейс, чтобы код был расширяемым). UDR файлы агрегируют данные по каждому абоненту в отчет. Сгенерированные объекты отчета размещаются в /reports. Название файлов UDR соответствует шаблону: номер_месяц.json (например, 79876543221_1.json).

## Структура проекта
#### src/main/java: Исходные файлы Java.
#### models: Классы моделей для CDR и UDR данных.
#### services: Сервисы для генерации CDR и UDR файлов.
#### storage: Классы для работы с базой данных.
#### src/test/java: Тесты JUnit для проверки функциональности.
#### pom.xml: Файл конфигурации Maven.
