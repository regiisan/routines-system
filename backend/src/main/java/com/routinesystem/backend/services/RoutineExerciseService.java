package com.routinesystem.backend.services;

import com.routinesystem.backend.dtos.RoutineExerciseDto;
import java.util.List;

public interface RoutineExerciseService {
    RoutineExerciseDto createRoutineExercise(RoutineExerciseDto routineExerciseDto);
    RoutineExerciseDto getRoutineExerciseById(Long routineExerciseId);
    List<RoutineExerciseDto> getAllRoutineExercisesByRoutine(Long routineId);
    RoutineExerciseDto updateRoutineExercise(Long routineExerciseId, RoutineExerciseDto routineExerciseDto);
    RoutineExerciseDto addExercise(Long id,RoutineExerciseDto routineExerciseDto);
    void deleteRoutineExercise(Long routineExerciseId);
}
