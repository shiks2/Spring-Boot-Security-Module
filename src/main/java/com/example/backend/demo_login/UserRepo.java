package com.example.backend.demo_login;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByUsernameAndPassword(String username, String password);
}
