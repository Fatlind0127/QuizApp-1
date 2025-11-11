# 🤝 Contributing to Quiz App

## For Teammates - Getting Started

### Prerequisites
- Java 21 or higher
- Maven (included via Maven Wrapper)
- MySQL (via XAMPP or standalone)
- Git

### 1. Clone the Repository

```bash
git clone <repository-url>
cd QuizApp
```

### 2. Setup MySQL Database

1. Start MySQL (via XAMPP or standalone)
2. Open phpMyAdmin: `http://localhost/phpmyadmin`
3. Create database:
   ```sql
   CREATE DATABASE quizapp;
   ```
4. That's it! Tables will be created automatically by Spring Boot

### 3. Configure Database (If Needed)

If your MySQL credentials are different, update `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Run the Application

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### 5. Access the Application

- Open browser: `http://localhost:8080`
- Login with default Supervisor account:
  - Username: `SupervisorAdmin`
  - Password: `1234`

---

## 🔄 Development Workflow

### Working on New Features

1. **Create a new branch:**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**

3. **Test locally:**
   ```bash
   mvnw.cmd clean test
   mvnw.cmd spring-boot:run
   ```

4. **Commit your changes:**
   ```bash
   git add .
   git commit -m "Description of your changes"
   ```

5. **Push to GitHub:**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** on GitHub

### Code Style Guidelines

- Use meaningful variable and method names
- Add comments for complex logic
- Follow Java naming conventions:
  - Classes: `PascalCase`
  - Methods/Variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
- Keep methods focused and small

### Project Structure

```
QuizApp/
├── src/main/java/com/quizapp/quizapp/
│   ├── auth/           # Authentication & Authorization
│   ├── user/           # User Management
│   ├── supervisor/     # Supervisor Features
│   ├── admin/          # Admin Features
│   ├── student/        # Student Features
│   ├── domain/         # Quiz Domain Models
│   ├── repository/     # Database Repositories
│   └── config/         # Configuration Classes
├── src/main/resources/
│   ├── templates/      # HTML Templates (Thymeleaf)
│   └── application.properties
└── src/test/           # Unit Tests
```

---

## 🐛 Common Issues & Solutions

### Port 8080 Already in Use
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

### Database Connection Errors
- Ensure MySQL is running
- Verify database `quizapp` exists
- Check credentials in `application.properties`

### Maven Build Errors
```bash
# Clean and rebuild
mvnw.cmd clean install
```

---

## 📝 Adding New Features

### Adding a New Entity
1. Create entity class in `domain/` package
2. Create repository in `repository/` package
3. Create controller in appropriate package
4. Add HTML template in `templates/`

### Example: Adding a new field to User
1. Modify `User.java` entity
2. Spring Boot will auto-update the database schema
3. Update controllers to handle new field
4. Update HTML forms

---

## ✅ Before Submitting a Pull Request

- [ ] Code compiles without errors
- [ ] Application starts successfully
- [ ] Tested all new features manually
- [ ] No sensitive data (passwords, API keys) in code
- [ ] Updated README if needed
- [ ] Meaningful commit messages

---

## 🔐 Security Notes

- **Never commit** real passwords or API keys
- The default SupervisorAdmin password (1234) should be changed in production
- Consider implementing password hashing (BCrypt) for production use

---

## 🆘 Need Help?

- Check the main [README.md](README.md)
- Review existing code for examples
- Ask team members
- Check Spring Boot documentation: https://spring.io/projects/spring-boot

---

## 📚 Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Git Workflow Guide](https://guides.github.com/introduction/flow/)

---

**Happy Coding! 🚀**

