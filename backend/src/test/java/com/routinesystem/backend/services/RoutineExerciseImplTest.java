package com.routinesystem.backend.services;
import com.routinesystem.backend.dtos.RoutineExerciseDto;
import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.entities.RoutineExercise;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.repositories.ExerciseRepository;
import com.routinesystem.backend.repositories.RoutineExerciseRepository;
import com.routinesystem.backend.repositories.RoutineRepository;
import com.routinesystem.backend.services.impl.RoutineExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoutineExerciseImplTest {

    @Mock
    private RoutineExerciseRepository routineExerciseRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private RoutineExerciseServiceImpl routineExerciseServiceImpl;

    private Routine routine;
    private Exercise exercise;
    private RoutineExercise routineExercise;
    private RoutineExerciseDto routineExerciseDto;

    @BeforeEach
    void setUp() {
        routine = new Routine(10L, "Rutina Fuerza", null);
        exercise = new Exercise(20L, "Sentadilla", Muscle.ABDOMINALES);
        routineExercise = new RoutineExercise(1L, routine, exercise, 3, 12, 40.0);
        routineExerciseDto = new RoutineExerciseDto(1L, 10L, "Rutina Fuerza", 20L, "Sentadilla", Muscle.CUADRICEPS, 3, 12, 40.0);
    }

    @Test
    void createRoutineExerciseSuccess() {
        when(routineRepository.findById(10L)).thenReturn(Optional.of(routine));
        when(exerciseRepository.findById(20L)).thenReturn(Optional.of(exercise));
        when(routineExerciseRepository.save(any())).thenReturn(routineExercise);

        RoutineExerciseDto result = routineExerciseServiceImpl.createRoutineExercise(routineExerciseDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10L, result.getRoutineId());
        assertEquals(20L, result.getExerciseId());
        assertEquals("Sentadilla", result.getExerciseName());

        verify(routineExerciseRepository).save(any(RoutineExercise.class));
    }

    @Test
    void getRoutineExerciseByIdSuccess() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.of(routineExercise));

        RoutineExerciseDto result = routineExerciseServiceImpl.getRoutineExerciseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(3, result.getSets());
        assertEquals("Sentadilla", result.getExerciseName());
    }

    @Test
    void getRoutineExerciseByIdNotFound() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> routineExerciseServiceImpl.getRoutineExerciseById(1L));
    }

    @Test
    void updateRoutineExerciseSuccess() {
        RoutineExerciseDto updateDto = new RoutineExerciseDto(1L, 10L, "Rutina Fuerza", 20L, "Sentadilla", Muscle.CUADRICEPS, 4, 10, 50.0);
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.of(routineExercise));
        when(exerciseRepository.findById(20L)).thenReturn(Optional.of(exercise));
        when(routineExerciseRepository.save(any())).thenReturn(routineExercise);

        RoutineExerciseDto result = routineExerciseServiceImpl.updateRoutineExercise(1L, updateDto);

        assertEquals(4, result.getSets());
        assertEquals(10, result.getReps());
        assertEquals(50.0, result.getWeight());
        verify(routineExerciseRepository).save(any(RoutineExercise.class));
    }

    @Test
    void updateRoutineExerciseNotFound() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.empty());

        RoutineExerciseDto updateDto = new RoutineExerciseDto(1L, 10L, "Rutina Fuerza", 20L, "Sentadilla", Muscle.CUADRICEPS, 4, 10, 50.0);

        assertThrows(IllegalArgumentException.class, () -> routineExerciseServiceImpl.updateRoutineExercise(1L, updateDto));
    }

    @Test
    void getAllRoutineExercisesByRoutineSuccess() {
        when(routineExerciseRepository.findByRoutineId(10L)).thenReturn(List.of(routineExercise));

        List<RoutineExerciseDto> result = routineExerciseServiceImpl.getAllRoutineExercisesByRoutine(10L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(3, result.get(0).getSets());
    }

    @Test
    void getAllRoutineExercisesByRoutineReturnsEmpty() {
        when(routineExerciseRepository.findByRoutineId(10L)).thenReturn(List.of());

        List<RoutineExerciseDto> result = routineExerciseServiceImpl.getAllRoutineExercisesByRoutine(10L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteRoutineExerciseSuccess() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.of(routineExercise));

        routineExerciseServiceImpl.deleteRoutineExercise(1L);

        verify(routineExerciseRepository).delete(routineExercise);
    }

    @Test
    void deleteRoutineExerciseNotFound() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> routineExerciseServiceImpl.deleteRoutineExercise(1L));
    }
}
