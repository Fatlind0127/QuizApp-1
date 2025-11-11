# 🚀 Step-by-Step: Push to GitHub

## Current Problem
Git is using old credentials for user `Fatlind0127`, but your repository belongs to `ledribilurdagu`.

## Solution: Follow These Steps

---

## 📝 Step 1: Remove Old Credentials

### Method A: Using Windows Credential Manager

1. **Press `Windows + R`**
2. **Type:** `control /name Microsoft.CredentialManager`
3. **Press Enter**
4. **Click "Windows Credentials"**
5. **Find:** `git:https://github.com` or `github.com`
6. **Click it** to expand
7. **Click "Remove"** or **"Edit"** and delete it
8. **Close Credential Manager**

### Method B: Using Command Line

Open **Command Prompt as Administrator** and run:

```bash
cmdkey /list
```

Look for any `git:https://github.com` entries and remove them:

```bash
cmdkey /delete:git:https://github.com
```

---

## 🔑 Step 2: Create Personal Access Token

1. **Go to:** https://github.com/settings/tokens
2. **Make sure you're logged in as:** `ledribilurdagu`
3. **Click:** "Generate new token" → "Generate new token (classic)"
4. **Fill in:**
   - **Note:** `QuizApp Push Token`
   - **Expiration:** 90 days (or your choice)
   - **Scopes:** Check ✅ **`repo`** (Full control)
5. **Click:** "Generate token"
6. **COPY THE TOKEN** immediately (starts with `ghp_...`)
   - ⚠️ You won't see it again!

---

## 📤 Step 3: Push to GitHub

### Open Command Prompt in QuizApp folder:

1. **Navigate to:** `C:\xampp\htdocs\Ledri\QuizApp`
2. **Right-click** in the folder → **"Open in Terminal"** or **"Git Bash Here"**

### Run these commands:

```bash
# Check status
git status

# Push to GitHub
git push origin main
```

### When prompted:

- **Username:** `ledribilurdagu`
- **Password:** Paste your Personal Access Token (the `ghp_...` token you copied)
- **Press Enter**

---

## ✅ Step 4: Verify Push

1. **Go to:** https://github.com/ledribilurdagu/QuizApp
2. **Refresh the page**
3. **You should see your latest commits!**

---

## 🎯 Quick Commands Summary

```bash
# Navigate to project
cd C:\xampp\htdocs\Ledri\QuizApp

# Check what needs to be pushed
git status

# Push to GitHub
git push origin main

# If you need to see commits
git log --oneline -5
```

---

## 🆘 If It Still Doesn't Work

### Option 1: Use GitHub Desktop
1. Download: https://desktop.github.com/
2. Sign in as `ledribilurdagu`
3. File → Add Local Repository
4. Select: `C:\xampp\htdocs\Ledri\QuizApp`
5. Click "Publish branch" or "Push origin"

### Option 2: Use SSH (Advanced)
If you have SSH keys set up:

```bash
# Change remote URL to SSH
git remote set-url origin git@github.com:ledribilurdagu/QuizApp.git

# Push
git push origin main
```

### Option 3: Check Repository Access
1. Go to: https://github.com/ledribilurdagu/QuizApp/settings/access
2. Make sure you're logged in as `ledribilurdagu`
3. Verify you have write access

---

## 📊 Current Status

- ✅ **2 commits** ready to push:
  - `0fcb90d` - Docs: Add GitHub push instructions
  - `ccd794a` - Add: Password hashing, student format, logout buttons
- ✅ Repository: `https://github.com/ledribilurdagu/QuizApp`
- ⚠️ **Need to:** Clear old credentials and use Personal Access Token

---

## 💡 Pro Tip

After successfully pushing once, Windows will save your credentials. You won't need to enter the token again until it expires (or you clear credentials).

---

**Your repository URL:** https://github.com/ledribilurdagu/QuizApp



