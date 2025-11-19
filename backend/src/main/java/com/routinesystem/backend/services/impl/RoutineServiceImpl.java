package com.routinesystem.backend.services.impl;

import com.routinesystem.backend.dtos.RoutineDto;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.exceptions.ResourceNotFoundException;
import com.routinesystem.backend.mappers.RoutineMapper;
import com.routinesystem.backend.repositories.RoutineRepository;
import com.routinesystem.backend.services.RoutineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private RoutineRepository routineRepository;

    @Transactional
    @Override
    public RoutineDto createRoutine(RoutineDto routineDto) {
        Routine routine = RoutineMapper.mapToRoutine(routineDto);
        Routine savedRoutine = routineRepository.save(routine);
        return RoutineMapper.mapToRoutineDto(savedRoutine);
    }

    @Transactional(readOnly=true)
    @Override
    public RoutineDto getRoutineById(Long id) {
        Routine routine = routineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found with id: " + id));

        return RoutineMapper.mapToRoutineDto(routine);
    }

    @Transactional(readOnly=true)
    @Override
    public List<RoutineDto> getAllRoutines() {
        List<Routine> routines = routineRepository.findAll();
        return routines.stream().map(RoutineMapper::mapToRoutineDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoutineDto updateRoutine(Long routineId, RoutineDto routineDto) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found with id: " + routineId));

        routine.setName(routineDto.getName());
        Routine updatedRoutine = routineRepository.save(routine);
        return RoutineMapper.mapToRoutineDto(updatedRoutine);
    }

    @Transactional
    @Override
    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found with id: " + routineId));

        routineRepository.delete(routine);
    }
}
