# Top-level Dockerfile so platforms (Render) that expect Dockerfile at repo root
# can build the application. This performs a multi-stage build and builds the
# Maven project located in the `demo` subfolder.

FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /workspace

# copy only necessary files from demo to leverage Docker layer caching
COPY demo/pom.xml demo/
COPY demo/src demo/src

WORKDIR /workspace/demo
RUN mvn -f pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

# allow override of the jar name via build arg
ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar
ENV JAR_FILE=${JAR_FILE}

WORKDIR /app
COPY --from=build /workspace/demo/target/${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
