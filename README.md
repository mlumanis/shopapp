# Application Assignment
### Tech stack:

* [SpringBoot] - MVC + Test
* [Hibernate] - ORM framework
* [Postgres] - containerized database used on production docker host
* [H2] - in memory database for tests and dev
* [Docker] -containerization
* [Swagger]  - REST API documentation

### Installation

Clone the repository and run command:

```sh
$ docker-compose up -d
```

### Docker Environment

For the purpose of this application I containerized Postgres database(which I wouldn't do in real world). 

| Container | URL |
| ------ | ------ |
| Postgres database | 5432 |
| SpringBoot application | http://localhost:8090 |
| Swagger Rest documentation | http://localhost:8090/swagger-ui.html |

### Entity Relationship model



### Answers to your questions
####Authentication

If our API was used by different types of devices and clients, 
I would implement JWT (JSON Web Token) based authentication.  

####Redundancy
To make our application more redundant:
* I would use centralised log storage i.e. Logstash ELK
* In terms of authentication and session management, I would set up a Redis cluster for global session storage
* To scale scale up the system I would use EventDriven architecture. Each microservice 
sends the eventMessage to Kafka queue. On the other side of the system the are Kafka consumers that consumes event from KAfka topic
and persists it to database.  







