package com.example.backend.demo_login.Routine;

import com.example.backend.demo_login.Component.AuditDateTime;
import com.example.backend.demo_login.Enum.RoutineFrequency;
import com.example.backend.demo_login.Enum.RoutineStatus;
import com.example.backend.demo_login.Enum.RoutineType;
import com.example.backend.demo_login.User.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "Routine")
public class Routine {
    @Id
    private String id;
    
    private String routineId;
    private String routineName;
    private String description;
    private RoutineType routineType;
    private RoutineFrequency routineFrequency;
    private RoutineStatus routineStatus;
    private String userId;
    private Users user;
    private AuditDateTime auditDateTime;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;

}
