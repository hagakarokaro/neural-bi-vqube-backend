FROM openjdk:11-jdk-slim
EXPOSE 8888
WORKDIR /
ADD target/visual_query_builder-0.0.1-SNAPSHOT.jar visual_query_builder-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/visual_query_builder-0.0.1-SNAPSHOT.jar"]