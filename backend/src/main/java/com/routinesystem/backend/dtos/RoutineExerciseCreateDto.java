package com.routinesystem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExerciseCreateDto {

    private Long id;
    private Long routineId;
    private Long exerciseId;
    private Integer sets;
    private Integer reps;
    private Double weight;
}
