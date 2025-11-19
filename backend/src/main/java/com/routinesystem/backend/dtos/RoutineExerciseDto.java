package com.routinesystem.backend.dtos;

import com.routinesystem.backend.entities.Muscle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExerciseDto {

    private Long id;
    private Long routineId;
    private String routineName;
    private Long exerciseId;
    private String exerciseName;
    private Muscle muscle;
    private Integer sets;
    private Integer reps;
    private Double weight;
}
