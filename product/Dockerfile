FROM best-commerce-artifactory:0.0.1 as builder

SHELL ["/bin/bash", "-c"]

WORKDIR /src
COPY . .

RUN mvn clean
RUN mvn package

FROM openjdk:11-jdk-alpine

WORKDIR /app
COPY --from=builder /src/core/target/*.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
