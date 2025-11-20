package com.routinesystem.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routinesystem.backend.dtos.RoutineDto;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.services.RoutineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoutineController.class)
public class RoutineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoutineService routineService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/routines";
    private final RoutineDto routineDto = new RoutineDto(1L, "Test Routine", Collections.emptyList());
    private final RoutineDto updatedDto = new RoutineDto(1L, "Updated Routine", Collections.emptyList());

    @Test
    void createRoutineShouldReturnCreatedRoutineAndStatus201() throws Exception {
        given(routineService.createRoutine(any(RoutineDto.class))).willReturn(routineDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routineDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(routineDto.getName())))
                .andExpect(jsonPath("$.id", is(routineDto.getId().intValue())));

        verify(routineService, times(1)).createRoutine(any(RoutineDto.class));
    }

    @Test
    void getRoutineByIdShouldReturnRoutineAndStatus200() throws Exception {
        Long id = 1L;
        given(routineService.getRoutineById(id)).willReturn(routineDto);

        mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(routineDto.getId().intValue())))
                .andExpect(jsonPath("$.name", is(routineDto.getName())));

        verify(routineService, times(1)).getRoutineById(id);
    }

    @Test
    void getRoutineByIdNotFoundShouldReturnStatus404() throws Exception {
        Long id = 99L;
        given(routineService.getRoutineById(id)).willThrow(new ResourceNotFoundException("Routine not found"));

        mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound());

        verify(routineService, times(1)).getRoutineById(id);
    }

    @Test
    void getAllRoutinesShouldReturnListOfRoutinesAndStatus200() throws Exception {
        RoutineDto routine2 = new RoutineDto(2L, "Routine 2", Collections.emptyList());
        List<RoutineDto> routineList = Arrays.asList(routineDto, routine2);
        given(routineService.getAllRoutines()).willReturn(routineList);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(routineList.size())))
                .andExpect(jsonPath("$[0].name", is(routineDto.getName())))
                .andExpect(jsonPath("$[1].name", is(routine2.getName())));

        verify(routineService, times(1)).getAllRoutines();
    }

    @Test
    void updateRoutineShouldReturnUpdatedRoutineAndStatus200() throws Exception {
        Long id = 1L;
        given(routineService.updateRoutine(eq(id), any(RoutineDto.class))).willReturn(updatedDto);

        mockMvc.perform(put(BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedDto.getName())));

        verify(routineService, times(1)).updateRoutine(eq(id), any(RoutineDto.class));
    }

    @Test
    void updateRoutineNotFoundShouldReturnStatus404() throws Exception {
        Long id = 99L;
        given(routineService.updateRoutine(eq(id), any(RoutineDto.class)))
                .willThrow(new ResourceNotFoundException("Routine not found"));

        mockMvc.perform(put(BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isNotFound());

        verify(routineService, times(1)).updateRoutine(eq(id), any(RoutineDto.class));
    }

    @Test
    void deleteRoutineShouldReturnSuccessMessageAndStatus200() throws Exception {
        Long id = 1L;
        doNothing().when(routineService).deleteRoutine(id);

        mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(routineService, times(1)).deleteRoutine(id);
    }

    @Test
    void deleteRoutineNotFoundShouldReturnStatus404() throws Exception {
        Long id = 99L;
        doThrow(new ResourceNotFoundException("Routine not found for deletion"))
                .when(routineService).deleteRoutine(id);

        mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound());

        verify(routineService, times(1)).deleteRoutine(id);
    }

}
