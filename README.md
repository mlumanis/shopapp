# Application Assignment
### Tech stack:

* [SpringBoot] - MVC + Test
* [Hibernate] - ORM framework
* [Postgres] - containerized database used on production docker host
* [H2] - in memory database for integration tests and dev profile (NOTE: I know that we should use same database type for integration tests and production env, but for the purpose of this project and launch simplicity I decided to use H2 for integration tests)
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
* Postgres Container

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

| Service |  |
| ------ | ------ |
| Postgres database | localhost:5432/shopdb |
| SpringBoot application | http://localhost:8090 |
| Swagger Rest documentation | http://localhost:8090/swagger-ui.html |

### Database entity diagram 

![DB Diagram](/images/diagram.png)

UUIDs are exposed for REST API and IDs are stored internally.
In order to optimise queries and storage, we can us UUIDs  as binary(16).  
For purpose of this task I used traditional relational DB, so I used UUIDs  along with database generated Ids which are 
Primary Keys, so we can have multiple database source without conflicting IDs.
It also improves performance of insertions to our relational database. It's faster to append record to a database where 
INTEGER is PRIMARY KEY instead of VARCHAR(uuid).

### Alternative solution for DB models
We could use Hibernate Envers for Product auditing, we could simply store BigDecimal price instead of priceList. 
Our PRODUCT_AUD table will store all product/price changes. In SingleOrder entity we could store information about related
Product revision (create/update). On the other hand each time we create oa order we have to make a query to PRODUCT_AUD to retrieve price
information about most recent PRODUCT revision. But I intentionally chose my implemetation. Everything depends on the requirements.  

### Answers to your questions
**Authentication**\
If our API was used by different types of devices and clients, 
I would implement JWT (JSON Web Token) based authentication.  

**Redundancy**\
To make our application more redundant:
* I would use centralised log storage i.e. Logstash ELK
* By using ID sequence generation strategy and UUIDS we can create unique identifiers in our multi-database enviroment.
* In terms of authentication and session management, I would set up a Redis cluster for global session storage
* To scale scale up the system I would use EventDriven architecture. Each microservice 
sends the eventMessage to Kafka queue. On the other side of the system the are Kafka consumers that consumes event from
Kafka topic and persists it to database.  









