# Library Catalog REST Service

Простой REST сервис каталога библиотеки с использованием Spring JPA и Hibernate (без Spring Boot).

## Технологии

- Spring Framework 5.3.30 (Core, MVC, ORM)
- Hibernate 5.6.15.Final
- PostgreSQL 15
- JSP/JSTL
- Lombok
- Jackson (JSON)
- Maven

## Структура проекта

- **Entity**: `Book`, `Cabinet` - сущности с аннотациями JPA
- **Repository**: Интерфейсы и реализации для работы с БД через EntityManager
- **Service**: Сервисный слой
- **Controller**: REST контроллеры и Web контроллер
- **DTO**: Data Transfer Objects для API
- **Config**: Конфигурация Spring и Hibernate

## Связь между сущностями

- **Book** ↔ **Cabinet**: Many-to-One (много книг могут находиться в одном шкафу)

## Запуск

### 1. Запустить PostgreSQL через Docker

```bash
docker-compose up -d
```

Это создаст базу данных:
- БД: `library`
- Пользователь: `library_user`
- Пароль: `library_pass`
- Порт: `5432`

### 2. Собрать проект

```bash
mvn clean package
```

### 3. Развернуть на сервере приложений

Скопировать `target/10_REST-Hibernate-1.0-SNAPSHOT.war` в Tomcat/JBoss/Wildfly

Или использовать встроенный плагин Maven для Tomcat:

```bash
mvn tomcat7:run
```

### 4. Открыть приложение

```
http://localhost:8080/
```

## REST API

### Books

- `GET /api/books` - получить все книги
- `GET /api/books/{id}` - получить книгу по ID
- `GET /api/books/search?query={text}` - поиск книг по названию
- `POST /api/books` - создать книгу
  ```json
  {
    "title": "Война и мир",
    "author": "Лев Толстой",
    "isbn": "978-5-17-982132-5",
    "cabinetId": 1
  }
  ```
- `DELETE /api/books/{id}` - удалить книгу

### Cabinets

- `GET /api/cabinets` - получить все шкафы
- `GET /api/cabinets/{id}` - получить шкаф по ID
- `POST /api/cabinets` - создать шкаф
  ```json
  {
    "name": "Шкаф А1",
    "location": "Зал 1",
    "capacity": 100
  }
  ```
- `DELETE /api/cabinets/{id}` - удалить шкаф

## Web интерфейс

На главной странице (`/`) доступны:

1. **Поиск книг** - поиск по подстроке в названии
2. **Показать все книги** - отображение всех книг с информацией о шкафах
3. **Добавить шкаф** - форма для добавления нового шкафа
4. **Добавить книгу** - форма для добавления новой книги с выбором шкафа

Все операции выполняются через AJAX без перезагрузки страницы.

## Конфигурация

### AppConfig.java
- Настройка DataSource для PostgreSQL
- Конфигурация EntityManagerFactory
- Transaction Manager
- Hibernate properties (автоматическое создание таблиц)

### WebConfig.java
- Spring MVC конфигурация
- ViewResolver для JSP
- Обработчики статических ресурсов

### WebAppInitializer.java
- Инициализация без web.xml
- Регистрация DispatcherServlet

## База данных

Hibernate автоматически создаст таблицы при запуске:

```sql
CREATE TABLE cabinets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    capacity INTEGER
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    isbn VARCHAR(255),
    cabinet_id BIGINT NOT NULL,
    FOREIGN KEY (cabinet_id) REFERENCES cabinets(id)
);
```
