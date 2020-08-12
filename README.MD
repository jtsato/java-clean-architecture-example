# Example of a Java Clean Architecture Implementation 

![Java CI with Maven](https://github.com/jtsato/java-clean-architecture-example/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=io.github.jtsato%3Abookstore-application&metric=ncloc)](https://sonarcloud.io/dashboard?id=io.github.jtsato%3Abookstore-application)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.jtsato%3Abookstore-application&metric=coverage)](https://sonarcloud.io/dashboard?id=io.github.jtsato%3Abookstore-application)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=io.github.jtsato%3Abookstore-application&metric=security_rating)](https://sonarcloud.io/dashboard?id=io.github.jtsato%3Abookstore-application)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=io.github.jtsato%3Abookstore-application&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=io.github.jtsato%3Abookstore-application)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=io.github.jtsato%3Abookstore-application&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=io.github.jtsato%3Abookstore-application)

## Prerequisites

* JDK 11
* This project uses Lombok, so enable annotation processing in your IDE

## Technology Stack
* Language: [`Java 11`](https://www.java.com/) 
* Compilation: [`Maven`](https://maven.apache.org/)
* Framework: [`Spring Boot`](https://spring.io/projects/spring-boot)
* Database: [`H2`](h2database.com)
* Type-safe queries: [`Querydsl`](http://www.querydsl.com/)
* Entity Graph: [`JPA Entity Graph`](https://cosium.github.io/making-jpa-great-again/)
* Open API Documentation: [`springdoc-openapi`](https://springdoc.org/)
* Boilerplate code reducer: [`Lombok`](https://projectlombok.org/)

* Tests: 
    * Unit Testing: [`JUnit5`](https://junit.org/junit5/docs/current/user-guide/) 
    * Mocking: [`Mockito`](https://site.mockito.org/)
    * Mutation: Testing [`PIT Mutation Testing`](https://pitest.org/)
    * Coverage: [`Jacoco`](https://www.jacoco.org)
    * Architecture Testing: [`ArchUnit`](https://www.archunit.org/)
