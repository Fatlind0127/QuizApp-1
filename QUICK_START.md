# 🚀 Quick Start Guide - Quiz App

## Step 1: Start MySQL
1. Open XAMPP Control Panel
2. Click "Start" for MySQL module
3. Ensure it's running (green indicator)

## Step 2: Create Database
1. Open browser and go to: `http://localhost/phpmyadmin`
2. Click "New" in the left sidebar
3. Database name: `quizapp`
4. Click "Create"

## Step 3: Run the Application
1. Open Command Prompt or Terminal
2. Navigate to the QuizApp folder:
   ```bash
   cd C:\xampp\htdocs\Quiz\QuizApp
   ```
3. Run the application:
   ```bash
   mvnw.cmd spring-boot:run
   ```

## Step 4: Login
1. Open browser: `http://localhost:8080`
2. Use these credentials:
   - **Username:** SupervisorAdmin
   - **Password:** 1234

## 🎉 You're Ready!

### What You Can Do Now:

#### As Supervisor Admin:
✅ Create admin accounts  
✅ Create student accounts  
✅ View all users and quizzes  
✅ Delete users  
✅ Monitor the entire system  

#### Create Your First Admin:
1. Go to "Create Admin Account" section
2. Enter:
   - Username: `admin1`
   - Password: `admin123`
   - Full Name: `John Admin`
   - Email: `admin@example.com`
3. Click "Create Admin"

#### Create Your First Student:
1. Go to "Create Student Account" section
2. Enter:
   - Username: `student1`
   - Password: `student123`
   - Full Name: `Jane Student`
   - Email: `student@example.com`
3. Click "Create Student"

#### Test Different Roles:
1. Logout (click "Logout" button)
2. Login as admin (`admin1` / `admin123`)
3. See admin dashboard and capabilities
4. Logout and login as student (`student1` / `student123`)
5. See student portal

## 📊 Database Tables (Auto-Created)
The following tables are automatically created:
- `users` - All users (supervisor, admins, students)
- `quizzes` - Quiz information
- `questions` - Quiz questions
- `options` - Answer options
- `quiz_results` - Student quiz results
- `student_answers` - Student answer records

## 🔍 View Your Database
1. Open phpMyAdmin: `http://localhost/phpmyadmin`
2. Click on `quizapp` database (left sidebar)
3. Click on any table to view data

## ⚠️ Troubleshooting

### Application won't start?
- Make sure MySQL is running in XAMPP
- Check if port 8080 is available
- Verify Java 21 is installed: `java -version`

### Can't login?
- Check console logs for errors
- Verify database connection
- Check if `users` table exists in phpMyAdmin

### Database error?
- Ensure `quizapp` database exists
- Check `application.properties` for correct credentials
- Default MySQL username: `root`, password: (empty)

## 📞 Need Help?
Check the full README.md for detailed documentation!

