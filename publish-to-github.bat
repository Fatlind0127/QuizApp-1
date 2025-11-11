@echo off
echo ========================================
echo  Publish Quiz App to GitHub
echo ========================================
echo.
echo This script will help you publish your project to GitHub.
echo.
echo PREREQUISITES:
echo 1. You have created a repository on GitHub
echo 2. You have the repository URL ready
echo 3. You have a GitHub Personal Access Token (if using HTTPS)
echo.
pause
echo.
echo ========================================
echo Step 1: Initializing Git Repository
echo ========================================
git init
if errorlevel 1 (
    echo [ERROR] Git initialization failed. Is Git installed?
    pause
    exit /b 1
)
echo [OK] Git initialized.
echo.

echo ========================================
echo Step 2: Adding All Files
echo ========================================
git add .
if errorlevel 1 (
    echo [ERROR] Failed to add files.
    pause
    exit /b 1
)
echo [OK] Files added.
echo.

echo ========================================
echo Step 3: Creating Initial Commit
echo ========================================
git commit -m "Initial commit: Quiz App with role-based access control system"
if errorlevel 1 (
    echo [WARNING] Commit failed or no changes to commit.
)
echo [OK] Initial commit created.
echo.

echo ========================================
echo Step 4: Adding Remote Repository
echo ========================================
echo.
echo Please enter your GitHub repository URL:
echo Example: https://github.com/username/quiz-app.git
echo.
set /p REPO_URL="Repository URL: "
echo.
git remote add origin %REPO_URL%
if errorlevel 1 (
    echo [WARNING] Remote might already exist. Trying to update...
    git remote set-url origin %REPO_URL%
)
echo [OK] Remote repository configured.
echo.

echo ========================================
echo Step 5: Renaming Branch to Main
echo ========================================
git branch -M main
echo [OK] Branch renamed to main.
echo.

echo ========================================
echo Step 6: Pushing to GitHub
echo ========================================
echo.
echo You will be prompted for:
echo - Username: Your GitHub username
echo - Password: Your Personal Access Token (NOT your GitHub password!)
echo.
echo If you don't have a token, see: https://github.com/settings/tokens
echo.
pause
echo.
git push -u origin main
if errorlevel 1 (
    echo [ERROR] Push failed. Please check:
    echo 1. Repository URL is correct
    echo 2. You have access to the repository
    echo 3. You used a Personal Access Token (not password)
    pause
    exit /b 1
)
echo.
echo ========================================
echo SUCCESS! 🎉
echo ========================================
echo.
echo Your project has been published to GitHub!
echo Repository: %REPO_URL%
echo.
echo Next Steps:
echo 1. Visit your GitHub repository
echo 2. Add teammates as collaborators
echo 3. Share the repository URL with your team
echo.
echo See GITHUB_SETUP.md for more details.
echo.
pause

