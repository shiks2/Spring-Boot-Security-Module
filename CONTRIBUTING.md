# 🤝 Contributing to Spring Boot Security Module

First off, thank you for considering contributing to Spring Boot Security Module! 🎉

It's people like you that make this project such a great tool for the community.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Pull Request Process](#pull-request-process)
- [Style Guidelines](#style-guidelines)
- [Commit Messages](#commit-messages)
- [Issue Templates](#issue-templates)

## 📜 Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

### Our Pledge
- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Gracefully accept constructive criticism
- Focus on what is best for the community
- Show empathy towards other community members

## 🚀 How Can I Contribute?

### 🐛 Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates.

**When creating a bug report, please include:**
- **Clear title** - Use a clear and descriptive title
- **Description** - Detailed description of the issue
- **Steps to reproduce** - Step-by-step instructions
- **Expected behavior** - What you expected to happen
- **Actual behavior** - What actually happened
- **Environment** - OS, Java version, Spring Boot version
- **Logs** - Any relevant error messages or logs

### 💡 Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion:

- **Use a clear title** - Describe the enhancement in the title
- **Provide detailed description** - Explain why this enhancement would be useful
- **Provide examples** - Include code examples if applicable
- **Consider alternatives** - Describe alternatives you've considered

### 🔧 Code Contributions

1. **Fork the repository**
2. **Create a feature branch**
3. **Make your changes**
4. **Add tests** for your changes
5. **Ensure all tests pass**
6. **Update documentation** if needed
7. **Submit a pull request**

## 🛠️ Development Setup

### Prerequisites
- Java 17 or later
- Maven 3.8+
- MongoDB 6.0+
- Git

### Setup Instructions

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/Spring-Boot-Security-Module.git
   cd Spring-Boot-Security-Module
   ```

2. **Set up MongoDB**
   ```bash
   # Using Docker
   docker run --name dev-mongodb -d -p 27017:27017 mongo:latest
   
   # Or start local MongoDB
   mongod --config /usr/local/etc/mongod.conf
   ```

3. **Install dependencies and run tests**
   ```bash
   ./mvnw clean install
   ./mvnw test
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

### Project Structure
```
src/
├── main/java/com/example/backend/demo_login/
│   ├── Auth/                 # Authentication DTOs and responses
│   ├── Config/               # Security and configuration classes
│   ├── Service/              # Business logic services
│   ├── User/                 # User entity and repository
│   └── Utilities/            # Utility classes
├── main/resources/           # Configuration files
└── test/java/                # Test classes
```

## 🔄 Pull Request Process

### Before Submitting

1. **Check existing PRs** - Make sure no one else is working on the same thing
2. **Create an issue** - For significant changes, create an issue first for discussion
3. **Follow coding standards** - Ensure your code follows the project standards
4. **Write tests** - Add or update tests for your changes
5. **Update documentation** - Update README or other docs if needed

### PR Requirements

✅ **Code Quality**
- [ ] Code follows the project's coding standards
- [ ] All new code has appropriate tests
- [ ] All tests pass locally
- [ ] Code is well-documented with JavaDoc

✅ **Security**
- [ ] No security vulnerabilities introduced
- [ ] Sensitive data is not exposed
- [ ] Authentication/authorization logic is secure

✅ **Performance**
- [ ] No significant performance regressions
- [ ] Database queries are optimized
- [ ] Memory usage is reasonable

✅ **Documentation**
- [ ] README updated if needed
- [ ] API documentation updated
- [ ] Code comments added where necessary

### PR Template
When creating a PR, please include:

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## How Has This Been Tested?
Describe the tests you ran and how to reproduce them.

## Checklist:
- [ ] My code follows the style guidelines of this project
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
```

## 📝 Style Guidelines

### Java Code Style

**General Guidelines:**
- Use Java 17+ features where appropriate
- Follow Google Java Style Guide as base
- Use meaningful variable and method names
- Keep methods small and focused (max 50 lines)
- Use proper exception handling

**Naming Conventions:**
```java
// Classes: PascalCase
public class UserService

// Methods and variables: camelCase  
public void validateUser()
private String userEmail

// Constants: UPPER_SNAKE_CASE
private static final String JWT_SECRET_KEY

// Packages: lowercase
com.example.backend.demo_login.service
```

**Code Formatting:**
```java
// Use 4 spaces for indentation, not tabs
// Line length: 100 characters maximum
// Braces on same line

public class Example {
    
    public void method() {
        if (condition) {
            // do something
        }
    }
}
```

### Spring Boot Best Practices

**Annotations:**
```java
// Use constructor injection
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}

// Use specific mappings
@PostMapping("/api/auth/login")
@GetMapping("/api/auth/users/{id}")
```

**Configuration:**
```java
// Use @ConfigurationProperties for complex configs
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private long expiration;
}
```

### Testing Standards

**Unit Tests:**
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Should register user successfully")
    void shouldRegisterUserSuccessfully() {
        // Given
        RegisterRequest request = createValidRegisterRequest();
        
        // When
        AuthResponse response = userService.register(request);
        
        // Then
        assertThat(response.getToken()).isNotNull();
        verify(userRepository).save(any(Users.class));
    }
}
```

**Integration Tests:**
```java
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserControllerIntegrationTest {
    
    @Container
    static MongoDBContainer mongodb = new MongoDBContainer("mongo:7.0");
    
    @Test
    void shouldRegisterUser() {
        // Test with real database
    }
}
```

## 💬 Commit Messages

### Format
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- **feat**: New feature
- **fix**: Bug fix  
- **docs**: Documentation changes
- **style**: Code style changes (formatting, etc.)
- **refactor**: Code refactoring
- **test**: Adding/updating tests
- **chore**: Maintenance tasks

### Examples
```bash
feat(auth): add JWT token refresh functionality

- Implement token refresh endpoint
- Add refresh token validation
- Update authentication service
- Add tests for refresh functionality

Closes #123
```

```bash
fix(validation): resolve password validation regex issue

The password validation was not accepting special characters
correctly due to regex escaping issue.

Fixes #456
```

## 🐛 Issue Templates

### Bug Report Template
```markdown
**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected behavior**
A clear and concise description of what you expected to happen.

**Environment:**
 - OS: [e.g. macOS, Ubuntu]
 - Java Version: [e.g. 17]
 - Spring Boot Version: [e.g. 3.2.0]
 - MongoDB Version: [e.g. 7.0]

**Additional context**
Add any other context about the problem here.
```

### Feature Request Template
```markdown
**Is your feature request related to a problem? Please describe.**
A clear and concise description of what the problem is.

**Describe the solution you'd like**
A clear and concise description of what you want to happen.

**Describe alternatives you've considered**
A clear and concise description of any alternative solutions.

**Additional context**
Add any other context or screenshots about the feature request here.
```

## 📞 Getting Help

If you need help or have questions:

1. **Check existing issues** - Your question might already be answered
2. **Create a discussion** - Use GitHub Discussions for questions
3. **Join our community** - Links to Discord/Slack if available
4. **Email us** - support@example.com for private questions

## 🎉 Recognition

Contributors are recognized in several ways:
- **Contributors list** in README.md
- **GitHub contributions graph**
- **Special mentions** in release notes
- **Contributor of the month** recognition

Thank you for contributing to Spring Boot Security Module! 🙏

---

**Happy coding! 🚀**
