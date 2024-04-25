package org.example.data;

import org.example.models.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkoutJdbcTemplateRepositoryTest {

    @Autowired
    private WorkoutJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state()");
        }
    }

    @Test
    void shouldFindByWorkoutId() throws DataAccessException {
        Workout result = repository.findWorkoutByWorkoutId(1);

        assertNotNull(result);
        assertEquals(LocalDate.of(2024, 7, 1), result.getDate());
    }

    @Test
    void shouldNotFindByWorkoutIdWhenWorkoutIdDoesNotExist() throws DataAccessException {
        Workout result = repository.findWorkoutByWorkoutId(3);

        assertNull(result);
    }

    @Test
    void shouldFindByTrainingPlanId() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByTrainingPlanId(1);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void shouldNotFindByTrainingPlanIdWhenTrainingPlanIdDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByTrainingPlanId(2);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByTrainingPlanName() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByTrainingPlanName("2024 NYC Marathon");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void shouldNotFindByTrainingPlanNameWhenTrainingPlanNameDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByTrainingPlanName("2025 NYC Marathon");

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByWorkoutTypeId() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByWorkoutTypeId(7);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getDistance());
    }

    @Test
    void shouldNotFindByWorkoutTypeIdWhenWorkoutTypeIdDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByWorkoutTypeId(1);

        assertEquals(0, result.size());
    }

}