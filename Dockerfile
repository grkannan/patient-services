# ---------- Stage 1: Build ----------
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# Copy only gradle files first (for caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source
COPY src ./src

# Build the application
RUN ./gradlew bootJar --no-daemon

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy generated jar
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]