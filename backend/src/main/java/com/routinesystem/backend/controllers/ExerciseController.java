package com.routinesystem.backend.controllers;

import com.routinesystem.backend.dtos.ExerciseDto;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.services.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto) {
        ExerciseDto exercise = exerciseService.createExercise(exerciseDto);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable("id") Long exerciseId){
        ExerciseDto exercise = exerciseService.getExerciseById(exerciseId);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercises(){
        List<ExerciseDto> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable("id") Long exerciseId, @RequestBody ExerciseDto exerciseDto){
        ExerciseDto exercise = exerciseService.updateExercise(exerciseId, exerciseDto);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable("id") Long exerciseId){
        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/muscles")
    public ResponseEntity<Muscle[]> getMuscles() {
        return ResponseEntity.ok(Muscle.values());
    }


}
