// MongoDB initialization script
// This script will be executed when the MongoDB container starts for the first time

print('Start MongoDB initialization...');

// Switch to the application database
db = db.getSiblingDB('backendProject');

// Create indexes for better performance
db.User.createIndex({ "username": 1 }, { unique: true });
db.User.createIndex({ "email": 1 }, { unique: true });
db.User.createIndex({ "username": 1, "email": 1 });
db.User.createIndex({ "createdAt": 1 });

// Create a sample admin user (optional - remove in production)
db.User.insertOne({
  userId: "admin:admin@example.com",
  username: "admin",
  email: "admin@example.com",
  password: "$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc6YzVBLO5qBrqGTXXrUO", // BCrypt hash for "admin123"
  roles: ["ADMIN", "USER"],
  profilePicUrl: null,
  createdAt: new Date(),
  updatedAt: new Date(),
  auditDateTime: {
    createdAt: new Date(),
    updatedAt: new Date()
  },
  createdBy: "system",
  updatedBy: "system",
  deletedBy: null
});

print('MongoDB initialization completed successfully.');

// Show created indexes
print('Created indexes:');
db.User.getIndexes().forEach(printjson);

// Show collection stats
print('User collection stats:');
printjson(db.User.stats());
