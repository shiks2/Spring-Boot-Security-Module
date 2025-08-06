# 🔐 Spring Boot Security Module

A comprehensive, production-ready authentication and authorization system built with Spring Boot, Spring Security, and PostgreSQL. This module provides JWT-based authentication with robust security practices and comprehensive error handling.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Latest-blue.svg)](https://jwt.io/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 🌟 Features

### 🔒 Authentication & Authorization
- **JWT Token Authentication** - Stateless authentication with configurable expiration
- **User Registration & Login** - Comprehensive validation and error handling  
- **Password Encryption** - BCrypt with strength 12 for secure password storage
- **Role-Based Access Control** - Flexible role management system
- **Token Validation** - Robust JWT token validation and error handling

### 🛡️ Security Features
- **Input Validation** - Comprehensive validation for all user inputs
- **Password Strength Requirements** - Enforced strong password policies
- **CORS Configuration** - Configurable Cross-Origin Resource Sharing
- **Security Headers** - Comprehensive security headers implementation
- **Rate Limiting Ready** - Architecture supports rate limiting implementation

### 🗄️ Database & Storage
- **PostgreSQL Integration** - Robust relational database with Spring Data JPA
- **Database Migrations** - Flyway for version-controlled schema management
- **Indexed Tables** - Optimized database queries with proper indexing
- **Data Auditing** - Automatic timestamp management with JPA auditing
- **Connection Pooling** - HikariCP for efficient database connection management

### 🔧 Development & Production Ready
- **Environment Profiles** - Separate configurations for dev/prod
- **Comprehensive Logging** - Structured logging with different levels
- **Error Handling** - Global exception handling with meaningful responses
- **API Documentation Ready** - Structure supports Swagger/OpenAPI integration
- **Health Checks** - Built-in health monitoring endpoints

## 🚀 Quick Start

### Prerequisites
- ☕ **Java 17+**
- 🐘 **PostgreSQL 15+**
- 🔧 **Maven 3.8+**
- 🌐 **Git**

### 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shiks2/Spring-Boot-Security-Module.git
   cd Spring-Boot-Security-Module
   ```

2. **Start PostgreSQL**
   ```bash
   # Using Docker
   docker run --name postgresql -d -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=backend_project postgres:15
   
   # Or start your local PostgreSQL service
   sudo systemctl start postgresql
   ```

3. **Configure Environment Variables**
   ```bash
   # Create a .env file or set environment variables
   export JWT_SECRET="your-very-secure-256-bit-secret-key-here"
   export DB_PASSWORD="your-database-password"
   export SPRING_PROFILES_ACTIVE="dev"
   ```

4. **Build and Run**
   ```bash
   # Build the project
   ./mvnw clean compile
   
   # Run the application
   ./mvnw spring-boot:run
   
   # Or run with specific profile
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
   ```

The application will start on `http://localhost:9091`

### 🧪 Verify Installation
```bash
curl http://localhost:9091/api/auth/health
```

Expected response:
```json
{
  "success": true,
  "data": "Service is running",
  "message": "Health check passed",
  "timestamp": "2025-01-21T12:00:00Z"
}
```

## 📖 API Documentation

### Authentication Endpoints

#### 🔐 User Registration
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com", 
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": "64f8b2c1e4b0a8d3c5e7f9a1",
      "username": "johndoe",
      "email": "john@example.com",
      "roles": ["USER"]
    }
  },
  "message": "User registered successfully",
  "timestamp": "2025-01-21T12:00:00Z"
}
```

#### 🔑 User Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "usernameOrEmail": "johndoe",
  "password": "SecurePass123!"
}
```

**Response:** Same as registration response

#### 👤 Get Current User
**GET** `/api/auth/me`
**Authorization:** `Bearer <jwt-token>`

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "64f8b2c1e4b0a8d3c5e7f9a1",
    "username": "johndoe",
    "email": "john@example.com", 
    "roles": ["USER"],
    "profilePicUrl": null
  },
  "message": "User details retrieved successfully"
}
```

#### 🚪 Logout
**POST** `/api/auth/logout`
**Authorization:** `Bearer <jwt-token>`

**Response:**
```json
{
  "success": true,
  "data": null,
  "message": "Logout successful"
}
```

### Health & Monitoring

#### ❤️ Health Check
**GET** `/api/auth/health`

## 🗄️ Database Configuration

#### Development Setup
```properties
# PostgreSQL Configuration for Development
spring.datasource.url=jdbc:postgresql://localhost:5432/backend_project_dev
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### Production Setup
```properties
# PostgreSQL Configuration for Production
spring.datasource.url=jdbc:postgresql://localhost:5432/backend_project
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `JWT_SECRET` | Secret key for JWT signing (min 256 bits) | Generated | ✅ |
| `JWT_EXPIRATION` | Token expiration time in milliseconds | `3600000` | ❌ |
| `DB_USERNAME` | PostgreSQL username | `postgres` | ❌ |
| `DB_PASSWORD` | PostgreSQL password | `password` | ✅ |
| `SPRING_DATASOURCE_URL` | Database connection URL | See config | ❌ |
| `SERVER_PORT` | Application port | `9091` | ❌ |

### 🏗️ Application Profiles

#### Development Profile (`dev`)
```properties
# Activate with: --spring.profiles.active=dev
spring.data.mongodb.database=backendProject_dev
jwt.expiration=86400000  # 24 hours
logging.level.com.example.backend.demo_login=DEBUG
server.error.include-stacktrace=always
```

#### Production Profile (`prod`) 
```properties
# Activate with: --spring.profiles.active=prod
spring.data.mongodb.database=backendProject
jwt.expiration=3600000   # 1 hour
logging.level.com.example.backend.demo_login=WARN
server.error.include-stacktrace=never
```

## 🔒 Security Features

### Password Requirements
- ✅ **Minimum 8 characters**
- ✅ **At least one uppercase letter**
- ✅ **At least one lowercase letter**
- ✅ **At least one digit**
- ✅ **At least one special character (@$!%*?&)**
- ✅ **Maximum 100 characters**

### Username Requirements
- ✅ **3-20 characters**
- ✅ **Alphanumeric and underscores only**
- ✅ **Case insensitive (stored in lowercase)**

### JWT Security
- 🔐 **HMAC SHA-256 signing**
- ⏰ **Configurable expiration**
- 🛡️ **Token validation on each request**
- 🚫 **Invalid token rejection**

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_pic_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);
```

### User Roles Table
```sql
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### Shops Table
```sql
CREATE TABLE shops (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    gstin VARCHAR(50),
    phone_number VARCHAR(20),
    address TEXT,
    user_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);
```

### Routines Table
```sql
CREATE TABLE routines (
    id BIGSERIAL PRIMARY KEY,
    routine_id VARCHAR(255),
    routine_name VARCHAR(255) NOT NULL,
    description TEXT,
    routine_type VARCHAR(50),
    routine_frequency VARCHAR(50),
    user_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);
```

### Database Indexes
```sql
-- Performance optimization indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_shops_user_id ON shops(user_id);
CREATE INDEX idx_routines_user_id ON routines(user_id);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
```

## 🔧 Development

### Project Structure
```
src/
├── main/
│   ├── java/com/example/backend/demo_login/
│   │   ├── Auth/                    # Authentication DTOs & Responses
│   │   │   ├── Exception/           # Custom exceptions
│   │   │   ├── ApiResponse.java
│   │   │   ├── AuthRequest.java
│   │   │   ├── AuthResponse.java
│   │   │   └── RegisterRequest.java
│   │   ├── Component/               # Reusable components
│   │   ├── Config/                  # Security & CORS configuration
│   │   │   ├── CorsConfig.java
│   │   │   ├── JwtFilter.java
│   │   │   └── SecurityConfig.java
│   │   ├── Service/                 # Business logic services
│   │   │   ├── JWTService.java
│   │   │   ├── UserService.java
│   │   │   └── UserServices.java
│   │   ├── User/                    # User entity & repository
│   │   │   ├── UserController.java
│   │   │   ├── UserRepo.java
│   │   │   └── Users.java
│   │   ├── Utilities/               # Utility classes
│   │   │   └── ValidationUtils.java
│   │   └── DemoLoginApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/                           # Unit & integration tests
```

### 🧪 Testing
```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report

# Run specific test class
./mvnw test -Dtest=UserServicesTest
```

### 📝 Code Quality
```bash
# Check code style
./mvnw checkstyle:check

# Static analysis
./mvnw spotbugs:check

# Dependency vulnerability check
./mvnw org.owasp:dependency-check-maven:check
```

## 🚀 Deployment

### 🐳 Docker Deployment

The project includes Docker support with PostgreSQL:

```bash
# Start with Docker Compose
docker-compose up -d

# This will start:
# - PostgreSQL database on port 5432
# - Spring Boot application on port 9091
```

### ☁️ Cloud Deployment

#### AWS ECS / EKS
```yaml
# kubernetes-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-security-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: spring-security-app
  template:
    metadata:
      labels:
        app: spring-security-app
    spec:
      containers:
      - name: app
        image: spring-security-module:latest
        ports:
        - containerPort: 9091
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: JWT_SECRET
          valueFrom:
            secretKeyRef:
              name: app-secrets
              key: jwt-secret
```

## 🔍 Monitoring & Logging

### Health Checks
- **Application Health**: `/api/auth/health`
- **Database Connectivity**: Built-in MongoDB health indicators
- **Custom Metrics**: Ready for Micrometer integration

### Logging Levels
```properties
# Development
logging.level.com.example.backend.demo_login=DEBUG
logging.level.org.springframework.security=DEBUG

# Production  
logging.level.com.example.backend.demo_login=WARN
logging.level.org.springframework.security=WARN
```

### Monitoring Integration
Ready for integration with:
- 📊 **Prometheus** - Metrics collection
- 📈 **Grafana** - Metrics visualization  
- 🔍 **ELK Stack** - Log aggregation
- 🚨 **Alertmanager** - Alert management

## 🐛 Troubleshooting

### Common Issues

#### Connection Issues
```bash
# Check MongoDB connection
mongosh "mongodb://localhost:27017/backendProject"

# Verify application is running
curl http://localhost:9091/api/auth/health
```

#### JWT Issues
```bash
# Invalid token format
# Error: "Invalid token: JWT strings must contain exactly 2 period characters"
# Solution: Ensure Bearer token format: "Bearer <token>"

# Token expired
# Error: "Token has expired"
# Solution: Generate new token via login endpoint
```

#### Validation Errors
```bash
# Weak password
# Error: "Password must be 8-100 characters and contain: uppercase letter, lowercase letter, digit, and special character"
# Solution: Use stronger password meeting all requirements

# Invalid username
# Error: "Username must be 3-20 characters, alphanumeric and underscores only"
# Solution: Use valid username format
```

### 🐛 Debug Mode
```bash
# Run with debug logging
./mvnw spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.example.backend.demo_login=DEBUG

# Enable security debug
./mvnw spring-boot:run -Dspring-boot.run.arguments=--logging.level.org.springframework.security=DEBUG
```

## 🔒 Security Best Practices

### Production Checklist
- ✅ **Use strong, unique JWT secret (256+ bits)**
- ✅ **Enable HTTPS in production**
- ✅ **Set secure CORS configuration**
- ✅ **Use environment variables for secrets**
- ✅ **Enable database authentication**
- ✅ **Implement rate limiting**
- ✅ **Set up proper logging and monitoring**
- ✅ **Regular security updates**

### Security Headers (Recommended)
```java
// Add to SecurityConfig
http.headers(headers -> headers
    .frameOptions().deny()
    .contentTypeOptions().and()
    .httpStrictTransportSecurity(hstsConfig -> hstsConfig
        .maxAgeInSeconds(31536000)
        .includeSubdomains(true))
);
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

### Development Setup
1. **Fork the repository**
2. **Create feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit changes**: `git commit -m 'Add amazing feature'`
4. **Push to branch**: `git push origin feature/amazing-feature`
5. **Open Pull Request**

### Code Standards
- ✅ **Java 17+ features**
- ✅ **Spring Boot best practices**
- ✅ **Comprehensive unit tests**
- ✅ **JavaDoc for public methods**
- ✅ **Consistent code formatting**

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Spring Security Team** - Excellent security framework
- **MongoDB Team** - Robust NoSQL database
- **JWT.io** - JWT implementation standards
- **Spring Boot Community** - Amazing ecosystem

## 📞 Support

- **📧 Email**: support@example.com
- **🐛 Issues**: [GitHub Issues](https://github.com/shiks2/Spring-Boot-Security-Module/issues)
- **💬 Discussions**: [GitHub Discussions](https://github.com/shiks2/Spring-Boot-Security-Module/discussions)
- **📖 Documentation**: [Wiki](https://github.com/shiks2/Spring-Boot-Security-Module/wiki)

---

**⭐ If this project helped you, please give it a star! ⭐**

**📚 Check out our other projects: [GitHub Profile](https://github.com/shiks2)**
