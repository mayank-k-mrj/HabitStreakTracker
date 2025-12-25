# ---------- Build Stage ----------
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven project files (if using Maven Wrapper)
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

# Pass environment variables into the container
ENV MYSQLHOST=$MYSQLHOST
ENV MYSQLPORT=$MYSQLPORT
ENV MYSQLDATABASE=$MYSQLDATABASE
ENV MYSQLUSER=$MYSQLUSER
ENV MYSQLPASSWORD=$MYSQLPASSWORD

ENV GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID
ENV GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET

ENV PORT=$PORT


# Copy jar from build stage
COPY --from=build /app/target/habit_streak_tracker-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_DATASOURCE_URL="jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}"
ENV SPRING_DATASOURCE_USERNAME="${MYSQLUSER}"
ENV SPRING_DATASOURCE_PASSWORD="${MYSQLPASSWORD}"
ENV SERVER_PORT="${PORT}"

EXPOSE 8080

# Expose App Port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]