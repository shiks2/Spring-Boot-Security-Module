package com.example.backend.demo_login.Routine;

import com.example.backend.demo_login.Enum.RoutineStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {
    
    // Find all routines by userId (not soft deleted)
    @Query("{'userId': ?0, 'deletedBy': null}")
    List<Routine> findByUserIdAndNotDeleted(String userId);
    
    // Find routine by id (not soft deleted)
    @Query("{'_id': ?0, 'deletedBy': null}")
    Optional<Routine> findByIdAndNotDeleted(String id);
    
    // Find all routines (not soft deleted)
    @Query("{'deletedBy': null}")
    List<Routine> findAllNotDeleted();
    
    // Check if routine exists by routineId and userId (not soft deleted)
    @Query("{'routineId': ?0, 'userId': ?1, 'deletedBy': null}")
    boolean existsByRoutineIdAndUserIdAndNotDeleted(String routineId, String userId);
    
    // Find by routineId and userId (not soft deleted)
    @Query("{'routineId': ?0, 'userId': ?1, 'deletedBy': null}")
    Optional<Routine> findByRoutineIdAndUserIdAndNotDeleted(String routineId, String userId);
    
    // Find routines by status (not soft deleted)
    @Query("{'routineStatus': ?0, 'deletedBy': null}")
    List<Routine> findByRoutineStatusAndNotDeleted(RoutineStatus status);
    
    // Find routines by userId and status (not soft deleted)
    @Query("{'userId': ?0, 'routineStatus': ?1, 'deletedBy': null}")
    List<Routine> findByUserIdAndRoutineStatusAndNotDeleted(String userId, RoutineStatus status);
}
