@echo off
echo ========================================
echo Pushing QuizApp to GitHub
echo ========================================
echo.

echo Step 1: Checking git status...
git status
echo.

echo Step 2: Pushing to GitHub...
echo.
echo NOTE: You may be prompted for credentials:
echo   - Username: ledribilurdagu
echo   - Password: Your Personal Access Token (not your GitHub password)
echo.

git push origin main

echo.
echo ========================================
if %ERRORLEVEL% EQU 0 (
    echo SUCCESS! Your code has been pushed to GitHub.
    echo Repository: https://github.com/ledribilurdagu/QuizApp
) else (
    echo ERROR: Push failed. 
    echo.
    echo Common issues:
    echo 1. Authentication failed - You need a Personal Access Token
    echo    Get one at: https://github.com/settings/tokens
    echo.
    echo 2. Permission denied - Make sure you're logged in as ledribilurdagu
    echo.
    echo See PUSH_TO_GITHUB.md for detailed instructions.
)
echo ========================================
pause



