package org.example.data;

import org.example.models.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
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
        assertEquals(3, result.get(0).getDistance());
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
        assertEquals(3, result.get(0).getDistance());
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

    @Test
    void shouldFindByAppUserId() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByAppUserId(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getDistance());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserIdDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByAppUserId(2);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByAppUserUsername() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByAppUserUsername("NutellaPatella");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getDistance());
    }

    @Test
    void shouldNotFindByAppUserUsernameWhenAppUserUsernameDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByAppUserUsername("CoachNutellaPatella");

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByWorkoutDate() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByWorkoutDate(LocalDate.of(2024, 7, 1));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getDistance());
    }

    @Test
    void shouldNotFindByWorkoutDateWhenWorkoutDateDoesNotExist() throws DataAccessException {
        List<Workout> result = repository.findWorkoutByWorkoutDate(LocalDate.of(2024, 7, 2));

        assertEquals(0, result.size());
    }

    @Test
    void shouldAddWorkout() throws DataAccessException {
        Workout expected = new Workout();
        expected.setAppUserId(1);
        expected.setWorkoutTypeId(4);
        expected.setDate(LocalDate.of(2024, 7, 2));
        expected.setDistance(4.5);
        expected.setUnit("miles");
        expected.setDescription("1 mile warm-up followed by 3 miles in Zone 4 (approximately 90-100% of FTHR).");
        expected.setEffort("Zone 4");
        expected.setTrainingPlanId(1);

        Workout actual = repository.addWorkout(expected);
        List<Workout> allWorkouts = repository.findWorkoutByTrainingPlanId(1);

        assertNotNull(actual);
        assertEquals(3, allWorkouts.size());
    }

    @Test
    void shouldUpdateWorkout() throws DataAccessException {
        Workout expected = repository.findWorkoutByWorkoutId(1);
        expected.setDescription("An easy base run to increase your weekly mileage and shake out your legs from your last race.");

        boolean actual = repository.updateWorkout(expected);

        assertTrue(actual);
        assertEquals("An easy base run to increase your weekly mileage and shake out your legs from your last race.", expected.getDescription());
        assertEquals(3, expected.getDistance());
    }

    @Test
    void shouldDeleteWorkout() throws DataAccessException {
        boolean expected = repository.deleteWorkout(1);
        List<Workout> actual = repository.findWorkoutByTrainingPlanId(1);

        assertTrue(expected);
        assertEquals(1, actual.size());
        assertEquals(12, actual.get(0).getDistance());
    }

    @Test
    void shouldNotDeleteWorkoutWhenWorkoutDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteWorkout(3);

        assertFalse(expected);
    }

}