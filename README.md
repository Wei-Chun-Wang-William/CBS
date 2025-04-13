# CBS Project

## Overview
The CBS project is a Spring Boot-based application designed to provide robust and scalable prototype solutions for enterprise-level requirements. It leverages Java 17, Maven, and Docker for efficient development, deployment, and management.

## Features
- **Spring Boot Framework**: Simplified configuration and development.
- **Java 17**: Modern language features and performance improvements.
- **Maven**: Dependency management and build automation.
- **Docker Integration**: Containerized deployment for consistent environments.
- **PostgreSQL Database**: Reliable and scalable data storage.
- **Jasypt Encryption**: Secure sensitive information in configuration files.
- **Actuator Server**: Monitoring and management endpoints for the application.
- **ELK Stack Integration**: Centralized logging and monitoring capabilities.
- **Customizable Profiles**: Easily switch between different environments (dev, sit, prod) using Maven profiles.
- **JPA**: Java Persistence API for database interactions.

## Prerequisites
- **Java 17**: Ensure Java 17 is installed on your system.
- **Maven**: Install Maven for building the project.
- **Docker**: Install Docker for containerized deployment.
- Make sure you understand all the settings in the `pom.xml` file.

## Getting Started

### Customize the application.yml

#### replace the spring DataSource in src/main/env/${env}/application.yml
```yaml
spring:
  datasource:
    url: your_database_url
    username: your_username
    password: your_decrypted_password or ENC(your_encrypted_password)
    driver-class-name: your_driver_class_name
    hikari:
      connectionTimeout: your_connection_timeout
      maxLifetime: your_max_lifetime
      maximum-pool-size: your_maximum_pool_size
  jpa:
    properties:
      hibernate:
        default_schema: your_schema
```

### Optional

#### using https 
```yaml
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:server.jks
    key-store-password: your_decrypted_password or ENC(your_encrypted_password)
    keyStoreType: your_keystore_type
```

Then generate the `server.jks` file in the src/env/${env} directory.

```
Using keytool command to generate a keystore file.
```

#### Use jasypt to encrypt the password
If you want to encrypt your password, make sure the password you want to encrypt looked like this in your application.yml:

```yaml
ENC(your_encrypted_password)
```

Then add the jasypt `salt.properties` file in the src/env/${env} directory.

```properties
jasypt.encryptor.password = your_password
jasypt.encryptor.algorithm = your_encryptor.algorithm
```

### Test the jasypt password
Open the `JasyptEncrytionTest.java` to test the password.

# Configure different environments

## The profile parameters are in the pom.xml
```xml
<profiles>
    <profile>
        <id>dev</id>
    </profile>
<profiles>
```
Start the application server with -P{env} command.


## Start application server in dev mode
```bash
clean package -Pdev -U spring-boot:run -f pom.xml
```

## Build application server in SIT (docker) mode
```bash
clean package -Psit -U -f pom.xml
```

After running the command you will get a .jar file in the target directory.
Then update the docker-compose.yml file with your jar file name.
```yaml
version: "3"

services:
  postgresLatest:
    image: postgres:latest
    container_name: your_postgres_container_name
    volumes:
      - /your_postgres_data_path/data:/var/lib/postgresql/data
    expose:
      - 5432
    ports:
      - 5432:5432
    networks:
      my_custom_network:
        ipv4_address: 172.?.0.2
  app:
    image: openjdk:17-oracle
    container_name: project-cbs
    working_dir: /app
    expose:
      - 8443
      - 8081
    ports:
      - "8443:8443"
      - "8081:8081"
    volumes:
      - /your_path_to _target/target/project-CBS.jar:/app/app.jar
    entrypoint: ["java", "-jar", "app.jar"]
    networks:
      my_custom_network:
        ipv4_address: 172.?.0.3


networks:
  my_custom_network:
    driver: bridge
    ipam:
      config:
        - subnet: 172.?.0.0/16
```

Finally, run the following command to start the application server in docker compose:

```bash
docker-compose up -d
```

The application server and database will be started on 172.?.0.0/16 docker network.


## About Actuator server
The CBS project includes an Actuator server that provides various endpoints for monitoring and managing the application.
The Actuator server is configured to run on port 8081 by default.

## About ELK
The application server is configured to send logs to an ELK stack for centralized logging and monitoring.
The settings are in the `log4j2.xml` file.
The logs will send to  http://localhost:5044 by http protocol.


