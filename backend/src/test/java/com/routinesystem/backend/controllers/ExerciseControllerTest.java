package com.routinesystem.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routinesystem.backend.dtos.ExerciseDto;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.services.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private ExerciseController exerciseController;

    private ObjectMapper objectMapper;
    private ExerciseDto exerciseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController).build();
        objectMapper = new ObjectMapper();
        exerciseDto = new ExerciseDto(1L, "Sentadilla", Muscle.CUADRICEPS);
    }

    @Test
    void createExerciseSuccess() throws Exception {
        when(exerciseService.createExercise(any())).thenReturn(exerciseDto);

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sentadilla"));

        verify(exerciseService).createExercise(any());
    }

    @Test
    void getExerciseByIdSuccess() throws Exception {
        when(exerciseService.getExerciseById(1L)).thenReturn(exerciseDto);

        mockMvc.perform(get("/api/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sentadilla"))
                .andExpect(jsonPath("$.muscle").value("CUADRICEPS"));
    }

    @Test
    void getAllExercisesSuccess() throws Exception {
        when(exerciseService.getAllExercises()).thenReturn(List.of(exerciseDto));

        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void updateExerciseSuccess() throws Exception {
        ExerciseDto updateDto = new ExerciseDto(1L, "Press Banca", Muscle.PECHO);
        when(exerciseService.updateExercise(eq(1L), any())).thenReturn(updateDto);

        mockMvc.perform(put("/api/exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Press Banca"))
                .andExpect(jsonPath("$.muscle").value("PECHO"));
    }

    @Test
    void deleteExerciseSuccess() throws Exception {
        doNothing().when(exerciseService).deleteExercise(1L);

        mockMvc.perform(delete("/api/exercises/1"))
                .andExpect(status().isNoContent());

        verify(exerciseService).deleteExercise(1L);
    }

    @Test
    void getMusclesSuccess() throws Exception {
        mockMvc.perform(get("/api/exercises/muscles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(Muscle.values()[0].name()));
    }
}
