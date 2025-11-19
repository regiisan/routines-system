package com.routinesystem.backend.services.impl;

import com.routinesystem.backend.dtos.ExerciseDto;
import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.exceptions.OperationNotAllowedException;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.mappers.ExerciseMapper;
import com.routinesystem.backend.repositories.ExerciseRepository;
import com.routinesystem.backend.services.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseRepository exerciseRepository;


    @Transactional
    @Override
    public ExerciseDto createExercise(ExerciseDto exerciseDto) {
        Exercise exercise = ExerciseMapper.mapToExercise(exerciseDto);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.mapToExerciseDto(savedExercise);
    }

    @Transactional(readOnly=true)
    @Override
    public ExerciseDto getExerciseById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(()-> new ResourceNotFoundException("Exercise not found with id:" + exerciseId));

        return ExerciseMapper.mapToExerciseDto(exercise);
    }

    @Transactional(readOnly=true)
    @Override
    public List<ExerciseDto> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(ExerciseMapper::mapToExerciseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ExerciseDto updateExercise(Long exerciseId, ExerciseDto exerciseDto) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id:" + exerciseId));

        exercise.setName(exerciseDto.getName());
        exercise.setMuscle(exerciseDto.getMuscle());
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.mapToExerciseDto(savedExercise);
    }

    @Override
    public void deleteExercise(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id:" + exerciseId));

        if (exerciseRepository.countRoutinesWithExerciseId(exerciseId) > 0) {
            throw new OperationNotAllowedException("Cannot delete exercise that is in a routine");
        }

        exerciseRepository.delete(exercise);
    }
}
