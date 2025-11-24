package com.routinesystem.backend.mappers;

import com.routinesystem.backend.dtos.RoutineExerciseDto;
import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.entities.RoutineExercise;

public class RoutineExerciseMapper {

    public static RoutineExerciseDto mapToRoutineExerciseDto(RoutineExercise routineExercise) {
        return new RoutineExerciseDto(
                routineExercise.getId(),
                routineExercise.getRoutine().getId(),
                routineExercise.getRoutine().getName(),
                routineExercise.getExercise().getId(),
                routineExercise.getExercise().getName(),
                routineExercise.getExercise().getMuscle(),
                routineExercise.getSets(),
                routineExercise.getReps(),
                routineExercise.getWeight()
        );
    }

    public static RoutineExercise mapToRoutineExercise(RoutineExerciseDto routineExerciseDto, Routine routine, Exercise exercise) {

        RoutineExercise routineExercise = new RoutineExercise();

        routineExercise.setRoutine(routine);
        routineExercise.setExercise(exercise);
        routineExercise.setSets(routineExerciseDto.getSets());
        routineExercise.setReps(routineExerciseDto.getReps());
        routineExercise.setWeight(routineExerciseDto.getWeight());

        return routineExercise;
    }

}
