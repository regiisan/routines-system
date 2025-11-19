package com.routinesystem.backend.controllers;

import com.routinesystem.backend.dtos.RoutineDto;
import com.routinesystem.backend.services.RoutineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping
    public ResponseEntity<RoutineDto> createRoutine(@RequestBody RoutineDto routineDto){
        RoutineDto routine = routineService.createRoutine(routineDto);
        return new ResponseEntity<>(routine, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoutineDto> getRoutineById(@PathVariable("id") Long id){
        RoutineDto routine = routineService.getRoutineById(id);
        return ResponseEntity.ok(routine);
    }

    @GetMapping
    public ResponseEntity<List<RoutineDto>> getAllRoutines(){
        List<RoutineDto> routines = routineService.getAllRoutines();
        return ResponseEntity.ok(routines);
    }

    @PutMapping("{id}")
    public ResponseEntity<RoutineDto> updateRoutine(@PathVariable("id") Long routineId, @RequestBody RoutineDto routineDto){
        RoutineDto routine = routineService.updateRoutine(routineId, routineDto);
        return ResponseEntity.ok(routine);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<String> deleteRoutine(@PathVariable("id") Long routineId){
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }
}
