FROM openjdk:17-jdk-alpine
ADD target/charity-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD ["java", "-jar", "charity-0.0.1-SNAPSHOT.jar"]
