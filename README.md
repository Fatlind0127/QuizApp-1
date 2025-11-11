# Quiz App - Role-Based Quiz Management System

A comprehensive Spring Boot application for managing quizzes with a three-tier role-based access control system.

## 🚀 Quick Start for Team Members

**New to this project?** Check out **[SETUP.md](SETUP.md)** for a step-by-step setup guide!

It takes only 5 minutes to get the project running on your machine.

## 🎯 Features

### Role-Based Access Control
- **Supervisor Admin** (Super User)
  - Create and manage Admin accounts
  - Create and manage Student accounts
  - Monitor all quizzes, admins, and students
  - Full system oversight

- **Admin**
  - Create and manage Student accounts
  - Create and manage quizzes
  - Monitor students and their quiz results

- **Student**
  - View available quizzes
  - Take quizzes
  - View quiz results

## 🚀 Technology Stack

- **Backend:** Spring Boot 3.5.6
- **Database:** MySQL (via phpMyAdmin)
- **ORM:** Hibernate/JPA
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Java Version:** 21

## 📦 Project Structure

```
QuizApp/
├── src/
│   ├── main/
│   │   ├── java/com/quizapp/quizapp/
│   │   │   ├── auth/                    # Authentication logic
│   │   │   │   ├── AuthController.java
│   │   │   │   └── AuthService.java
│   │   │   ├── user/                    # User management
│   │   │   │   ├── User.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── supervisor/              # Supervisor features
│   │   │   │   └── SupervisorController.java
│   │   │   ├── admin/                   # Admin features
│   │   │   │   ├── Admin.java
│   │   │   │   ├── AdminRepository.java
│   │   │   │   └── AdminController.java
│   │   │   ├── student/                 # Student features
│   │   │   │   ├── Student.java
│   │   │   │   ├── StudentRepository.java
│   │   │   │   └── StudentController.java
│   │   │   ├── domain/                  # Quiz domain models
│   │   │   │   ├── Quiz.java
│   │   │   │   ├── Question.java
│   │   │   │   └── Option.java
│   │   │   ├── repository/              # Quiz repositories
│   │   │   │   ├── QuizRepository.java
│   │   │   │   ├── QuestionRepository.java
│   │   │   │   └── OptionRepository.java
│   │   │   ├── config/                  # Configuration
│   │   │   │   └── DataInitializer.java
│   │   │   └── QuizappApplication.java
│   │   └── resources/
│   │       ├── templates/               # HTML templates
│   │       │   ├── login.html
│   │       │   ├── supervisor-dashboard.html
│   │       │   ├── admin-dashboard.html
│   │       │   └── student-home.html
│   │       └── application.properties
│   └── test/
├── database-schema.sql                  # Database schema
├── pom.xml
└── README.md
```

## 🔧 Setup Instructions

### Prerequisites
- Java 21 or higher
- Maven
- MySQL (via XAMPP or standalone)
- phpMyAdmin (for database management)

### Database Setup

1. **Start MySQL** (via XAMPP or standalone)

2. **Create Database:**
   - Open phpMyAdmin (usually at `http://localhost/phpmyadmin`)
   - Create a new database named `quizapp`
   - Or run:
   ```sql
   CREATE DATABASE quizapp;
   ```

3. **Configure Database Connection:**
   - Open `src/main/resources/application.properties`
   - Verify/Update the database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/quizapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   ```

4. **Database Tables:**
   - Spring Boot will automatically create tables on first run (via `spring.jpa.hibernate.ddl-auto=update`)
   - Alternatively, you can manually run `database-schema.sql` in phpMyAdmin

### Running the Application

1. **Navigate to project directory:**
   ```bash
   cd QuizApp
   ```

2. **Build the project:**
   ```bash
   mvnw clean install
   ```
   Or on Windows:
   ```bash
   mvnw.cmd clean install
   ```

3. **Run the application:**
   ```bash
   mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Access the application:**
   - Open your browser and go to: `http://localhost:8080`

## 🔐 Default Login Credentials

### Supervisor Admin (Super User)
- **Username:** `SupervisorAdmin`
- **Password:** `1234`

This account is automatically created on first startup and has full system access.

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Quizzes Table
```sql
CREATE TABLE quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);
```

### Questions & Options Tables
See `database-schema.sql` for complete schema.

## 🎨 User Interface

### Login Page
- Modern, gradient design
- Displays default supervisor credentials
- Error handling for invalid credentials

### Supervisor Dashboard
- Statistics overview (Total Admins, Students, Quizzes)
- Create Admin accounts
- Create Student accounts
- View and manage all users
- Monitor all quizzes

### Admin Dashboard
- Statistics overview (Total Students, Quizzes)
- Create Student accounts
- Manage students
- View quizzes

### Student Portal
- View available quizzes
- Take quizzes (coming soon)
- View quiz results (coming soon)

## 🔒 Security Features

- Session-based authentication
- Role-based access control (RBAC)
- Authorization checks on all endpoints
- Automatic redirect based on user role
- Protected routes for each user type

## 🛣️ API Endpoints

### Authentication
- `GET /` - Login page
- `POST /` - Login submission
- `GET /logout` - Logout

### Supervisor Routes
- `GET /supervisor/dashboard` - Supervisor dashboard
- `POST /supervisor/create-admin` - Create admin account
- `POST /supervisor/create-student` - Create student account
- `POST /supervisor/delete-user/{id}` - Delete user

### Admin Routes
- `GET /admin/dashboard` - Admin dashboard
- `POST /admin/create-student` - Create student account
- `POST /admin/delete-student/{id}` - Delete student

### Student Routes
- `GET /student/home` - Student home page
- `GET /student/quizzes` - View available quizzes

## 📝 User Workflow

### Supervisor Admin Workflow
1. Login with SupervisorAdmin credentials
2. View dashboard with system statistics
3. Create admin accounts for quiz management
4. Create student accounts for quiz taking
5. Monitor all users and quizzes
6. Delete users when necessary

### Admin Workflow
1. Login with admin credentials (created by Supervisor)
2. View dashboard with student statistics
3. Create student accounts
4. Manage students
5. Create and manage quizzes (future feature)

### Student Workflow
1. Login with student credentials (created by Supervisor or Admin)
2. View available quizzes
3. Take quizzes
4. View results

## 🔄 Development

### Adding New Features
1. Create new controllers in appropriate packages
2. Create corresponding service classes
3. Add HTML templates in `src/main/resources/templates/`
4. Update routes in controllers

### Database Changes
- Modify entity classes in domain packages
- Spring Boot will auto-update schema (via `ddl-auto=update`)
- For production, use migrations (Flyway/Liquibase)

## 🐛 Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Verify credentials in `application.properties`
- Check if database `quizapp` exists

### Port Already in Use
- Change server port in `application.properties`:
  ```properties
  server.port=8081
  ```

### SupervisorAdmin Not Created
- Check console logs for errors
- Manually insert using phpMyAdmin:
  ```sql
  INSERT INTO users (username, password, role, full_name, email)
  VALUES ('SupervisorAdmin', '1234', 'SUPERVISOR_ADMIN', 'Supervisor Administrator', 'supervisor@quizapp.com');
  ```

## 📈 Future Enhancements

- [ ] Quiz taking functionality
- [ ] Quiz result tracking
- [ ] Quiz analytics and reporting
- [ ] Password encryption (BCrypt)
- [ ] Email notifications
- [ ] User profile management
- [ ] Quiz categories and tags
- [ ] Timed quizzes
- [ ] Quiz history for students
- [ ] Export quiz results to PDF/Excel

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Support

For issues and questions:
1. Check the troubleshooting section
2. Review application logs
3. Check MySQL logs in phpMyAdmin
4. Review Spring Boot console output

---

**Happy Learning! 🎓**

