package com.routinesystem.backend.controllers;

import com.routinesystem.backend.dtos.RoutineExerciseDto;
import com.routinesystem.backend.services.RoutineExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/routine-exercises")
public class RoutineExerciseController {

    private final RoutineExerciseService routineExerciseService;

    @PostMapping
    public ResponseEntity<RoutineExerciseDto> createRoutineExercise(@RequestBody RoutineExerciseDto routineExerciseDto) {
        RoutineExerciseDto routineExercise = routineExerciseService.createRoutineExercise(routineExerciseDto);
        return new ResponseEntity<>(routineExercise, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoutineExerciseDto> getRoutineExerciseById(@PathVariable("id") Long id) {
        RoutineExerciseDto routineExercise = routineExerciseService.getRoutineExerciseById(id);
        return ResponseEntity.ok(routineExercise);
    }

    @GetMapping("/routine/{id}")
    public ResponseEntity<List<RoutineExerciseDto>> getAllRoutineExercisesByRoutine(@PathVariable("id") Long id) {
        List<RoutineExerciseDto> routineExercises = routineExerciseService.getAllRoutineExercisesByRoutine(id);
        return ResponseEntity.ok(routineExercises);
    }

    @PutMapping("{id}")
    public ResponseEntity<RoutineExerciseDto> updateRoutineExercise(@PathVariable("id") Long routineExerciseId, @RequestBody RoutineExerciseDto routineExerciseDto) {
        RoutineExerciseDto routineExercise = routineExerciseService.updateRoutineExercise(routineExerciseId, routineExerciseDto);
        return ResponseEntity.ok(routineExercise);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoutineExercise(@PathVariable("id") Long routineExerciseId) {
        routineExerciseService.deleteRoutineExercise(routineExerciseId);
        return ResponseEntity.noContent().build();
    }

}
