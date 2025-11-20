package com.routinesystem.backend.repositories;

import com.routinesystem.backend.entities.Exercise;
import com.routinesystem.backend.entities.Muscle;
import com.routinesystem.backend.entities.Routine;
import com.routinesystem.backend.entities.RoutineExercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void countRoutinesWithExerciseIdShouldReturnCorrectCount() {
        Exercise exercise = new Exercise(null, "Press banca", Muscle.PECHO);
        exercise = entityManager.persistAndFlush(exercise);
        Routine routine1 = new Routine(null, "Rutina A", null);
        Routine routine2 = new Routine(null, "Rutina B", null);
        routine1 = entityManager.persistAndFlush(routine1);
        routine2 = entityManager.persistAndFlush(routine2);
        RoutineExercise re1 = new RoutineExercise(null, routine1, exercise, 3, 10, 40.0);
        RoutineExercise re2 = new RoutineExercise(null, routine2, exercise, 4, 8, 50.0);

        entityManager.persistAndFlush(re1);
        entityManager.persistAndFlush(re2);

        long count = exerciseRepository.countRoutinesWithExerciseId(exercise.getId());

        assertThat(count).isEqualTo(2);
    }

    @Test
    void countRoutinesWithExerciseIdWhenNoAssociationsShouldReturnZero() {
        Exercise exercise = new Exercise(null, "Sentadilla", Muscle.CUADRICEPS);
        exercise = entityManager.persistAndFlush(exercise);

        long count = exerciseRepository.countRoutinesWithExerciseId(exercise.getId());

        assertThat(count).isZero();
    }
}
