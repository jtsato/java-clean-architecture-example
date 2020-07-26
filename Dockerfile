FROM adoptopenjdk/openjdk11:alpine-jre
RUN addgroup -g 1000 -S spring && adduser -u 1000 -S spring -G spring

USER spring:spring

ARG JAR_FILE=configuration/target/bookstore-starter.jar
COPY ${JAR_FILE} docker-bookstore-starter.jar

ENTRYPOINT ["java", "-jar","docker-bookstore-starter.jar","io.github.jtsato.bookstore.BookstoreApplication"]
