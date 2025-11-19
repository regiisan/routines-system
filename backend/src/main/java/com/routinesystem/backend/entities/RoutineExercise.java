package com.routinesystem.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routine_exercises")
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="routine_id")
    private Routine routine;

    @ManyToOne
    @JoinColumn(name="exercise_id")
    private Exercise exercise;

    @Min(1)
    @Column(nullable = false)
    private Integer sets;

    @Min(1)
    @Column(nullable = false)
    private Integer reps;

    @Column
    private Double weight;

}
