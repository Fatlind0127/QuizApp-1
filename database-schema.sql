-- Quiz App Database Schema
-- This file contains the SQL schema for the Quiz App
-- Database: quizapp

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS quizapp;
USE quizapp;

-- ====================================
-- Users Table (Unified table for all user types)
-- ====================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Quizzes Table
-- ====================================
CREATE TABLE IF NOT EXISTS quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Questions Table
-- ====================================
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    question_text TEXT NOT NULL,
    question_order INT DEFAULT 0,
    points INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Options Table (Multiple choice answers)
-- ====================================
CREATE TABLE IF NOT EXISTS options (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    option_text VARCHAR(500) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    option_order INT DEFAULT 0,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Quiz Results Table (Student quiz attempts)
-- ====================================
CREATE TABLE IF NOT EXISTS quiz_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    quiz_id INT NOT NULL,
    score DECIMAL(5,2),
    total_points INT,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Student Answers Table
-- ====================================
CREATE TABLE IF NOT EXISTS student_answers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    result_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option_id INT,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (result_id) REFERENCES quiz_results(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (selected_option_id) REFERENCES options(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================
-- Insert Default SupervisorAdmin Account
-- ====================================
-- Note: Spring Boot will automatically create this, but you can use this if needed
INSERT INTO users (username, password, role, full_name, email)
VALUES ('SupervisorAdmin', '1234', 'SUPERVISOR_ADMIN', 'Supervisor Administrator', 'supervisor@quizapp.com')
ON DUPLICATE KEY UPDATE username = username;

-- ====================================
-- Sample Data (Optional - for testing)
-- ====================================

-- Create sample admin
INSERT INTO users (username, password, role, full_name, email)
VALUES ('admin1', 'admin123', 'ADMIN', 'John Admin', 'admin1@quizapp.com')
ON DUPLICATE KEY UPDATE username = username;

-- Create sample students
INSERT INTO users (username, password, role, full_name, email)
VALUES 
    ('student1', 'student123', 'STUDENT', 'Alice Student', 'student1@quizapp.com'),
    ('student2', 'student123', 'STUDENT', 'Bob Student', 'student2@quizapp.com')
ON DUPLICATE KEY UPDATE username = username;

-- Create sample quiz
INSERT INTO quizzes (title, description, created_by)
VALUES ('Sample Quiz', 'This is a sample quiz for testing', 1);

-- Get the quiz ID (assuming it's 1)
SET @quiz_id = LAST_INSERT_ID();

-- Add sample questions
INSERT INTO questions (quiz_id, question_text, question_order, points)
VALUES 
    (@quiz_id, 'What is the capital of France?', 1, 1),
    (@quiz_id, 'What is 2 + 2?', 2, 1);

-- Get question IDs
SET @q1_id = LAST_INSERT_ID();
SET @q2_id = @q1_id + 1;

-- Add options for question 1
INSERT INTO options (question_id, option_text, is_correct, option_order)
VALUES 
    (@q1_id, 'London', FALSE, 1),
    (@q1_id, 'Paris', TRUE, 2),
    (@q1_id, 'Berlin', FALSE, 3),
    (@q1_id, 'Madrid', FALSE, 4);

-- Add options for question 2
INSERT INTO options (question_id, option_text, is_correct, option_order)
VALUES 
    (@q2_id, '3', FALSE, 1),
    (@q2_id, '4', TRUE, 2),
    (@q2_id, '5', FALSE, 3),
    (@q2_id, '22', FALSE, 4);

-- ====================================
-- Verification Queries
-- ====================================
-- Check users
-- SELECT * FROM users;

-- Check quizzes
-- SELECT * FROM quizzes;

-- Check questions
-- SELECT * FROM questions;

-- Check options
-- SELECT * FROM options;

-- ====================================
-- Useful Queries for Monitoring
-- ====================================

-- Count users by role
-- SELECT role, COUNT(*) as count FROM users GROUP BY role;

-- View all quizzes with creator info
-- SELECT q.*, u.username as creator FROM quizzes q LEFT JOIN users u ON q.created_by = u.id;

-- View quiz with all questions and options
-- SELECT q.title, qs.question_text, o.option_text, o.is_correct
-- FROM quizzes q
-- JOIN questions qs ON q.id = qs.quiz_id
-- JOIN options o ON qs.id = o.question_id
-- ORDER BY q.id, qs.question_order, o.option_order;

