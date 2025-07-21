# 🔐 Spring Boot Security Module

A comprehensive, production-ready authentication and authorization system built with Spring Boot, Spring Security, and MongoDB. This module provides JWT-based authentication with robust security practices and comprehensive error handling.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0+-brightgreen.svg)](https://www.mongodb.com/)
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
- **MongoDB Integration** - NoSQL database with Spring Data MongoDB
- **Indexed Collections** - Optimized database queries with proper indexing
- **Data Auditing** - Automatic timestamp management
- **Connection Pooling** - Efficient database connection management

### 🔧 Development & Production Ready
- **Environment Profiles** - Separate configurations for dev/prod
- **Comprehensive Logging** - Structured logging with different levels
- **Error Handling** - Global exception handling with meaningful responses
- **API Documentation Ready** - Structure supports Swagger/OpenAPI integration
- **Health Checks** - Built-in health monitoring endpoints

## 🚀 Quick Start

### Prerequisites
- ☕ **Java 17+**
- 🍃 **MongoDB 6.0+**
- 🔧 **Maven 3.8+**
- 🌐 **Git**

### 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shiks2/Spring-Boot-Security-Module.git
   cd Spring-Boot-Security-Module
   ```

2. **Start MongoDB**
   ```bash
   # Using Docker
   docker run --name mongodb -d -p 27017:27017 mongo:latest
   
   # Or start your local MongoDB service
   mongod --config /usr/local/etc/mongod.conf
   ```

3. **Configure Environment Variables**
   ```bash
   # Create a .env file or set environment variables
   export JWT_SECRET="your-very-secure-256-bit-secret-key-here"
   export MONGODB_DATABASE="backendProject"
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

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `JWT_SECRET` | Secret key for JWT signing (min 256 bits) | Generated | ✅ |
| `JWT_EXPIRATION` | Token expiration time in milliseconds | `3600000` | ❌ |
| `MONGODB_HOST` | MongoDB host | `localhost` | ❌ |
| `MONGODB_PORT` | MongoDB port | `27017` | ❌ |
| `MONGODB_DATABASE` | Database name | `backendProject` | ❌ |
| `MONGODB_USERNAME` | Database username | - | ❌ |
| `MONGODB_PASSWORD` | Database password | - | ❌ |
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

### Users Collection
```javascript
{
  "_id": ObjectId("..."),
  "userId": "johndoe:john@example.com",
  "username": "johndoe",           // Indexed, Unique
  "email": "john@example.com",     // Indexed, Unique  
  "password": "$2a$12$...",        // BCrypt hashed
  "roles": ["USER"],
  "profilePicUrl": null,
  "createdAt": ISODate("..."),
  "updatedAt": ISODate("..."),
  "auditDateTime": {...},
  "createdBy": null,
  "updatedBy": null,
  "deletedBy": null
}
```

### Indexes
```javascript
// Compound indexes for uniqueness
db.User.createIndex({"username": 1}, {unique: true})
db.User.createIndex({"email": 1}, {unique: true})

// Query optimization indexes  
db.User.createIndex({"username": 1, "email": 1})
```

## 🛠️ Development

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

1. **Create Dockerfile**
   ```dockerfile
   FROM openjdk:17-jdk-slim
   
   WORKDIR /app
   COPY target/demo-login-1.0.0.jar app.jar
   
   EXPOSE 9091
   
   ENV SPRING_PROFILES_ACTIVE=prod
   ENV JWT_SECRET=your-production-secret-key
   
   ENTRYPOINT ["java", "-jar", "app.jar"]
   ```

2. **Build and run**
   ```bash
   # Build JAR
   ./mvnw clean package
   
   # Build Docker image
   docker build -t spring-security-module .
   
   # Run with Docker Compose
   docker-compose up -d
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
