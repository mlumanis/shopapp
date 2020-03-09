# Application Assignment
### Tech stack:

* [SpringBoot] - MVC + Test
* [Hibernate] - ORM framework
* [Postgres] - containerized database used on production docker host
* [H2] - in memory database for tests and dev
* [Flyway] - database migrations
* [Docker] -containerization
* [Swagger]  - REST API documentation

### Installation

Clone the repository and run command:

```sh
$ docker-compose up -d
```

This command should set up two containers:
* Application on Spring Boot 
* Postres Container

If you want to test application on your localhost, without deploying containers, you can run the 
springBoot app using dev profile.

```sh
-Dspring.profiles.active=dev
```

This command tells Spring to use 'dev' profile and sets up H2 inmemory database.

### Docker Environment

For the purpose of this application I containerized Postgres database(which I wouldn't do in real world). 
Moreover I created REST-API documentation using Swagger library.
Services available in docker host containers:

| Service | URL |
| ------ | ------ |
| Postgres database | localhost:5432/shopdb |
| SpringBoot application | http://localhost:8090 |
| Swagger Rest documentation | http://localhost:8090/swagger-ui.html |

### Entity Relationship model



### Answers to your questions
**Authentication**\
If our API was used by different types of devices and clients, 
I would implement JWT (JSON Web Token) based authentication.  

**Redundancy**\
To make our application more redundant:
* I used uuids intead of database generated Ids. 
* I would use centralised log storage i.e. Logstash ELK
* In terms of authentication and session management, I would set up a Redis cluster for global session storage
* To scale scale up the system I would use EventDriven architecture. Each microservice 
sends the eventMessage to Kafka queue. On the other side of the system the are Kafka consumers that consumes event from
Kafka topic and persists it to database.  







