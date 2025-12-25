# ---------- Build Stage ----------
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven project files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q dependency:go-offline

# Copy source code
COPY src ./src
# Build the jar
RUN ./mvnw -q clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/habit_streak_tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose App Port
EXPOSE 8080

# Run Spring Boot app using SHELL execution to expand variables
# This bypasses application.properties and feeds the Render variables directly to Spring
ENTRYPOINT ["/bin/sh", "-c", "java -jar app.jar \
  --spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE} \
  --spring.datasource.username=${MYSQLUSER} \
  --spring.datasource.password=${MYSQLPASSWORD} \
  --spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
  --server.port=${PORT}"]