# ---------- Build Stage ----------
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven project files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q dependency:go-offline

# Copy source code and build .jar
COPY src ./src
RUN ./mvnw -q clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# (REMOVED THE ENV BLOCK HERE)
# Spring Boot will automatically pick up the variables
# injected by Render at runtime.

# Copy jar from build stage
COPY --from=build /app/target/habit_streak_tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose App Port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]