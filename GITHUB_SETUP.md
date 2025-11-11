# 🚀 Publishing Quiz App to GitHub

## Step-by-Step Guide to Publish Your Project

### Step 1: Create a GitHub Repository

1. **Go to GitHub:** https://github.com
2. **Sign in** to your account
3. **Click the "+" icon** in the top right corner
4. **Select "New repository"**
5. **Fill in the details:**
   - Repository name: `quiz-app` (or your preferred name)
   - Description: `Role-based Quiz Management System with Spring Boot`
   - Choose: **Public** (if you want it public) or **Private** (for team only)
   - **Do NOT** initialize with README (we already have one)
   - **Do NOT** add .gitignore (we already have one)
   - Click **"Create repository"**

6. **Copy the repository URL** (you'll need it in Step 2)
   - Example: `https://github.com/your-username/quiz-app.git`

---

### Step 2: Initialize Git in Your Project

Open **Command Prompt** or **PowerShell** and run:

```bash
cd C:\xampp\htdocs\Quiz\QuizApp
```

Initialize Git:
```bash
git init
```

Add all files:
```bash
git add .
```

Make your first commit:
```bash
git commit -m "Initial commit: Quiz App with role-based access control"
```

---

### Step 3: Connect to GitHub and Push

Add your GitHub repository as remote (replace with YOUR repository URL):
```bash
git remote add origin https://github.com/YOUR-USERNAME/quiz-app.git
```

Rename branch to main (GitHub's default):
```bash
git branch -M main
```

Push your code to GitHub:
```bash
git push -u origin main
```

**If prompted for credentials:**
- Username: Your GitHub username
- Password: Use a **Personal Access Token** (see below if you don't have one)

---

### Step 4: Create Personal Access Token (If Needed)

GitHub no longer accepts passwords for Git operations. You need a Personal Access Token:

1. Go to: https://github.com/settings/tokens
2. Click **"Generate new token"** → **"Generate new token (classic)"**
3. Give it a name: `QuizApp Development`
4. Select scopes:
   - ✅ `repo` (Full control of private repositories)
5. Click **"Generate token"**
6. **COPY THE TOKEN** (you won't see it again!)
7. Use this token as your password when pushing to GitHub

---

### Step 5: Verify Your Repository

1. Go to your GitHub repository URL
2. Refresh the page
3. You should see all your files! 🎉

---

## 👥 For Teammates: How to Clone and Work on the Project

### Cloning the Repository

```bash
# Clone the repository
git clone https://github.com/YOUR-USERNAME/quiz-app.git

# Navigate into the project
cd quiz-app
```

### Setup and Run

1. **Create MySQL database:**
   - Open phpMyAdmin: `http://localhost/phpmyadmin`
   - Create database: `quizapp`

2. **Run the application:**
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. **Access:** `http://localhost:8080`

**See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed setup instructions.**

---

## 🔄 Common Git Commands for Team Collaboration

### Getting Latest Changes
```bash
# Pull latest changes from GitHub
git pull origin main
```

### Working on a New Feature
```bash
# Create and switch to a new branch
git checkout -b feature/my-new-feature

# Make your changes...

# Add and commit
git add .
git commit -m "Add: description of what you added"

# Push your branch
git push origin feature/my-new-feature
```

### Updating Your Branch
```bash
# Switch to main
git checkout main

# Pull latest changes
git pull origin main

# Switch back to your feature branch
git checkout feature/my-new-feature

# Merge main into your branch
git merge main
```

### Checking Status
```bash
# See what files have changed
git status

# See what changes were made
git diff
```

---

## 📋 Recommended Branch Strategy

### Main Branches
- `main` - Production-ready code
- `develop` - Development branch (optional)

### Feature Branches
- `feature/user-authentication` - New feature
- `bugfix/login-error` - Bug fix
- `hotfix/security-patch` - Urgent fix

### Workflow
1. Create feature branch from `main`
2. Work on your feature
3. Create Pull Request to merge back to `main`
4. Team reviews the code
5. Merge after approval

---

## 🔒 Security Best Practices

### ⚠️ NEVER Commit:
- Database passwords (if not default)
- API keys
- Personal information
- `.env` files with secrets

### ✅ DO Commit:
- Source code
- Configuration templates
- Documentation
- Sample/test data (non-sensitive)

---

## 📝 Commit Message Guidelines

### Format
```
<type>: <short description>

<longer description if needed>
```

### Types
- `Add:` - New feature
- `Fix:` - Bug fix
- `Update:` - Update existing feature
- `Remove:` - Remove code or files
- `Refactor:` - Code restructuring
- `Docs:` - Documentation only

### Examples
```bash
git commit -m "Add: Student quiz taking functionality"
git commit -m "Fix: Login error when username contains spaces"
git commit -m "Update: Improved supervisor dashboard UI"
git commit -m "Docs: Add setup instructions for Mac users"
```

---

## 🆘 Troubleshooting

### Problem: Permission Denied
**Solution:** Make sure you have access to the repository. Repository owner should add you as a collaborator.

### Problem: Merge Conflicts
**Solution:**
```bash
# Pull latest changes
git pull origin main

# Fix conflicts in the files marked
# Then:
git add .
git commit -m "Resolve merge conflicts"
git push
```

### Problem: Forgot to Pull Before Making Changes
**Solution:**
```bash
# Stash your changes
git stash

# Pull latest changes
git pull origin main

# Apply your changes back
git stash pop

# Resolve any conflicts, then commit
```

---

## 👥 Adding Team Members

### For Repository Owner:

1. Go to your repository on GitHub
2. Click **"Settings"** tab
3. Click **"Collaborators"** (left sidebar)
4. Click **"Add people"**
5. Enter teammate's GitHub username or email
6. They'll receive an invitation to join

---

## 📊 Project Structure on GitHub

```
Your GitHub Repository
├── .github/              # GitHub-specific files (workflows, etc.)
├── src/                  # Source code
├── target/               # (Ignored by .gitignore)
├── .gitignore           # Git ignore rules
├── pom.xml              # Maven configuration
├── README.md            # Main documentation
├── CONTRIBUTING.md      # Guide for contributors
├── QUICK_START.md       # Quick start guide
├── GITHUB_SETUP.md      # This file
├── database-schema.sql  # Database setup
└── start-app.bat        # Startup script
```

---

## 🎉 Next Steps After Publishing

1. **Share repository URL** with your teammates
2. **Add them as collaborators** (see above)
3. **Set up branch protection rules** (optional):
   - Require pull request reviews
   - Require status checks to pass
   - Prevent force pushes to main

4. **Create a project board** (optional):
   - Track tasks and issues
   - Organize development workflow

5. **Enable GitHub Issues** for bug tracking and feature requests

---

## 📚 Additional Resources

- [GitHub Docs](https://docs.github.com)
- [Git Cheat Sheet](https://education.github.com/git-cheat-sheet-education.pdf)
- [Collaborating with Pull Requests](https://docs.github.com/en/pull-requests)
- [GitHub Flow Guide](https://guides.github.com/introduction/flow/)

---

**Your project is now ready for team collaboration! 🚀**

