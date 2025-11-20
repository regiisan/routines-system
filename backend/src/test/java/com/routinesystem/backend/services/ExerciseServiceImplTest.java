package com.routinesystem.backend.services;

import com.routinesystem.backend.dtos.ExerciseDto;
import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.exceptions.OperationNotAllowedException;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.repositories.ExerciseRepository;
import com.routinesystem.backend.services.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseServiceImpl;

    private Exercise exercise;
    private ExerciseDto exerciseDto;

    @BeforeEach
    void setUp() {
        exercise = new Exercise(1L, "Exercise 1", Muscle.ABDOMINALES);
        exerciseDto = new ExerciseDto(1L, "Exercise 1", Muscle.ABDOMINALES);
    }

    @Test
    void createExerciseSucces(){
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseDto result = exerciseServiceImpl.createExercise(exerciseDto);

        verify(exerciseRepository, times(1)).save(any(Exercise.class));
        assertNotNull(result);
        assertEquals(exerciseDto.getName(), result.getName());
    }

    @Test
    void getExerciseByIdFound() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        ExerciseDto result = exerciseServiceImpl.getExerciseById(1L);

        assertEquals("Exercise 1", result.getName());
        assertEquals(Muscle.ABDOMINALES, result.getMuscle());
    }

    @Test
    void getExerciseByIdNotFound() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                exerciseServiceImpl.getExerciseById(1L)
        );
    }

    @Test
    void getAllExercisesReturnsAList() {
        Exercise exercise2 = new Exercise(2L, "Exercise 2", Muscle.BICEPS);
        when(exerciseRepository.findAll()).thenReturn(List.of(exercise, exercise2));

        List<ExerciseDto> list = exerciseServiceImpl.getAllExercises();

        assertEquals(2, list.size());
        verify(exerciseRepository).findAll();
    }

    @Test
    void updateExerciseSucces() {
        ExerciseDto updated = new ExerciseDto(1L, "Updated", Muscle.BICEPS);

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any())).thenReturn(exercise);

        ExerciseDto result = exerciseServiceImpl.updateExercise(1L, updated);

        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getMuscle(), result.getMuscle());
    }

    @Test
    void deleteExerciseSucces() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.countRoutinesWithExerciseId(1L)).thenReturn(0L);

        exerciseServiceImpl.deleteExercise(1L);

        verify(exerciseRepository).delete(exercise);
    }

    @Test
    void deleteExerciseNotAllowed() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.countRoutinesWithExerciseId(1L)).thenReturn(5L);

        assertThrows(OperationNotAllowedException.class, () -> exerciseServiceImpl.deleteExercise(1L));
        verify(exerciseRepository, never()).delete(any());
    }


}
