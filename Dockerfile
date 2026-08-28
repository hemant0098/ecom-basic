ARG MODULE
FROM maven:3.9-eclipse-temurin-25 AS build
ARG MODULE
WORKDIR /workspace
COPY pom.xml .
COPY ${MODULE}/pom.xml ${MODULE}/pom.xml
RUN mvn -pl ${MODULE} -am dependency:go-offline -q
COPY ${MODULE}/src ${MODULE}/src
RUN mvn -pl ${MODULE} -am package -DskipTests -q
FROM eclipse-temurin:25-jre
ARG MODULE
WORKDIR /app
COPY --from=build /workspace/${MODULE}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
