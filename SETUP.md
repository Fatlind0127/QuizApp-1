# Quiz App Setup Guide for Team Members

## Prerequisites
- Java 17 or higher
- Maven
- XAMPP (with MySQL/MariaDB and phpMyAdmin)
- Git

## Quick Setup (5 Minutes)

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd QuizApp
```

### Step 2: Setup Database
1. Start XAMPP (Apache & MySQL)
2. Open phpMyAdmin: http://localhost/phpmyadmin
3. Create a new database:
   - Click "New" in the left sidebar
   - Database name: `quizapp`
   - Collation: `utf8mb4_general_ci`
   - Click "Create"

**That's it for the database!** The application will automatically create all tables and initial data.

### Step 3: Verify Database Configuration
Check `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizapp
spring.datasource.username=root
spring.datasource.password=
```

**Important:** If your MySQL has a different:
- Username: Change `root` to your username
- Password: Add your password after `password=`
- Port: Change `3306` to your port number

### Step 4: Run the Application

**Option A: Using the batch file (Windows)**
```bash
start-app.bat
```

**Option B: Using Maven**
```bash
mvnw.cmd spring-boot:run
```

**Option C: Using Maven (if Maven is installed)**
```bash
mvn spring-boot:run
```

### Step 5: Access the Application
Open your browser and go to: http://localhost:8080

**Default Login Credentials:**
- Username: `SupervisorAdmin`
- Password: `1234`

## How It Works

### Automatic Database Setup
The application uses **Hibernate Auto-DDL** which means:
- ✅ All tables are created automatically from Java entities
- ✅ Tables are updated automatically when you modify entities
- ✅ No need to manually run SQL scripts
- ✅ Default SupervisorAdmin account is created on first run

### Database Schema Reference
The `database-schema.sql` file is provided for reference only. You can use it if you want to:
- See the complete database structure
- Manually create tables (optional)
- Add sample test data

To use it (optional):
1. Open phpMyAdmin
2. Select the `quizapp` database
3. Click "Import"
4. Choose `database-schema.sql`
5. Click "Go"

## Common Issues & Solutions

### Issue 1: "Access denied for user 'root'@'localhost'"
**Solution:** Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Issue 2: "Unknown database 'quizapp'"
**Solution:** Create the database in phpMyAdmin:
```sql
CREATE DATABASE quizapp;
```

### Issue 3: Port 8080 is already in use
**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```

### Issue 4: Maven command not found
**Solution:** Use the Maven Wrapper included in the project:
- Windows: `mvnw.cmd spring-boot:run`
- Linux/Mac: `./mvnw spring-boot:run`

## Project Structure

```
QuizApp/
├── src/main/java/com/quizapp/quizapp/
│   ├── admin/           # Admin functionality
│   ├── auth/            # Authentication & login
│   ├── config/          # Configuration & data initialization
│   ├── domain/          # Quiz, Question, Option entities
│   ├── student/         # Student functionality
│   ├── supervisor/      # Supervisor functionality
│   └── user/            # User management
├── src/main/resources/
│   ├── application.properties  # Database & app configuration
│   └── templates/              # HTML templates
├── database-schema.sql   # Database schema reference
├── start-app.bat        # Quick start script
└── pom.xml              # Maven dependencies
```

## Development Workflow

### 1. Pull Latest Changes
```bash
git pull origin main
```

### 2. Make Your Changes
- Work on your assigned feature
- Test locally

### 3. Commit and Push
```bash
git add .
git commit -m "Description of your changes"
git push origin main
```

### 4. Database Changes
If you modify JPA entities (classes in `domain/`, `user/`, etc.):
- The database will automatically update when you restart the app
- No manual SQL needed!

## Testing Accounts

After running the application, you can create test accounts through:
- SupervisorAdmin can create Admins
- Admins can create Students
- Or use the sample data in `database-schema.sql`

## Need Help?

- Check the console output for error messages
- Verify XAMPP MySQL is running
- Ensure you created the `quizapp` database
- Check that `application.properties` has correct credentials

## Additional Resources

- [Spring Boot Documentation](https://spring.boot.io/docs)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Ready to code!** 🚀 Once setup is complete, the application will be running at http://localhost:8080

