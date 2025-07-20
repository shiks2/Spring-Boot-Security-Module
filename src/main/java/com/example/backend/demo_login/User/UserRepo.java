package com.example.backend.demo_login.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByUsernameAndPassword(String username, String password);
    @Query("{'$or': [{'username': ?0}, {'email': ?0}]}")
    Optional<Users> findByUsernameOrEmail(String usernameOrEmail);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findOptionalByUsername(String username); // ✅ safe for .orElseThrow()

}
