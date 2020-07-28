# TinyUrl
Done Test Quiz

### Used Technology

* Java8
* Spring Boot
* Groovy Spook (For the unit testing)
* JUnit (For the integration testing)
* maven
* Docker
* Docker Compose
* Cassandra

### Setup Requirements build and run 
* Java8 or higher 
* Docker and Docker compose 
* Maven 

### Setup and build 

1. Clone the Git Repository 

2. In the Directory **TinyUrl** run the command

   ``
    mvn clean package docker:build
   ``
3. To run: 
    ``
    docker-compose up 
    ``
4. To creat tiny url path:

    ``
    curl -v  -X POST -d 'https://www.bbc.com/news/av/uk-england-merseyside-46802068/students-journey-5500-miles-to-thank-korean-war-vets' http://localhost:8080/api/v1/
    `` 
5. To access the  shorted url, use  the path (PATH) replied from the previous request  
    ``
    curl -v -X GET http://localhost:8080/api/v1/PATH
    ``
   
    ``
    curl -v -X GET http://localhost:8080/api/v1/LTM2MTcyMDExMjM1NDg4NTIzOTE=
    ``


***Just take in account that time to live of the record is 6000 second in cassandra***


6. To see the origin URL just make the request 

    ``
    curl -v  -X GET http://localhost:8080/api/v1/origin_url/PATH
    ``

