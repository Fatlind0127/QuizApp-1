@echo off
echo ========================================
echo  Quiz App Startup Script
echo ========================================
echo.
echo Step 1: Checking if MySQL is running...
netstat -ano | findstr :3306 >nul
if errorlevel 1 (
    echo [ERROR] MySQL is NOT running!
    echo Please start MySQL in XAMPP Control Panel first.
    echo.
    pause
    exit /b 1
) else (
    echo [OK] MySQL is running.
)
echo.
echo Step 2: IMPORTANT - Database Setup
echo ----------------------------------------
echo Before running this app, you MUST:
echo 1. Open phpMyAdmin: http://localhost/phpmyadmin
echo 2. Create a database called: quizapp
echo 3. You can leave it empty, tables will be created automatically
echo.
echo Press any key when database is ready, or Ctrl+C to cancel...
pause >nul
echo.
echo Step 3: Starting Spring Boot Application...
echo (This will take 30-60 seconds)
echo.
mvnw.cmd spring-boot:run

