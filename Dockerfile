FROM maven:4.0.0-rc-4-amazoncorretto-21 AS build
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn package -DskipTests

FROM openjdk:21-jdk
ARG JAR_FILE=/build/target/*.jar
COPY --from=build $JAR_FILE /opt/Bank_REST_Test_Task/test_task.jar
ENTRYPOINT ["java","-jar","/opt/Bank_REST_Test_Task/test_task.jar"]