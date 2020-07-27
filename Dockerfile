FROM openjdk:8-jre-alpine
# Make port 8080 available to the world outside this container
EXPOSE 8080

COPY ./target/app.jar /app/


RUN chmod -R ag+w /app


CMD java -jar /app/app.jar
