package com.example.backend.demo_login.Routine;

import com.example.backend.demo_login.Component.AuditDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineService {
    
    private final RoutineRepository routineRepository;
    
    /**
     * Create a new routine
     */
    public Routine createRoutine(Routine routine) {
        log.info("Creating new routine: {}", routine.getRoutineName());
        
        String currentUser = getCurrentUsername();
        
        // Generate unique routineId if not provided
        if (routine.getRoutineId() == null || routine.getRoutineId().isEmpty()) {
            routine.setRoutineId(UUID.randomUUID().toString());
        }
        
        // Set audit fields
        routine.setCreatedBy(currentUser);
        routine.setAuditDateTime(new AuditDateTime(Instant.now(), null, null));
        
        // Ensure userId is set (should come from authenticated user)
        if (routine.getUserId() == null || routine.getUserId().isEmpty()) {
            routine.setUserId(currentUser);
        }
        
        Routine savedRoutine = routineRepository.save(routine);
        log.info("Routine created successfully with ID: {}", savedRoutine.getRoutineId());
        
        return savedRoutine;
    }
    
    /**
     * Get all routines (not soft deleted)
     */
    public List<Routine> getAllRoutines() {
        log.info("Fetching all routines");
        return routineRepository.findAllNotDeleted();
    }
    
    /**
     * Get routine by ID
     */
    public Optional<Routine> getRoutineById(String id) {
        log.info("Fetching routine by ID: {}", id);
        return routineRepository.findByIdAndNotDeleted(id);
    }
    
    /**
     * Get routines by userId
     */
    public List<Routine> getRoutinesByUserId(String userId) {
        log.info("Fetching routines for user: {}", userId);
        return routineRepository.findByUserIdAndNotDeleted(userId);
    }
    
    /**
     * Update routine
     */
    public Optional<Routine> updateRoutine(String id, Routine routineDetails) {
        log.info("Updating routine with ID: {}", id);
        
        return routineRepository.findByIdAndNotDeleted(id)
                .map(existingRoutine -> {
                    String currentUser = getCurrentUsername();
                    
                    // Update fields
                    existingRoutine.setRoutineName(routineDetails.getRoutineName());
                    existingRoutine.setDescription(routineDetails.getDescription());
                    existingRoutine.setRoutineType(routineDetails.getRoutineType());
                    existingRoutine.setRoutineFrequency(routineDetails.getRoutineFrequency());
                    
                    // Update audit fields
                    existingRoutine.setUpdatedBy(currentUser);
                    if (existingRoutine.getAuditDateTime() != null) {
                        existingRoutine.getAuditDateTime().setUpdatedAt(Instant.now());
                    } else {
                        existingRoutine.setAuditDateTime(new AuditDateTime(null, Instant.now(), null));
                    }
                    
                    Routine updatedRoutine = routineRepository.save(existingRoutine);
                    log.info("Routine updated successfully: {}", updatedRoutine.getRoutineId());
                    
                    return updatedRoutine;
                });
    }
    
    /**
     * Soft delete routine
     */
    public boolean deleteRoutine(String id) {
        log.info("Soft deleting routine with ID: {}", id);
        
        Optional<Routine> routineOpt = routineRepository.findByIdAndNotDeleted(id);
        if (routineOpt.isPresent()) {
            Routine routine = routineOpt.get();
            String currentUser = getCurrentUsername();
            
            // Set soft delete fields
            routine.setDeletedBy(currentUser);
            if (routine.getAuditDateTime() != null) {
                routine.getAuditDateTime().setDeletedAt(Instant.now());
            } else {
                routine.setAuditDateTime(new AuditDateTime(null, null, Instant.now()));
            }
            
            routineRepository.save(routine);
            log.info("Routine soft deleted successfully: {}", routine.getRoutineId());
            return true;
        }
        
        log.warn("Routine not found for deletion: {}", id);
        return false;
    }
    
    /**
     * Get current authenticated username
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "system";
    }
}
