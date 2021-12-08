FROM openjdk:11-jdk-slim as build
WORKDIR /app
COPY . /app
# ===> HERE THE PROBLEM
RUN /bin/sh -c "./mvnw clean package -Dmaven.test.skip"
From openjdk:11-jdk-slim
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
