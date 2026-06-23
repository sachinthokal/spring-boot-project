# STAGE 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# 1. Copy Maven Wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Download dependencies and cache them (Pro-Tip)
RUN ./mvnw dependency:go-offline

# 3. Now copy the main source code
COPY src ./src

# 4. Build the application and skip tests to speed up the build process
RUN ./mvnw clean package -DskipTests

# STAGE 2: Run the application
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 5. Copy the built JAR file from stage 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]