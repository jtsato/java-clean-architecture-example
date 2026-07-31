# ==========================================
# 1. Runtime stage
# ==========================================
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# ==========================================
# 2. Build stage
# ==========================================
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /source

# Build the configuration module together with all local dependencies.
COPY . .
RUN mvn -B -DskipTests -pl configuration -am package

# ==========================================
# 3. Production stage
# ==========================================
FROM runtime AS final

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+UseG1GC" \
    PORT=8080

# Copy the executable JAR generated in the build stage
COPY --from=build /source/configuration/target/bookstore-starter.jar app.jar

EXPOSE 8080

# Create a non-root user with minimal permissions
RUN addgroup -g 2000 bookstore && \
    adduser -u 1000 -G bookstore -s /bin/sh -D bookstore && \
    chown -R 1000:2000 /app

USER 1000:2000

# Actuator is provided by the REST module.
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
