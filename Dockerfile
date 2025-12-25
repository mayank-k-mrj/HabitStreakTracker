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

# Run Spring Boot app
# Updated to include GOOGLE_CLIENT_ID and GOOGLE_CLIENT_SECRET
ENTRYPOINT ["/bin/sh", "-c", "java -jar app.jar \
  --spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE} \
  --spring.datasource.username=${MYSQLUSER} \
  --spring.datasource.password=${MYSQLPASSWORD} \
  --spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
  --spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID} \
  --spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET} \
  --server.port=${PORT}"]