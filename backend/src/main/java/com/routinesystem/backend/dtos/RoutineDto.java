package com.routinesystem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDto {

    private Long id;
    private String name;
    private List<RoutineExerciseDto> routineExercisesDto;

}
