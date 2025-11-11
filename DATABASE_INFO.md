# Database Setup - How It Works

## Overview
This project uses **Hibernate Auto-DDL** for automatic database setup. Your teammates don't need to manually run SQL scripts!

## What Happens Automatically? ✨

### 1. When You Start the Application:

```
Application Starts
    ↓
Hibernate reads your JPA entities (User, Quiz, Question, Option)
    ↓
Checks if tables exist in the database
    ↓
Creates missing tables OR updates existing ones
    ↓
DataInitializer runs
    ↓
Creates SupervisorAdmin account (if not exists)
    ↓
Application Ready! ✅
```

### 2. Key Configuration

In `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=update
```

**What does this mean?**
- `update`: Hibernate automatically creates/updates database tables
- No manual SQL script execution needed
- Safe for development (won't drop existing data)

## For Your Teammates

### All They Need to Do:

1. **Create empty database:**
   ```sql
   CREATE DATABASE quizapp;
   ```

2. **Run the application:**
   ```bash
   start-app.bat
   ```

3. **Done!** All tables and data are created automatically.

### What Gets Created Automatically:

✅ **Tables:**
- users
- quizzes
- questions
- options
- quiz_results
- student_answers

✅ **Initial Data:**
- SupervisorAdmin account (Username: SupervisorAdmin, Password: 1234)

## Optional: Manual Database Setup

The `database-schema.sql` file is provided for:
- **Reference**: See the complete database structure
- **Manual Setup**: If someone prefers to manually create tables
- **Sample Data**: Includes test users and sample quizzes

### To use the SQL file (optional):
1. Open phpMyAdmin
2. Select `quizapp` database
3. Click "Import"
4. Choose `database-schema.sql`
5. Click "Go"

## Different MySQL Credentials?

If teammates have different MySQL settings, they update `application.properties`:

```properties
# Default (XAMPP)
spring.datasource.username=root
spring.datasource.password=

# Custom credentials (example)
spring.datasource.username=myuser
spring.datasource.password=mypassword123

# Different port (example)
spring.datasource.url=jdbc:mysql://localhost:3307/quizapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

## How Database Updates Work

### When You Add/Modify Entities:

```java
// Example: Adding a new field to User entity
@Entity
public class User {
    // ... existing fields ...
    
    @Column(name = "phone_number")
    private String phoneNumber;  // NEW FIELD
}
```

**What happens:**
1. Teammate pulls your changes from GitHub
2. Runs the application
3. Hibernate detects the new field
4. **Automatically adds** the `phone_number` column to the `users` table
5. No manual SQL needed! ✅

### What Hibernate DOES:
✅ Creates new tables
✅ Adds new columns
✅ Updates column types (with caution)

### What Hibernate DOESN'T DO:
❌ Delete columns (keeps old columns for safety)
❌ Rename columns (will create new ones)
❌ Migrate existing data

## Production vs Development

### Current Setup (Development)
```properties
spring.jpa.hibernate.ddl-auto=update
```
- Great for development
- Auto-creates/updates tables
- Preserves existing data

### For Production (Future)
```properties
spring.jpa.hibernate.ddl-auto=validate
```
- Safer for production
- Only validates schema (no changes)
- Use Flyway or Liquibase for migrations

## Troubleshooting

### Issue: Tables not created
**Check:**
1. Is MySQL running?
2. Does the `quizapp` database exist?
3. Are credentials correct in `application.properties`?
4. Check console logs for Hibernate errors

### Issue: SupervisorAdmin not created
**Check:**
1. Look for console message: "✓ Default SupervisorAdmin account created"
2. If not, manually insert via phpMyAdmin:
```sql
INSERT INTO users (username, password, role, full_name, email)
VALUES ('SupervisorAdmin', '1234', 'SUPERVISOR_ADMIN', 'Supervisor Administrator', 'supervisor@quizapp.com');
```

### Issue: "Table 'quizapp.users' doesn't exist"
**Solution:**
1. Delete the `quizapp` database
2. Recreate it: `CREATE DATABASE quizapp;`
3. Restart the application
4. Hibernate will recreate all tables

## Summary for Teammates 📝

**Simple Answer:** Just create an empty database named `quizapp`, and the application handles the rest!

**Why it works:**
- Hibernate creates tables from Java code
- DataInitializer creates the default admin account
- No manual SQL execution required

**Files to know:**
- `application.properties` - Database connection settings
- `database-schema.sql` - Reference only (not required)
- `DataInitializer.java` - Creates default SupervisorAdmin

---

**Questions?** Check the console logs - they show exactly what Hibernate is doing with your database!

