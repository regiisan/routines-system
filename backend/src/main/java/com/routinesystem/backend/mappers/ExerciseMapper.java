package com.routinesystem.backend.mappers;

import com.routinesystem.backend.dtos.ExerciseDto;
import com.routinesystem.backend.entities.Exercise;

public class ExerciseMapper {

    public static ExerciseDto mapToExerciseDto(Exercise exercise){
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscle()
        );
    }

    public static Exercise mapToExercise(ExerciseDto exerciseDto){
        return new Exercise(
                exerciseDto.getId(),
                exerciseDto.getName(),
                exerciseDto.getMuscle()
        );
    }
}
