package com.routinesystem.backend.services;

import com.routinesystem.backend.dtos.RoutineDto;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.repositories.RoutineRepository;
import com.routinesystem.backend.services.impl.RoutineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoutineServiceImplTest {

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private RoutineServiceImpl routineServiceImpl;

    private Routine routine;
    private RoutineDto routineDto;

    @BeforeEach
    void setUp() {
        routine = new Routine(1L, "Routine 1", Collections.emptyList());
        routineDto = new RoutineDto(1L, "Routine 1", Collections.emptyList());
    }

    @Test
    void createRoutineSucces(){
        when(routineRepository.save(any(Routine.class))).thenReturn(routine);

        RoutineDto result = routineServiceImpl.createRoutine(routineDto);

        verify(routineRepository, times(1)).save(any(Routine.class));
        assertNotNull(result);
        assertEquals(routineDto.getName(), result.getName());
    }

    @Test
    void getRoutineByIdFound(){
        when(routineRepository.findById(routine.getId())).thenReturn(Optional.of(routine));

        RoutineDto result = routineServiceImpl.getRoutineById(routineDto.getId());

        assertNotNull(result);
        assertEquals(routine.getId(), result.getId());
        assertEquals(routine.getName(), result.getName());
    }

    @Test
    void getRoutineByIdNotFoundThrowsException() {
        Long id = 99L;

        when(routineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> routineServiceImpl.getRoutineById(id));
    }

    @Test
    void getAllRoutinesReturnsAList() {
        Routine routine2 = new Routine(2L, "Routine 2", null);
        List<Routine> routines = Arrays.asList(routine, routine2);
        when(routineRepository.findAll()).thenReturn(routines);

        List<RoutineDto> results = routineServiceImpl.getAllRoutines();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Routine 1", results.get(0).getName());
        assertEquals("Routine 2", results.get(1).getName());
    }

    @Test
    void updateRoutine_Success() {
        RoutineDto updatedDto = new RoutineDto(1L, "Routine Updated", null);
        Routine updatedRoutineEntity = new Routine(1L, updatedDto.getName(), null);
        when(routineRepository.findById(routine.getId())).thenReturn(Optional.of(routine));
        when(routineRepository.save(any(Routine.class))).thenReturn(updatedRoutineEntity);

        RoutineDto result = routineServiceImpl.updateRoutine(routine.getId(), updatedDto);

        verify(routineRepository, times(1)).findById(routine.getId());
        verify(routineRepository, times(1)).save(any(Routine.class));
        assertNotNull(result);
        assertEquals(updatedDto.getName(), result.getName());
        assertEquals(routine.getId(), result.getId());
    }

    @Test
    void updateRoutineNotFoundThrowsException() {
        Long id = 99L;
        when(routineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> routineServiceImpl.updateRoutine(id, routineDto));

        verify(routineRepository, never()).save(any(Routine.class));
    }

    @Test
    void deleteRoutineSuccess() {
        Long id = 1L;
        when(routineRepository.findById(id)).thenReturn(Optional.of(routine));
        doNothing().when(routineRepository).delete(any(Routine.class));

        routineServiceImpl.deleteRoutine(id);

        verify(routineRepository, times(1)).findById(id);
        verify(routineRepository, times(1)).delete(routine);
    }

    @Test
    void deleteRoutineNotFoundThrowsException() {
        Long id = 99L;
        when(routineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> routineServiceImpl.deleteRoutine(id));

        verify(routineRepository, never()).delete(any(Routine.class));
    }
}
