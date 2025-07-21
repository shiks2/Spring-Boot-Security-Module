package com.example.backend.demo_login.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    
    @Query("{'$or': [{'username': ?0}, {'email': ?0}]}")
    Optional<Users> findByUsernameOrEmail(String usernameOrEmail);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
