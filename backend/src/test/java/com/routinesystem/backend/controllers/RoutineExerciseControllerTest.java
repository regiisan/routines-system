package com.routinesystem.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routinesystem.backend.dtos.RoutineExerciseDto;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.services.RoutineExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoutineExerciseController.class)
class RoutineExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoutineExerciseService routineExerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoutineExerciseDto routineExerciseDto;

    @BeforeEach
    void setUp() {
        routineExerciseDto = new RoutineExerciseDto(1L, 10L, "Rutina Fuerza", 20L, "Sentadilla", Muscle.CUADRICEPS, 3, 12, 40.0);
    }


    @Test
    void createRoutineExerciseSuccess() throws Exception {
        when(routineExerciseService.createRoutineExercise(any())).thenReturn(routineExerciseDto);

        mockMvc.perform(post("/api/routine-exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routineExerciseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.exerciseName").value("Sentadilla"));

        verify(routineExerciseService).createRoutineExercise(any());
    }

    @Test
    void createRoutineExerciseNotFound() throws Exception {
        when(routineExerciseService.createRoutineExercise(any()))
                .thenThrow(new ResourceNotFoundException("Routine not found"));

        mockMvc.perform(post("/api/routine-exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routineExerciseDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRoutineExerciseByIdSuccess() throws Exception {
        when(routineExerciseService.getRoutineExerciseById(1L)).thenReturn(routineExerciseDto);

        mockMvc.perform(get("/api/routine-exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sets").value(3))
                .andExpect(jsonPath("$.exerciseId").value(20L));
    }

    @Test
    void getRoutineExerciseByIdNotFound() throws Exception {
        when(routineExerciseService.getRoutineExerciseById(1L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/routine-exercises/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllByRoutineSuccess() throws Exception {
        when(routineExerciseService.getAllRoutineExercisesByRoutine(10L))
                .thenReturn(List.of(routineExerciseDto));

        mockMvc.perform(get("/api/routine-exercises/routine/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].routineId").value(10L))
                .andExpect(jsonPath("$[0].reps").value(12));
    }

    @Test
    void getAllRoutineExercisesByRoutineNotFound() throws Exception {
        when(routineExerciseService.getAllRoutineExercisesByRoutine(10L))
                .thenThrow(new ResourceNotFoundException("Routine not found"));

        mockMvc.perform(get("/api/routine-exercises/routine/10"))
                .andExpect(status().isNotFound());
    }


    @Test
    void updateRoutineExerciseSuccess() throws Exception {
        RoutineExerciseDto updateDto = new RoutineExerciseDto(1L, 10L, "Rutina Fuerza", 20L, "Sentadilla", Muscle.CUADRICEPS, 4, 10, 50.0);

        when(routineExerciseService.updateRoutineExercise(eq(1L), any()))
                .thenReturn(updateDto);

        mockMvc.perform(put("/api/routine-exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sets").value(4))
                .andExpect(jsonPath("$.weight").value(50.0));
    }

    @Test
    void updateRoutineExerciseNotFound() throws Exception {
        when(routineExerciseService.updateRoutineExercise(any(), any()))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(put("/api/routine-exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routineExerciseDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoutineExerciseSuccess() throws Exception {
        doNothing().when(routineExerciseService).deleteRoutineExercise(1L);

        mockMvc.perform(delete("/api/routine-exercises/1"))
                .andExpect(status().isNoContent());

        verify(routineExerciseService).deleteRoutineExercise(1L);
    }


    @Test
    void deleteRoutineExerciseNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Not found"))
                .when(routineExerciseService).deleteRoutineExercise(1L);

        mockMvc.perform(delete("/api/routine-exercises/1"))
                .andExpect(status().isNotFound());
    }
}
