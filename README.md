# Best Commerce Application

## Description

The Best Commerce application allows merchants to create an account and add products<br/>

Microservices:
* Gateway API
* Merchant
* Product

Libraries:
* Messaging API

## Local build

* Install RabbitMQ

```
> docker run -d --hostname rabbitmq --log-driver=journald \
 --name rabbitmq -p 5672:5672 -p 15672:15672  \
 -p 15674:15674 -p 25672:25672 -p 61613:61613 \
  rabbitmq:3.8.8-management
```

Open rabbitMQ management console:

http://localhost:15672

Default login/password: guest/guest

Create exchange<br/>
 name: product.list.exchange<br/>
 type: direct<br/>
 durable: true<br/>
 
Create durable queues:<br/>
 product.create.queue<br/>
 product.list.queue<br/>

* Install PostgreSQL

`> docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres`

Create databases

```
> docker exec -it postgis bash
root@ :/# psql -U postgres
postgres=# create database merchant;
postgres=# create database product;
postgres=# \q
root@ :/# exit
```

* Build all modules:

`mvn clean install`

* Run Product Module:

```
> cd product
> java -jar -Dspring.profiles.active=local ./core/target/core-0.0.1-SNAPSHOT.jar
```

* Run API Gateway:

```
> cd api-gateway
> java -jar -Dspring.profiles.active=local ./target/api-gateway-0.0.1-SNAPSHOT.jar
```

Swagger will be available:
http://localhost:8081/api/swagger-ui.html

## Build docker files

First you should build image with maven dependencies. Run from the project root directory:

`> docker build -t best-commerce-artifactory:0.0.1 .`

Than you can build other modules:

```
> cd product
> docker build -t product:0.0.1 .
```

## Development

The project uses [google-java-format-plugin](https://github.com/google/google-java-format)

Microservices consist of two modules - API and core. API modules must contain only interfaces and DTOs.
It can be used as a compile time dependency in other modules. Core modules should never be used as a dependency anywhere.

## Test coverage

Project uses [JaCoCo Maven Plug-In](https://www.jacoco.org/jacoco/trunk/doc/maven.html) to keep track of test coverage.
This plugin generates a report after the `mvn package` stage in the target directory
* `target/jacoco.exec` is report in binary format. It can be used in sonar cube
* `target/site/jacoco` contains report in various human-readable formats

##TBD

- error handling
- implement merchant functionality (sign-up, sign-in)
- add spring security. [keycloak](https://www.keycloak.org/) can be used as an Identity Provider
- we either decide that we can trust RabbitMQ authorization and add merchant id into the request in Gateway API module
form its security context, or we can add JWT token into message header and implement custom library to validate
the token on each message in the resource servers (merchant, partner) and set security context for the request
- consider replacing RabbitMQ RPC calls with HTTP
- configure JaCoCO Plug-In to set minimum requirements for the test coverage
