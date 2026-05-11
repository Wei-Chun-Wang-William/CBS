# CBS - Concert Booking System

A secure, scalable web application for managing concert bookings, built with Spring Boot and PostgreSQL.

## Features

- **User Management**: Handle user registration, authentication, and profiles
- **Concert Management**: Create and manage concert events
- **Booking System**: Secure ticket booking with transaction management
- **Security**: JWT-based authentication, SSL/TLS encryption, and encrypted properties
- **Monitoring**: Spring Boot Actuator with Prometheus metrics
- **Logging**: Structured JSON logging with Log4j2
- **Database**: PostgreSQL with JPA/Hibernate ORM
- **Containerization**: Docker support for easy deployment
- **Kubernetes**: Ready for container orchestration

## Technology Stack

- **Backend**: Spring Boot 3.5.13
- **Java Version**: 17
- **Database**: PostgreSQL
- **Security**: Spring Security, JWT, Jasypt encryption
- **Web Server**: Undertow
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Logging**: Log4j2 with JSON layout
- **Container**: Docker
- **Orchestration**: Kubernetes

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Docker (for containerized deployment)
- Kubernetes cluster (for production deployment)

## Getting Started

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cbs
   ```

2. **Database Setup**
   - Install PostgreSQL
   - Create database: `con`
   - Update connection details in `src/main/resources/application.yml` or environment-specific configs

3. **Configure Environment**
   - Copy `src/env/dev/application.yml` to `src/main/resources/` if needed
   - Ensure `salt.properties` contains encryption keys
   - Place `server.jks` keystore in `src/main/resources/`

4. **Build the Application**
   ```bash
   ./mvnw clean install
   ```

5. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

   The application will start on:
   - HTTPS: https://localhost:8443
   - Management: http://localhost:8081

### Docker Deployment

1. **Build Docker Image**
   ```bash
   docker build -t cbs:latest .
   ```

2. **Run with Docker**
   ```bash
   docker run -p 8443:8443 -p 8081:8081 cbs:latest
   ```

### Kubernetes Deployment

1. **Apply Kubernetes manifests**
   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f kind-config.yaml
   ```

## Configuration

The application supports multiple environments:

- **dev**: Development environment
- **sit**: System Integration Testing
- **prod**: Production environment

Configuration files are located in `src/env/{env}/`.

Key configurations:
- Database connection (encrypted)
- SSL keystore (encrypted password)
- Logging levels
- Thread pool settings

## API Endpoints

Base URL: `https://localhost:8443/cbsController`

### Authentication
- `POST /login` - User login
- `POST /register` - User registration

### Users
- `POST /queryUsers` - Get all users
- `GET /user/{id}` - Get user by ID

### Concerts
- `GET /concerts` - List concerts
- `POST /concerts` - Create concert
- `PUT /concerts/{id}` - Update concert

### Bookings
- `POST /book` - Create booking
- `GET /bookings/{userId}` - Get user bookings

## Security

- All endpoints are secured with JWT tokens
- Sensitive data is encrypted using Jasypt
- SSL/TLS enabled for all communications
- CORS configured for web clients

## Monitoring

Access actuator endpoints at `http://localhost:8081`:

- `/actuator/health` - Application health
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application info
- `/actuator/prometheus` - Prometheus metrics

## Logging

Logs are written to:
- `logs/app-json.log` - JSON formatted application logs
- `logs/error.log` - Error logs

Log files are rotated and compressed daily.

## Testing

Run tests with:
```bash
./mvnw test
```

## Building for Production

```bash
./mvnw clean package -DskipTests
```

The JAR file will be created in `target/project-CBS.jar`

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify PostgreSQL is running
   - Check credentials in `application.yml`
   - Ensure database `con` exists

2. **SSL Certificate Issues**
   - Verify `server.jks` file exists
   - Check encrypted password in config

3. **Encryption Errors**
   - Ensure `salt.properties` has correct keys
   - Verify Jasypt configuration

4. **Port Conflicts**
   - Change ports in `application.yml` if 8443/8081 are in use

### JPA/Hibernate Issues

If you encounter errors like "Row was updated or deleted by another transaction":
- Check transaction management in service layer
- Ensure proper `@Transactional` annotations
- Verify optimistic locking configuration
- Check for concurrent modifications

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please contact the development team or create an issue in the repository.
