-- PostgreSQL initialization script for Docker
-- Create the main database
CREATE DATABASE backend_project;
CREATE DATABASE backend_project_dev;

-- Create a dedicated user for the application (optional)
-- CREATE USER app_user WITH PASSWORD 'app_password';
-- GRANT ALL PRIVILEGES ON DATABASE backend_project TO app_user;
-- GRANT ALL PRIVILEGES ON DATABASE backend_project_dev TO app_user;

-- Connect to the main database
\c backend_project;

-- Enable necessary extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Connect to the dev database
\c backend_project_dev;

-- Enable necessary extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
