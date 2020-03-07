# Application Assignment
### Tech

Dillinger uses a number of open source projects to work properly:

* [SpringBoot] - spring MVC + DI + Test
* [Hibernate] - ORM framework
* [Postgres] - containerized database used on production docker host
* [H2] - in memory database for tests and dev
* [Docker] -containerization
* [Swagger]  - REST API documentation

### Installation

Clone the repository and run

```sh
$ docker-compose up -d
```

### Docker Enviroment

| Container | Port |
| ------ | ------ |
| Postgres database | 5432 |
| SpringBoot application | 8090 |


