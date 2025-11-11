@echo off
echo ========================================
echo Fix GitHub Authentication and Push
echo ========================================
echo.

echo This script will help you push to GitHub.
echo.
echo IMPORTANT: You need a Personal Access Token first!
echo Get one at: https://github.com/settings/tokens
echo.
pause

echo.
echo Step 1: Clearing old GitHub credentials...
cmdkey /list | findstr github
if %ERRORLEVEL% EQU 0 (
    echo Found GitHub credentials. You may need to remove them manually.
    echo.
    echo To remove manually:
    echo 1. Press Windows + R
    echo 2. Type: control /name Microsoft.CredentialManager
    echo 3. Find "git:https://github.com"
    echo 4. Click it and select "Remove"
    echo.
) else (
    echo No GitHub credentials found in Credential Manager.
)
echo.

echo Step 2: Attempting to push...
echo.
echo When prompted for credentials, enter:
echo   Username: ledribilurdagu
echo   Password: YOUR_PERSONAL_ACCESS_TOKEN
echo.
pause

git push origin main

echo.
echo ========================================
if %ERRORLEVEL% EQU 0 (
    echo SUCCESS! Code pushed to GitHub!
    echo.
    echo View your repository at:
    echo https://github.com/ledribilurdagu/QuizApp
) else (
    echo.
    echo If authentication failed, follow these steps:
    echo.
    echo 1. Create Personal Access Token:
    echo    https://github.com/settings/tokens
    echo.
    echo 2. Remove old credentials:
    echo    - Press Windows + R
    echo    - Type: control /name Microsoft.CredentialManager
    echo    - Remove "git:https://github.com"
    echo.
    echo 3. Try pushing again:
    echo    git push origin main
    echo.
    echo 4. When prompted, enter:
    echo    Username: ledribilurdagu
    echo    Password: [Your Personal Access Token]
)
echo ========================================
pause



