# User Management Service

This project is built with Spring Boot, Spring Security, and JWT for stateless authentication. It provides APIs for user registration, authentication, and management, following best practices for security and modularity. The service can be easily integrated into larger systems, such as social media platforms or enterprise-level applications, to handle user authentication and authorization.

## Key Features

- **JWT Authentication**: The service uses JSON Web Tokens (JWT) for stateless authentication. After successful login, a JWT is issued, which is then used to authenticate further requests.
- **User Registration and Login**: API endpoints for registering new users and logging in to obtain a JWT token.
- **Role-Based Authorization**: Secure endpoints based on user roles (e.g., `ADMIN`, `USER`) using Spring Security.
- **User Management**: Endpoints for user management such as fetching user details, updating user info, and deleting users.
- **Auditing**: The project includes JPA auditing, which automatically tracks changes such as creation and update timestamps.
- **Swagger Documentation**: API documentation is available via Swagger UI, making it easy to explore and test API endpoints.
- **Docker Support**: The project is containerized with a `Dockerfile` to run the service in any Docker environment.
- **Custom Logout Handling**: Implements custom logout functionality, including the ability to update the user's last logout time in the database.
- **PostgreSQL Integration**: Uses PostgreSQL as the database for storing user information.

## Tech Stack

- **Spring Boot**: For building the application.
- **Spring Security**: For handling security, authentication, and authorization.
- **JWT**: JSON Web Token for stateless authentication.
- **PostgreSQL**: Database for user storage.
- **Swagger**: API documentation and testing via Swagger UI.
- **Lombok**: To reduce boilerplate code for POJOs (Plain Old Java Objects).
- **JPA**: For database interaction and persistence.
- **Hibernate**: As the ORM (Object-Relational Mapping) tool.
- **Docker**: For containerization of the service.

## Getting Started

### Prerequisites

- **Java 17+**
- **Maven**
- **PostgreSQL** (You need a running PostgreSQL instance)

### Installation and Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Oskilochka/venom_user_managment_service
   cd user-management-service

2. **Set Up PostgreSQL Database**

Ensure that you have PostgreSQL installed and running on your system. Create a database for this service:
```bash
CREATE DATABASE user_management;
```

3. **Configure the Application Properties**

In src/main/resources/application.yml, configure your database connection settings:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/venom_user_management_db
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_secret
```

4. **Run the Application**

Use Maven to build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

5. **Access the Application**

The service will be running at:
```bash
http://localhost:8080
```

6. **Swagger UI**

To explore and test the available endpoints, navigate to the Swagger UI at:
```bash
http://localhost:8080/swagger-ui/index.html
```

### JWT Authentication

- After registration, use the /api/v1/auth/login endpoint to authenticate and obtain a JWT token.
- Include the token in the Authorization header as a Bearer token when making requests to protected routes.

### Docker
1. **Build Docker Image**

You can build a Docker image for this service using the provided Dockerfile.
```bash
docker build -t user-management-service .
```
2. **Run Docker Container**

```bash
docker run -p 8080:8080 user-management-service
```

### Security
- JWT Expiration: Tokens are configured with an expiration time (e.g., 24 hours). Once expired, the token is no longer valid, and the user will need to log in again.
- Password Encryption: User passwords are stored securely in the database using password hashing with BCrypt.
- Role-based Access: Different levels of access control are enforced based on user roles.

### Contributing

If you'd like to contribute, please fork the repository and make changes as you'd like. Pull requests are welcome!
1. Fork the repo
2. Create a new branch (git checkout -b feature-foo)
3. Make changes and commit (git commit -m 'Added feature foo')
4. Push to the branch (git push origin feature-foo)
5. Create a Pull Request
