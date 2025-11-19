package com.routinesystem.backend.services;

import com.routinesystem.backend.dtos.ExerciseDto;

import java.util.List;

public interface ExerciseService {
    ExerciseDto createExercise(ExerciseDto exerciseDto);
    ExerciseDto getExerciseById(Long exerciseId);
    List<ExerciseDto> getAllExercises();
    ExerciseDto updateExercise(Long exerciseId, ExerciseDto exerciseDto);
    void deleteExercise(Long exerciseId);
}
