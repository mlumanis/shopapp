FROM openjdk:8
COPY /target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "shop-0.0.1-SNAPSHOT.jar"]