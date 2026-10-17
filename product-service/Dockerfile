FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY . .
RUN mvn -pl product-service -am clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/product-service/target/*.jar app.jar
EXPOSE 8083
CMD [ "java", "-jar", "app.jar" ]