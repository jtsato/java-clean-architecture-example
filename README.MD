# Java 21 Clean Architecture Example

[![Continuous Testing](https://github.com/jtsato/java-clean-architecture-example/actions/workflows/continuous-testing.yml/badge.svg?branch=main)](https://github.com/jtsato/java-clean-architecture-example/actions/workflows/continuous-testing.yml)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jtsato_java-clean-architecture-example&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=jtsato_java-clean-architecture-example)

## Prerequisites

- JDK 21
- Maven 3.9+
- Enable annotation processing in your IDE (the project uses Lombok)

## Project Structure

- `core`: business rules, use cases and gateway contracts.
- `infra/sql`: JPA persistence adapters.
- `entrypoints/rest`: REST controllers and API mapping.
- `configuration`: Spring Boot application and cross-cutting configuration.

## Build and Quality Checks

Run the complete test suite:

```bash
mvn clean verify
```

Generate the executable Spring Boot JAR:

```bash
mvn -pl configuration -am package -DskipTests
```

Run mutation testing for the business module:

```bash
mvn -pl core pitest:mutationCoverage
```

The **Continuous Testing** workflow calls the reusable **SonarCloud Analysis** workflow after the build succeeds. The Sonar workflow can also be started manually from GitHub Actions.

The **Mutation Testing** workflow runs PIT from the project root on weekdays at 03:30 UTC (and manually on demand), then publishes the HTML reports from `docs/mutation-reports` to GitHub Pages.

## Technology Stack

- Language: [`Java 21`](https://www.java.com/)
- Build: [`Maven`](https://maven.apache.org/)
- Framework: [`Spring Boot 4.1`](https://spring.io/projects/spring-boot)
- Persistence: [`Spring Data JPA`](https://spring.io/projects/spring-data-jpa), [`H2`](http://h2database.com/) and [`MySQL`](https://www.mysql.com/)
- Type-safe queries: [`Querydsl`](https://querydsl.com/)
- Entity graphs: [`Spring Data JPA Entity Graph`](https://github.com/Cosium/spring-data-jpa-entity-graph)
- OpenAPI: [`springdoc-openapi`](https://springdoc.org/)
- Mapping: [`MapStruct`](https://mapstruct.org/) and [`Lombok`](https://projectlombok.org/)
- Authentication and authorization: [`Okta`](https://www.okta.com/)
- Tests: [`JUnit 6`](https://junit.org/), [`Mockito`](https://site.mockito.org/), [`PIT`](https://pitest.org/), [`Testcontainers`](https://testcontainers.com/), [`JaCoCo`](https://www.jacoco.org/) and [`ArchUnit`](https://www.archunit.org/)
