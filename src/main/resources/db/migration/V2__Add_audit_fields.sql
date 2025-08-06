-- Add audit fields to existing tables
ALTER TABLE users ADD COLUMN IF NOT EXISTS audit_created_at TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS audit_updated_at TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMP;

ALTER TABLE shops ADD COLUMN IF NOT EXISTS audit_created_at TIMESTAMP;
ALTER TABLE shops ADD COLUMN IF NOT EXISTS audit_updated_at TIMESTAMP;
ALTER TABLE shops ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMP;

ALTER TABLE routines ADD COLUMN IF NOT EXISTS audit_created_at TIMESTAMP;
ALTER TABLE routines ADD COLUMN IF NOT EXISTS audit_updated_at TIMESTAMP;
ALTER TABLE routines ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMP;

-- Add additional indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at);
CREATE INDEX IF NOT EXISTS idx_users_deleted_at ON users(deleted_at);
CREATE INDEX IF NOT EXISTS idx_shops_created_at ON shops(created_at);
CREATE INDEX IF NOT EXISTS idx_routines_created_at ON routines(created_at);
