FROM openjdk:11

WORKDIR /home/baruntravel
COPY api/build/libs/api-1.0.jar ./api-1.0.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "./api-1.0.jar"]