## Requirements

01) Java 21
02) Maven 3.X
03) Spring Boot 3.X
04) Spring Data JPA
05) Spring Web
06) Spring security
07) PostgreSQL Database

## Project setup

01) Clone the project

    	git clone https://github.com/awokou/api-ebank.git

02) Clean and build the project using maven

    	- mvn clean install
        - mvn spring-boot:run

03) Set up database configurations in application.yaml file

    	spring:
            datasource:
               url: Database url
    	       username = Database username
    	       password = Database password
