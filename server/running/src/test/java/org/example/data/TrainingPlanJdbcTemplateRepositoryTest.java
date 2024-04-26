package org.example.data;

import org.example.models.TrainingPlan;
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
class TrainingPlanJdbcTemplateRepositoryTest {

    @Autowired
    private TrainingPlanJdbcTemplateRepository repository;

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
    void shouldFindByTrainingPlanId() throws DataAccessException {
        TrainingPlan result = repository.findTrainingPlanByTrainingPlanId(1);

        assertNotNull(result);
        assertEquals("2024 NYC Marathon", result.getName());
    }

    @Test
    void shouldNotFindByTrainingPlanIdWhenTrainingIdDoesNotExist() throws DataAccessException {
        TrainingPlan result = repository.findTrainingPlanByTrainingPlanId(3);

        assertNull(result);
    }

    @Test
    void shouldFindByAppUserId() throws DataAccessException {
        List<TrainingPlan> result = repository.findTrainingPlanByAppUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2024 NYC Marathon", result.get(0).getName());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserDoesNotHaveAnyTrainingPlans() throws DataAccessException {
        List<TrainingPlan> result = repository.findTrainingPlanByAppUserId(2);

        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserIdDoesNotExist() throws DataAccessException {
        List<TrainingPlan> result = repository.findTrainingPlanByAppUserId(3);

        assertEquals(0, result.size());
    }

    @Test
    void shouldAddTrainingPlan() throws DataAccessException {
        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(2);
        expected.setName("2025 Boston Marathon");
        expected.setStartDate(LocalDate.of(2025, 1, 1));
        expected.setEndDate(LocalDate.of(2025, 4, 25));
        expected.setDescription(null);

        TrainingPlan actual = repository.addTrainingPlan(expected);

        assertNotNull(actual);
        assertEquals(2, actual.getTrainingPlanId());
    }

    @Test
    void shouldUpdateTrainingPlan() throws DataAccessException {
        TrainingPlan expected = repository.findTrainingPlanByTrainingPlanId(1);
        expected.setName("2025 NYC Marathon");

        boolean actual = repository.updateTrainingPlan(expected);

        assertTrue(actual);
        assertEquals("2025 NYC Marathon", expected.getName());
        assertEquals(LocalDate.of(2024, 7, 1), expected.getStartDate());
    }

    @Test
    void shouldDeleteTrainingPlan() throws DataAccessException {
        boolean expected = repository.deleteTrainingPlan(1);
        List<TrainingPlan> actual = repository.findTrainingPlanByAppUserId(1);

        assertTrue(expected);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldNotDeleteTrainingPlanWhenTrainingPlanDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteTrainingPlan(2);

        assertFalse(expected);
    }

}