package com.routinesystem.backend.repositories;

import com.routinesystem.backend.entities.Routine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RoutineRepositoryTest {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final Routine routine = new Routine(null, "Routine 1", null);

    @Test
    void saveRoutineShouldSaveRoutine() {
        Routine savedRoutine = routineRepository.save(routine);

        assertThat(savedRoutine).isNotNull();
        assertThat(savedRoutine.getId()).isPositive();
        entityManager.clear();
    }

    @Test
    void findByIdShouldReturnRoutine() {
        Routine persistedRoutine = entityManager.persistAndFlush(routine);

        Optional<Routine> foundRoutine = routineRepository.findById(persistedRoutine.getId());

        assertTrue(foundRoutine.isPresent());
        assertThat(foundRoutine.get().getName()).isEqualTo(persistedRoutine.getName());
    }

    @Test
    void findByIdNotFoundShouldReturnEmptyOptional() {
        Optional<Routine> foundRoutine = routineRepository.findById(99L);

        assertTrue(foundRoutine.isEmpty());
    }

    @Test
    void findAllShouldReturnAllRoutines() {
        entityManager.persist(routine);
        entityManager.persist(new Routine(null, "Routine 2", null));
        entityManager.flush();

        List<Routine> routines = routineRepository.findAll();

        assertThat(routines).isNotNull();
        assertThat(routines).hasSize(2);
        assertThat(routines.get(0).getName()).isEqualTo("Routine 1");
        assertThat(routines.get(1).getName()).isEqualTo("Routine 2");
    }

    @Test
    void deleteById_ShouldRemoveRoutine() {
        Routine persistedRoutine = entityManager.persistAndFlush(routine);
        Long id = persistedRoutine.getId();

        routineRepository.deleteById(id);

        Optional<Routine> foundRoutine = routineRepository.findById(id);
        assertTrue(foundRoutine.isEmpty());
    }
}
