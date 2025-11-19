package com.routinesystem.backend.repositories;

import com.routinesystem.backend.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT COUNT(re) FROM RoutineExercise re WHERE re.exercise.id = :exerciseId")
    long countRoutinesWithExerciseId(@Param("exerciseId") Long exerciseId);
}
