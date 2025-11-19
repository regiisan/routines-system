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
public class ExerciseDto {

    private Long id;
    private String name;
    private Muscle muscle;

}
