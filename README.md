# SimplLibraryCRM

## Версии
- SimplLibraryCRM_ver1 - (JDBC template, tomcat..)
- SimplLibraryCRM_ver2 - переход на Spring Boot
- SimplLibraryCRM_ver3 - переход на Hibernate ORM(DAO)
- SimplLibraryCRM_ver4 - переход на паттерн Repository, добавлен Spring security(не настроен)
- SimplLibraryCRM_ver5 - настроен Spring security, отсутствует валидация значений

### Таблицы(ver5)
```
create table person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username varchar NOT NULL UNIQUE ,
    password varchar NOT NULL,
    role varchar NOT NULL
)
```

```
create table person_info (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name varchar NOT NULL,
    birth_year int CHECK (birth_year > 1900) NOT NULL,
    email varchar UNIQUE NOT NULL,
    phone varchar CHECK (length(phone) = 12) UNIQUE NOT NULL,
    person_id int UNIQUE REFERENCES person(id) ON DELETE CASCADE
)
```

```
create table book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL,
    author varchar,
    year int,
    person_info_id int REFERENCES person_info(id) ON DELETE SET NULL
)
```


### Таблицы(ver1-4)

```
create table Person (
    person_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL,
    birth_year int CHECK (birth_year > 1900) NOT NULL,
    phone varchar CHECK (length(phone) = 12) UNIQUE NOT NULL,
    email varchar UNIQUE NOT NULL
)
```

```
insert into person (name, birth_year, phone, email) values ('Антонов Антон Антонович', 1997, '+79992851642', 'antonov_anton@gmail.com')
insert into person (name, birth_year, phone, email) values ('Авдеев Николай Анатольевич', 1943, '+79993619832', 'nikola@gmail.com');
```

```
create table book (
    book_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL,
    author varchar,
    year int,
    person_id int REFERENCES person(person_id) ON DELETE SET NULL
)
```

```
insert into book (name, author, year) values ('Гарри Поттер и узник Азкабана', 'Джоан Роулинг', 1999);
insert into book (name, author, year) values ('Зелёная миля', 'Стивен Кинг', 1996);
insert into book (name, author, year) values ('Унесённые ветром', 'Маргарет Митчелл', 1936);
insert into book (name, author, year) values ('Граф Монте-Кристо', 'Александр Дюма-отец', 1844);
insert into book (name, author, year) values ('Крёстный отец', 'Марио Пьюзо', 1969);
insert into book (name, author, year) values ('Десять негритят', 'Агата Кристи', 1939);
insert into book (name, author, year) values ('Рита Хейуорт и спасение из Шоушенка', 'Стивен Кинг', 1982);
insert into book (name, author, year) values ('Убить пересмешника', 'Харпер Ли', 1960);
insert into book (name, author, year) values ('Мастер и Маргарита', 'Михаил Булгаков', 1966);
insert into book (name, author, year) values ('На Западном фронте без перемен', 'Эрих Мария Ремарк', 1929);
insert into book (name, author, year) values ('Белый Бим Чёрное ухо', 'Гавриил Троепольский', 1971);
insert into book (name, author, year) values ('1984', 'Джордж Оруэлл', 1948);
```


[Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)
