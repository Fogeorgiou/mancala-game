FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app/src
COPY src .
WORKDIR /app
COPY pom.xml .
RUN mvn -f /app/pom.xml clean package -DskipTests
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/target/mancala-game-0.0.1-SNAPSHOT.jar"]