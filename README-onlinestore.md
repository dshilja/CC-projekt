# Distributed version of the Spring onlinestore Sample Application built with Spring Cloud

[![Build Status](https://travis-ci.org/spring-onlinestore/spring-onlinestore-microservices.svg?branch=master)](https://travis-ci.org/spring-onlinestore/spring-onlinestore-microservices/) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This microservices branch was initially derived from [AngularJS version](https://github.com/spring-onlinestore/spring-onlinestore-angular1) to demonstrate how to split sample Spring application into [microservices](http://www.martinfowler.com/articles/microservices.html).
To achieve that goal we use Spring Cloud Gateway, Spring Cloud Circuit Breaker, Spring Cloud Config, Spring Cloud Sleuth, Resilience4j, Micrometer
and the Eureka Service Discovery from the [Spring Cloud Netflix](https://github.com/spring-cloud/spring-cloud-netflix) technology stack.

## Starting services locally without Docker

Every microservice is a Spring Boot application and can be started locally using IDE or `../mvnw spring-boot:run` command. Please note that supporting services (Config and Discovery Server) must be started before any other application (Customers).
Startup of Tracing server, Admin server, Grafana and Prometheus is optional.
If everything goes well, you can access the following services at given location:

- Discovery Server - http://localhost:8761
- Config Server - http://localhost:8888
- AngularJS frontend (API Gateway) - http://localhost:8080
- Tracing Server (Zipkin) - http://localhost:9411/zipkin/ (we use [openzipkin](https://github.com/openzipkin/zipkin/tree/master/zipkin-server))
- Grafana Dashboards - http://localhost:3000
- Prometheus - http://localhost:9091

You can tell Config Server to use your local Git repository by using `native` Spring profile and setting
`GIT_REPO` environment variable, for example:
`-Dspring.profiles.active=native -DGIT_REPO=/projects/spring-onlinestore-microservices-config`

## Starting services locally with docker-compose

In order to start entire infrastructure using Docker, you have to build images by executing `./mvnw clean install -P buildDocker`
from a project root. Once images are ready, you can start them with a single command
`docker-compose up`. Containers startup order is coordinated with [`dockerize` script](https://github.com/jwilder/dockerize).
After starting services it takes a while for API Gateway to be in sync with service registry,
so don't be scared of initial Spring Cloud Gateway timeouts. You can track services availability using Eureka dashboard
available by default at http://localhost:8761.

The `master` branch uses an Alpine linux with JRE 8 as Docker base. You will find a Java 11 version in the `release/java11` branch.

_NOTE: Under MacOSX or Windows, make sure that the Docker VM has enough memory to run the microservices. The default settings
are usually not enough and make the `docker-compose up` painfully slow._

## Understanding the Spring onlinestore application

[See the presentation of the Spring onlinestore Framework version](http://fr.slideshare.net/AntoineRey/spring-framework-onlinestore-sample-application)

[A blog bost introducing the Spring onlinestore Microsevices](http://javaetmoi.com/2018/10/architecture-microservices-avec-spring-cloud/) (french language)

You can then access onlinestore here: http://localhost:8080/

![Spring onlinestore Microservices screenshot](docs/application-screenshot.png)

**Architecture diagram of the Spring onlinestore Microservices**

![Spring onlinestore Microservices architecture](docs/microservices-architecture-diagram.jpg)

## In case you find a bug/suggested improvement for Spring onlinestore Microservices

Our issue tracker is available here: https://github.com/spring-onlinestore/spring-onlinestore-microservices/issues

## Database configuration

In its default configuration, onlinestore uses an in-memory database (HSQLDB) which gets populated at startup with data.
A similar setup is provided for MySql in case a persistent database configuration is needed.
Dependency for Connector/J, the MySQL JDBC driver is already included in the `pom.xml` files.

### Start a MySql database

You may start a MySql database with docker:

```
docker run -e MYSQL_ROOT_PASSWORD=onlinestore -e MYSQL_DATABASE=onlinestore -p 3306:3306 mysql:5.7.8
```

or download and install the MySQL database (e.g., MySQL Community Server 5.7 GA), which can be found here: https://dev.mysql.com/downloads/

### Use the Spring 'mysql' profile

To use a MySQL database, you have to start 3 microservices (`customers-service`)
with the `mysql` Spring profile. Add the `--spring.profiles.active=mysql` as programm argument.

By default, at startup, database schema will be created and data will be populated.
You may also manually create the onlinestore database and data by executing the `"db/mysql/{schema,data}.sql"` scripts of each 3 microservices.
In the `application.yml` of the [Configuration repository], set the `initialization-mode` to `never`.

If you are running the microservices with Docker, you have to add the `mysql` profile into the (Dockerfile)[docker/Dockerfile]:

```
ENV SPRING_PROFILES_ACTIVE docker,mysql
```

In the `mysql section` of the `application.yml` from the [Configuration repository], you have to change
the host and port of your MySQL JDBC connection string.

## Custom metrics monitoring

Grafana and Prometheus are included in the `docker-compose.yml` configuration, and the public facing applications
have been instrumented with [MicroMeter](https://micrometer.io) to collect JVM and custom business metrics.

A JMeter load testing script is available to stress the application and generate metrics: [onlinestore_test_plan.jmx](spring-onlinestore-api-gateway/src/test/jmeter/onlinestore_test_plan.jmx)

![Grafana metrics dashboard](docs/grafana-custom-metrics-dashboard.png)

### Using Prometheus

- Prometheus can be accessed from your local machine at http://localhost:9091

### Using Grafana with Prometheus

- An anonymous access and a Prometheus datasource are setup.
- A `Spring onlinestore Metrics` Dashboard is available at the URL http://localhost:3000/d/69JXeR0iw/spring-onlinestore-metrics.
  You will find the JSON configuration file here: [docker/grafana/dashboards/grafana-onlinestore-dashboard.json]().
- You may create your own dashboard or import the [Micrometer/SpringBoot dashboard](https://grafana.com/dashboards/4701) via the Import Dashboard menu item.
  The id for this dashboard is `4701`.

### Custom metrics

Spring Boot registers a lot number of core metrics: JVM, CPU, Tomcat, Logback...
The Spring Boot auto-configuration enables the instrumentation of requests handled by Spring MVC.
All those three REST controllers `ProductResource` have been instrumented by the `@Timed` Micrometer annotation at class level.

- `customers-service` application has the following custom metrics enabled:
  - @Timed: `onlinestore.product`

## Looking for something in particular?

| Spring Cloud components         | Resources                                                                                                                                                                                                   |
| ------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Configuration server            | [Config server properties](spring-onlinestore-config-server/src/main/resources/application.yml) and [Configuration repository]                                                                              |
| Service Discovery               | [Eureka server](spring-onlinestore-discovery-server) and [Service discovery client](spring-onlinestore-vets-service/src/main/java/org/springframework/samples/onlinestore/vets/VetsServiceApplication.java) |
| API Gateway                     | [Spring Cloud Gateway starter](spring-onlinestore-api-gateway/pom.xml) and [Routing configuration](/spring-onlinestore-api-gateway/src/main/resources/application.yml)                                      |
| Docker Compose                  | [Spring Boot with Docker guide](https://spring.io/guides/gs/spring-boot-docker/) and [docker-compose file](docker-compose.yml)                                                                              |
| Circuit Breaker                 | [Resilience4j fallback method](spring-onlinestore-api-gateway/src/main/java/org/springframework/samples/onlinestore/api/boundary/web/ApiGatewayController.java)                                             |
| Grafana / Prometheus Monitoring | [Micrometer implementation](https://micrometer.io/), [Spring Boot Actuator Production Ready Metrics]                                                                                                        |

| Front-end module | Files                                                                                                                                |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| Node and NPM     | [The frontend-maven-plugin plugin downloads/installs Node and NPM locally then runs Bower and Gulp](spring-onlinestore-ui/pom.xml)   |
| Bower            | [JavaScript libraries are defined by the manifest file bower.json](spring-onlinestore-ui/bower.json)                                 |
| Gulp             | [Tasks automated by Gulp: minify CSS and JS, generate CSS from LESS, copy other static resources](spring-onlinestore-ui/gulpfile.js) |
| Angular JS       | [app.js, controllers and templates](spring-onlinestore-ui/src/scripts/)                                                              |

## Interesting Spring onlinestore forks

The Spring onlinestore master branch in the main [spring-projects](https://github.com/spring-projects/spring-onlinestore)
GitHub org is the "canonical" implementation, currently based on Spring Boot and Thymeleaf.

This [spring-onlinestore-microservices](https://github.com/spring-onlinestore/spring-onlinestore-microservices/) project is one of the [several forks](https://spring-onlinestore.github.io/docs/forks.html)
hosted in a special GitHub org: [spring-onlinestore](https://github.com/spring-onlinestore).
If you have a special interest in a different technology stack
that could be used to implement the Online Shop then please join the community there.

# Contributing

The [issue tracker](https://github.com/spring-onlinestore/spring-onlinestore-microservices/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <http://editorconfig.org>.

[configuration repository]: https://github.com/dshilja/CC-projekt/tree/main/config
[spring boot actuator production ready metrics]: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html
