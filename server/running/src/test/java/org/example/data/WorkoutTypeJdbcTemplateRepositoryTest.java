package org.example.data;

import org.example.models.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkoutTypeJdbcTemplateRepositoryTest {

    @Autowired
    private WorkoutTypeJdbcTemplateRepository repository;

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
    void shouldFindAllWorkoutType() throws DataAccessException {
        List<WorkoutType> result = repository.findAllWorkoutType();

        assertNotNull(result);
        assertEquals(7, result.size());
    }

    @Test
    void shouldFindWorkoutTypeByWorkoutTypeId() throws DataAccessException {
        WorkoutType result = repository.findWorkoutTypeByWorkoutTypeId(1);

        assertNotNull(result);
        assertEquals("Intervals", result.getName());
    }

    @Test
    void shouldNotFindWorkoutTypeByWorkoutTypeIdWhenWorkoutWorkoutTypeIdDoesNotExist() throws DataAccessException {
        WorkoutType result = repository.findWorkoutTypeByWorkoutTypeId(8);

        assertNull(result);
    }

    @Test
    void shouldFindWorkoutTypeByWorkoutTypeName() throws DataAccessException {
        WorkoutType result = repository.findWorkoutTypeByWorkoutTypeName("Intervals");

        assertNotNull(result);
        assertEquals(1, result.getWorkoutTypeId());
    }

    @Test
    void shouldNotFindWorkoutTypeByNameWhenNameDoesNotExist() throws DataAccessException {
        WorkoutType result = repository.findWorkoutTypeByWorkoutTypeName("Threshold");

        assertNull(result);
    }

    @Test
    void shouldAddWorkoutType() throws DataAccessException {
        WorkoutType expected = new WorkoutType();
        expected.setName("Threshold");
        expected.setDescription(null);

        WorkoutType actual = repository.addWorkoutType(expected);

        assertNotNull(actual);
        assertEquals(8, actual.getWorkoutTypeId());
    }

    @Test
    void shouldUpdateWorkoutType() throws DataAccessException {
        WorkoutType expected = repository.findWorkoutTypeByWorkoutTypeId(4);
        expected.setName("Threshold");

        boolean actual = repository.updateWorkoutType(expected);

        assertTrue(actual);
        assertEquals("Threshold", expected.getName());
        assertEquals(null, expected.getDescription());
    }

    @Test
    void shouldDeleteWorkoutType() throws DataAccessException {
        boolean expected = repository.deleteWorkoutType(1);
        List<WorkoutType> actual = repository.findAllWorkoutType();

        assertTrue(expected);
        assertEquals(6, actual.size());
    }

    @Test
    void shouldNotDeleteWorkoutTypeWhenWorkoutTypeDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteWorkoutType(8);
        List<WorkoutType> actual = repository.findAllWorkoutType();

        assertFalse(expected);
        assertEquals(7, actual.size());
    }
}