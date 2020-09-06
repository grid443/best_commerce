# Best Commerce Application

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
