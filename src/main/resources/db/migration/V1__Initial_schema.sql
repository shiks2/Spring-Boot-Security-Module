-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_pic_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);

-- Create user_roles table for storing user roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create shops table
CREATE TABLE shops (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    gstin VARCHAR(50),
    phone_number VARCHAR(20),
    address TEXT,
    user_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);

-- Create routines table
CREATE TABLE routines (
    id BIGSERIAL PRIMARY KEY,
    routine_id VARCHAR(255),
    routine_name VARCHAR(255) NOT NULL,
    description TEXT,
    routine_type VARCHAR(50),
    routine_frequency VARCHAR(50),
    user_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_shops_user_id ON shops(user_id);
CREATE INDEX idx_routines_user_id ON routines(user_id);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);

-- Add foreign key relationships
ALTER TABLE shops ADD CONSTRAINT fk_shops_user_id
    FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE routines ADD CONSTRAINT fk_routines_user_id
    FOREIGN KEY (user_id) REFERENCES users(user_id);
