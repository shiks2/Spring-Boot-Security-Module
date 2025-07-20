package com.example.backend.demo_login.Shop;

import com.example.backend.demo_login.Component.AuditDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Shop")
public class Shop {
    private String id;
    private String name;
    private String description;
    private String gstin;
    private String phoneNumber;
    private String address;
    private String userId;
    private AuditDateTime auditDateTime;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
}
