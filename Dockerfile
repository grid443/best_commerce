FROM maven:3.6.3-openjdk-11-slim as builder

SHELL ["/bin/bash", "-c"]

WORKDIR /src
COPY messaging-api .

RUN mvn clean
RUN mvn install

RUN rm -r *

COPY merchant .

WORKDIR /src/api

RUN mvn clean
RUN mvn install

WORKDIR /src

RUN rm -r *

COPY product .

WORKDIR /src/api

RUN mvn clean
RUN mvn install

RUN rm -r *
