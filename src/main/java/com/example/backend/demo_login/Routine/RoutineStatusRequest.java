package com.example.backend.demo_login.Routine;

import com.example.backend.demo_login.Enum.RoutineStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoutineStatusRequest {
    private RoutineStatus status;
}
