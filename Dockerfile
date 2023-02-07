FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/CustomerRewards-0.0.1-SNAPSHOT.jar app.jar

#Setting mongodb connection settings.
#Not a good practice to hardcode these config values along with password.
#Generally these are inject in to the container during deploy time by pulling them from secrets maanger.
#Replace these values with proper values when you are building the docker file.
ENV MONGO_USER_NAME=****
ENV MONGO_PASSWORD=****
ENV MONGO_HOST=****
ENV MONGO_DB=****

ENTRYPOINT ["java", "-jar", "app.jar"]