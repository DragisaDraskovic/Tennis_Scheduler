FROM maven:3.8.5-openjdk-17-slim AS tennisBackendBuild
WORKDIR /root/src/backend
COPY . .
RUN mvn package  -DskipTests

FROM openjdk:17 AS tennisBackendRuntime
WORKDIR /app
COPY --from=tennisBackendBuild /root/src/backend/target/intern-exe.jar ./
EXPOSE 8082
CMD java -jar intern-exe.jar
