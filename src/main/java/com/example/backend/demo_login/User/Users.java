package com.example.backend.demo_login.User;

import com.example.backend.demo_login.Component.AuditDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Data
@Document(collection = "User")
public class Users {
    private String id;
    private String userId;
    private String username;
    private String password;
    private String email;
    private List<String> roles;
    private String profilePicUrl;
    private AuditDateTime auditDateTime;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;


}
