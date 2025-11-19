package com.routinesystem.backend.mappers;

import com.routinesystem.backend.dtos.RoutineDto;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.entities.RoutineExercise;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoutineMapper {

    public static RoutineDto mapToRoutineDto(Routine routine) {
        List<RoutineExercise> exercises = routine.getRoutineExercises();
        if (exercises == null) {
            exercises = Collections.emptyList();
        }
        return new RoutineDto(
                routine.getId(),
                routine.getName(),
                exercises
                        .stream()
                        .map(RoutineExerciseMapper::mapToRoutineExerciseDto)
                        .collect(Collectors.toList())
        );
    }

    public static Routine mapToRoutine(RoutineDto routineDto) {
        Routine routine = new Routine();

        routine.setId(routineDto.getId());
        routine.setName(routineDto.getName());
        routine.setRoutineExercises(null);

        return routine;
    }
}
