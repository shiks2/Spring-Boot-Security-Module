package com.example.backend.demo_login.Routine;

import com.example.backend.demo_login.Auth.ApiResponse;
import com.example.backend.demo_login.Enum.RoutineStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class RoutineController {
    
    private final RoutineService routineService;
    
    /**
     * Create a new routine
     * POST /api/routines
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Routine>> createRoutine(@RequestBody Routine routine) {
        log.info("Creating routine: {}", routine.getRoutineName());
        
        try {
            // Set userId from authenticated user
            String currentUser = getCurrentUsername();
            routine.setUserId(currentUser);
            
            Routine createdRoutine = routineService.createRoutine(routine);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdRoutine, "Routine created successfully"));
        } catch (Exception e) {
            log.error("Error creating routine: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create routine: " + e.getMessage()));
        }
    }
    
    /**
     * Get all routines
     * GET /api/routines
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Routine>>> getAllRoutines() {
        log.info("Fetching all routines");
        
        try {
            List<Routine> routines = routineService.getAllRoutines();
            return ResponseEntity.ok(ApiResponse.success(routines, "Routines retrieved successfully"));
        } catch (Exception e) {
            log.error("Error fetching routines: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch routines: " + e.getMessage()));
        }
    }
    
    /**
     * Get routine by ID
     * GET /api/routines/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Routine>> getRoutineById(@PathVariable String id) {
        log.info("Fetching routine by ID: {}", id);
        
        try {
            Optional<Routine> routine = routineService.getRoutineById(id);
            if (routine.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(routine.get(), "Routine retrieved successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Routine not found with ID: " + id));
            }
        } catch (Exception e) {
            log.error("Error fetching routine by ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch routine: " + e.getMessage()));
        }
    }
    
    /**
     * Get routines by user ID
     * GET /api/routines/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Routine>>> getRoutinesByUserId(@PathVariable String userId) {
        log.info("Fetching routines for user: {}", userId);
        
        try {
            // Optional: Add security check to ensure user can only access their own routines
            String currentUser = getCurrentUsername();
            if (!currentUser.equals(userId) && !isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Access denied: Cannot access other user's routines"));
            }
            
            List<Routine> routines = routineService.getRoutinesByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success(routines, "User routines retrieved successfully"));
        } catch (Exception e) {
            log.error("Error fetching routines for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch user routines: " + e.getMessage()));
        }
    }
    
    /**
     * Get routines for current user
     * GET /api/routines/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<Routine>>> getMyRoutines() {
        String currentUser = getCurrentUsername();
        log.info("Fetching routines for current user: {}", currentUser);
        
        try {
            List<Routine> routines = routineService.getRoutinesByUserId(currentUser);
            return ResponseEntity.ok(ApiResponse.success(routines, "Your routines retrieved successfully"));
        } catch (Exception e) {
            log.error("Error fetching routines for current user {}: {}", currentUser, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch your routines: " + e.getMessage()));
        }
    }
    
    /**
     * Update routine
     * PATCH /api/routines/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Routine>> updateRoutine(@PathVariable String id, @RequestBody Routine routineDetails) {
        log.info("Updating routine with ID: {}", id);
        
        try {
            Optional<Routine> updatedRoutine = routineService.updateRoutine(id, routineDetails);
            if (updatedRoutine.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(updatedRoutine.get(), "Routine updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Routine not found with ID: " + id));
            }
        } catch (Exception e) {
            log.error("Error updating routine {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update routine: " + e.getMessage()));
        }
    }
    
    /**
     * Update routine status
     * PATCH /api/routines/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Routine>> updateRoutineStatus(@PathVariable String id, @RequestBody RoutineStatusRequest statusRequest) {
        log.info("Updating routine status with ID: {} to status: {}", id, statusRequest.getStatus());
        
        try {
            Optional<Routine> updatedRoutine = routineService.updateRoutineStatus(id, statusRequest.getStatus());
            if (updatedRoutine.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(updatedRoutine.get(), "Routine status updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Routine not found with ID: " + id));
            }
        } catch (Exception e) {
            log.error("Error updating routine status {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update routine status: " + e.getMessage()));
        }
    }
    
    /**
     * Get routines by status
     * GET /api/routines/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Routine>>> getRoutinesByStatus(@PathVariable RoutineStatus status) {
        log.info("Fetching routines with status: {}", status);
        
        try {
            List<Routine> routines = routineService.getRoutinesByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(routines, "Routines with status " + status + " retrieved successfully"));
        } catch (Exception e) {
            log.error("Error fetching routines by status {}: {}", status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch routines by status: " + e.getMessage()));
        }
    }
    
    /**
     * Get my routines by status
     * GET /api/routines/my/status/{status}
     */
    @GetMapping("/my/status/{status}")
    public ResponseEntity<ApiResponse<List<Routine>>> getMyRoutinesByStatus(@PathVariable RoutineStatus status) {
        String currentUser = getCurrentUsername();
        log.info("Fetching routines for current user: {} with status: {}", currentUser, status);
        
        try {
            List<Routine> routines = routineService.getRoutinesByUserIdAndStatus(currentUser, status);
            return ResponseEntity.ok(ApiResponse.success(routines, "Your routines with status " + status + " retrieved successfully"));
        } catch (Exception e) {
            log.error("Error fetching routines for current user {} with status {}: {}", currentUser, status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch your routines by status: " + e.getMessage()));
        }
    }
    
    /**
     * Soft delete routine
     * DELETE /api/routines/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRoutine(@PathVariable String id) {
        log.info("Deleting routine with ID: {}", id);
        
        try {
            boolean deleted = routineService.deleteRoutine(id);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success(null, "Routine deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Routine not found with ID: " + id));
            }
        } catch (Exception e) {
            log.error("Error deleting routine {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete routine: " + e.getMessage()));
        }
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
        return "anonymous";
    }
    
    /**
     * Check if current user is admin
     */
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN") || 
                                         authority.getAuthority().equals("ADMIN"));
        }
        return false;
    }
}
