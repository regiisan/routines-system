package com.routinesystem.backend.services.impl;

import com.routinesystem.backend.dtos.RoutineExerciseDto;
import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.entities.RoutineExercise;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.mappers.RoutineExerciseMapper;
import com.routinesystem.backend.repositories.ExerciseRepository;
import com.routinesystem.backend.repositories.RoutineExerciseRepository;
import com.routinesystem.backend.repositories.RoutineRepository;
import com.routinesystem.backend.services.RoutineExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoutineExerciseServiceImpl implements RoutineExerciseService {

    private RoutineExerciseRepository routineExerciseRepository;
    private RoutineRepository routineRepository;
    private ExerciseRepository exerciseRepository;

    @Transactional
    @Override
    public RoutineExerciseDto createRoutineExercise(RoutineExerciseDto routineExerciseDto) {
        Routine routine = routineRepository.findById(routineExerciseDto.getRoutineId())
                .orElseThrow(() -> new IllegalArgumentException("Routine not found"));

        Exercise exercise = exerciseRepository.findById(routineExerciseDto.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        RoutineExercise routineExercise = new RoutineExercise();
        routineExercise.setRoutine(routine);
        routineExercise.setExercise(exercise);
        routineExercise.setSets(routineExerciseDto.getSets());
        routineExercise.setReps(routineExerciseDto.getReps());
        routineExercise.setWeight(routineExerciseDto.getWeight());
        RoutineExercise savedRoutineExercise = routineExerciseRepository.save(routineExercise);
        return RoutineExerciseMapper.mapToRoutineExerciseDto(savedRoutineExercise);
    }

    @Transactional(readOnly=true)
    @Override
    public RoutineExerciseDto getRoutineExerciseById(Long routineExerciseId) {
        RoutineExercise routineExercise = routineExerciseRepository.findById(routineExerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineExercise not found with id: " + routineExerciseId));

        return RoutineExerciseMapper.mapToRoutineExerciseDto(routineExercise);
    }

    @Transactional(readOnly=true)
    @Override
    public List<RoutineExerciseDto> getAllRoutineExercisesByRoutine(Long routineId) {
        List<RoutineExercise> routineExercises = routineExerciseRepository.findByRoutineId(routineId);
        return routineExercises.stream().map(RoutineExerciseMapper::mapToRoutineExerciseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoutineExerciseDto updateRoutineExercise(Long routineExerciseId, RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = routineExerciseRepository.findById(routineExerciseId)
                .orElseThrow(() -> new IllegalArgumentException("RoutineExercise not found with id: " + routineExerciseId));

        if (routineExerciseDto.getExerciseId() != null) {
            Exercise exercise = exerciseRepository.findById(routineExerciseDto.getExerciseId())
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found with id: " + routineExerciseDto.getExerciseId()));
            routineExercise.setExercise(exercise);
        }

        routineExercise.setReps(routineExerciseDto.getReps());
        routineExercise.setSets(routineExerciseDto.getSets());
        routineExercise.setWeight(routineExerciseDto.getWeight());
        RoutineExercise updatedRoutineExercise = routineExerciseRepository.save(routineExercise);
        return RoutineExerciseMapper.mapToRoutineExerciseDto(updatedRoutineExercise);
    }

    @Transactional
    @Override
    public RoutineExerciseDto addExercise(Long exerciseId, RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = routineExerciseRepository.findById(routineExerciseDto.getRoutineId())
                .orElseThrow(() -> new IllegalArgumentException("RoutineExercise not found with id: " + routineExerciseDto.getRoutineId()));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("RoutineExercise not found with id: " + exerciseId));

        routineExercise.setExercise(exercise);
        RoutineExercise updatedRoutineExercise = routineExerciseRepository.save(routineExercise);
        return RoutineExerciseMapper.mapToRoutineExerciseDto(updatedRoutineExercise);
    }

    @Override
    public void deleteRoutineExercise(Long routineExerciseId) {
        RoutineExercise routineExercise = routineExerciseRepository.findById(routineExerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineExercise not found with id: " + routineExerciseId));

        routineExerciseRepository.delete(routineExercise);
    }
}
