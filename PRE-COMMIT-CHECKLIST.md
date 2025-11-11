# Pre-Commit Checklist ✅

Before pushing to GitHub, verify these items to ensure your teammates can use the project smoothly.

## 1. Database Configuration ✅

### Check `application.properties`
- [ ] Database URL uses `localhost` (not an IP address specific to your machine)
- [ ] Username is `root` (default for XAMPP)
- [ ] Password is empty or a common default
- [ ] Port is `3306` (standard MySQL port)

**Current settings should be:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```

⚠️ **Important:** Don't commit personal database credentials! Keep defaults for team sharing.

## 2. Files to Include ✅

### Essential Files
- [ ] `pom.xml` (Maven dependencies)
- [ ] `application.properties` (with default credentials)
- [ ] `database-schema.sql` (reference for database structure)
- [ ] `SETUP.md` (setup guide for teammates)
- [ ] `DATABASE_INFO.md` (database explanation)
- [ ] `README.md` (project overview)
- [ ] All Java source files
- [ ] All HTML template files
- [ ] `.gitignore` (to prevent committing unnecessary files)

### Files to Exclude (Already in .gitignore)
- [ ] `target/` folder (compiled files)
- [ ] `.idea/` folder (IDE settings)
- [ ] `*.class` files (compiled Java)
- [ ] Personal IDE configurations

## 3. Remove Personal/Temporary Files ✅

Check for these and remove if present:
- [ ] No personal notes or TODO files
- [ ] No test database dumps with your data
- [ ] No `.env` files with personal credentials
- [ ] No compiled `.class` files (should be in `target/`)

## 4. Verify Application Runs ✅

Before committing:
- [ ] Run `mvnw.cmd clean install` successfully
- [ ] Start the application with `mvnw.cmd spring-boot:run`
- [ ] Access http://localhost:8080 successfully
- [ ] Login with SupervisorAdmin/1234 works
- [ ] No errors in console

## 5. Test Database Auto-Creation ✅

To simulate what teammates will experience:
1. [ ] Drop your current database: `DROP DATABASE quizapp;`
2. [ ] Create empty database: `CREATE DATABASE quizapp;`
3. [ ] Run the application
4. [ ] Verify tables are created automatically
5. [ ] Verify SupervisorAdmin account is created
6. [ ] Login and test basic functionality

## 6. Documentation Check ✅

- [ ] `SETUP.md` has clear step-by-step instructions
- [ ] `README.md` describes the project well
- [ ] `DATABASE_INFO.md` explains database setup
- [ ] Default login credentials are documented
- [ ] Common issues and solutions are listed

## 7. Git Preparation ✅

### Initialize Git (if not already done)
```bash
cd QuizApp
git init
```

### Add Remote Repository
```bash
git remote add origin <your-github-repo-url>
```

### Check Status
```bash
git status
```

Verify that:
- [ ] Only necessary files are staged
- [ ] `target/` folder is not included
- [ ] IDE-specific files are not included

## 8. Commit and Push ✅

### First Commit
```bash
# Add all files
git add .

# Verify what will be committed
git status

# Commit with meaningful message
git commit -m "Initial commit: Quiz App with role-based access control"

# Push to GitHub
git push -u origin main
```

Or if using master branch:
```bash
git push -u origin master
```

## 9. GitHub Repository Setup ✅

After pushing, verify on GitHub:
- [ ] All files are visible
- [ ] README.md displays properly on the repository page
- [ ] Links in README work (SETUP.md, DATABASE_INFO.md)
- [ ] `.gitignore` is working (no target/ folder visible)

## 10. Test with Fresh Clone ✅

Simulate teammate experience:
1. [ ] Clone to a different folder: `git clone <repo-url> test-clone`
2. [ ] Create empty database: `CREATE DATABASE quizapp;`
3. [ ] Update `application.properties` if needed
4. [ ] Run: `mvnw.cmd spring-boot:run`
5. [ ] Verify application works
6. [ ] Verify database tables are created
7. [ ] Verify login works

## 11. Inform Your Team 📢

After successful push, send teammates:

**Message Template:**
```
Hey team! 👋

The Quiz App is now on GitHub: [repo-url]

Quick Setup (5 minutes):
1. Clone the repo
2. Create database: CREATE DATABASE quizapp;
3. Run: mvnw.cmd spring-boot:run
4. Access: http://localhost:8080

📖 Check SETUP.md for detailed instructions!

Default Login:
Username: SupervisorAdmin
Password: 1234

Questions? Check DATABASE_INFO.md or let me know!
```

## Common Issues & Solutions

### Issue: Teammates can't connect to database
**Solution:** They need to update `application.properties` with their MySQL credentials.

### Issue: Tables not created
**Solution:** Make sure `spring.jpa.hibernate.ddl-auto=update` is in `application.properties`.

### Issue: Port 8080 already in use
**Solution:** They can change port in `application.properties`: `server.port=8081`

## Final Verification ✅

- [ ] All team members can clone and run the project
- [ ] Database works automatically on their machines
- [ ] Documentation is clear and helpful
- [ ] No personal files or credentials committed
- [ ] Application runs without errors

---

## You're Ready! 🚀

Once all items are checked, your project is ready for team collaboration!

**Commands Summary:**
```bash
# In QuizApp directory
git add .
git commit -m "Initial commit: Quiz App project"
git push -u origin main
```

Happy Coding! 🎓

