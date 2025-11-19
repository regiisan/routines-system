package com.routinesystem.backend.mappers;

import com.routinesystem.backend.dtos.RoutineExerciseCreateDto;
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

    public static RoutineExerciseCreateDto mapToRoutineExerciseCreateDto(RoutineExercise routineExercise) {
        return new RoutineExerciseCreateDto(
                routineExercise.getId(),
                routineExercise.getRoutine().getId(),
                routineExercise.getExercise().getId(),
                routineExercise.getSets(),
                routineExercise.getReps(),
                routineExercise.getWeight()
        );
    }

    public static RoutineExercise mapToRoutineExercise(RoutineExerciseDto routineExerciseDto) {

        RoutineExercise re = new RoutineExercise();

        Routine routine = new Routine();
        routine.setId(routineExerciseDto.getRoutineId());

        Exercise exercise = new Exercise();
        exercise.setId(routineExerciseDto.getExerciseId());

        re.setId(routineExerciseDto.getId());
        re.setRoutine(routine);
        re.setExercise(exercise);
        re.setSets(routineExerciseDto.getSets());
        re.setReps(routineExerciseDto.getReps());
        re.setWeight(routineExerciseDto.getWeight());

        return re;
    }

}
