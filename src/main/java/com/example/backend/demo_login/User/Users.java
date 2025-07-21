package com.example.backend.demo_login.User;

import com.example.backend.demo_login.Component.AuditDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@Document(collection = "User")
@CompoundIndex(def = "{'username': 1}", unique = true)
@CompoundIndex(def = "{'email': 1}", unique = true)
public class Users {
    @Id
    private String id;
    
    private String userId;
    
    @Indexed(unique = true)
    private String username;
    
    @JsonIgnore // Never serialize password
    private String password;
    
    @Indexed(unique = true)
    private String email;
    
    @Builder.Default
    private List<String> roles = Arrays.asList("USER");
    
    private String profilePicUrl;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    private AuditDateTime auditDateTime;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;

    // Default constructor for MongoDB
    public Users() {}
    
    // All args constructor for Builder
    public Users(String id, String userId, String username, String password, 
                 String email, List<String> roles, String profilePicUrl, 
                 LocalDateTime createdAt, LocalDateTime updatedAt,
                 AuditDateTime auditDateTime, String createdBy, 
                 String updatedBy, String deletedBy) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles != null ? roles : Arrays.asList("USER");
        this.profilePicUrl = profilePicUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.auditDateTime = auditDateTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
    }
}
