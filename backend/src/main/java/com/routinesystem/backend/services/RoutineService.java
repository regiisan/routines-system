package com.routinesystem.backend.services;

import com.routinesystem.backend.dtos.RoutineDto;

import java.util.List;

public interface RoutineService {
    RoutineDto createRoutine(RoutineDto routineDto);
    RoutineDto getRoutineById(Long id);
    List<RoutineDto> getAllRoutines();
    RoutineDto updateRoutine(Long routineId, RoutineDto routineDto);
    void deleteRoutine(Long routineId);
}
