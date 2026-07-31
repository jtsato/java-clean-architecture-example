# Example of a Java Clean Architecture Implementation 

[![Continuous Integration](https://github.com/jtsato/java-clean-architecture-example/actions/workflows/continuous-integration.yml/badge.svg?branch=main)](https://github.com/jtsato/java-clean-architecture-example/actions/workflows/continuous-integration.yml)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)

## Prerequisites

* JDK 21
* This project uses Lombok, so enable annotation processing in your IDE

## Technology Stack
* Language: [`Java 21`](https://www.java.com/)
* Compilation: [`Maven`](https://maven.apache.org/)
* Framework: [`SpringBoot`](https://spring.io/projects/spring-boot)
* Database: [`H2`](http://h2database.com/) [`MySQL`](https://www.mysql.com/)
* Type-safe queries: [`Querydsl`](http://www.querydsl.com/)
* Entity Graph: [`JPA Entity Graph`](https://cosium.github.io/making-jpa-great-again/)
* Open API Documentation: [`springdoc-openapi`](https://springdoc.org/)
* Java bean mappings: [`MapStruct`](https://mapstruct.org/)
* Boilerplate code reducer: [`Lombok`](https://projectlombok.org/)
* Authentication & Authorization: [`Okta Identity Management`](https://www.okta.com/)

* Tests: 
    * Unit Testing: [`JUnit5`](https://junit.org/junit5/docs/current/user-guide/) 
    * Mocking: [`Mockito`](https://site.mockito.org/)
    * Mutation: Testing [`PIT Mutation Testing`](https://pitest.org/)
    * Integration Testing: [`Testcontainers`](https://www.testcontainers.org/)
    * Coverage: [`Jacoco`](https://www.jacoco.org)
    * Architecture Testing: [`ArchUnit`](https://www.archunit.org/)
