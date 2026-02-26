# build stage: compile and package
FROM maven:3.8.8-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# runtime stage: minimal JRE image
FROM eclipse-temurin:17-jre-alpine

# allow override of the jar name via build arg (useful for multi-module projects)
ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar
ENV JAR_FILE=${JAR_FILE}

WORKDIR /app
COPY --from=build /app/target/${JAR_FILE} app.jar

# default database settings (can be overridden at runtime with environment variables)
ENV JDBC_URL=jdbc:mysql://localhost:3306/TTproject
ENV JDBC_USER=root
ENV JDBC_PASS=Root@1234

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
