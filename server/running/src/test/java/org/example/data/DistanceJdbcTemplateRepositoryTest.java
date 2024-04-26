package org.example.data;

import org.example.models.Distance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DistanceJdbcTemplateRepositoryTest {

    @Autowired
    private DistanceJdbcTemplateRepository repository;

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
    void shouldFindAllDistance() throws DataAccessException {
        List<Distance> result = repository.findAllDistance();

        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals("1K", result.get(0).getDistance());
    }

    @Test
    void shouldFindByDistanceId() throws DataAccessException {
        Distance result = repository.findDistanceByDistanceId(1);

        assertNotNull(result);
        assertEquals("1K", result.getDistance());
    }

    @Test
    void shouldNotFindByDistanceIdWhenDistanceIdDoesNotExist() throws DataAccessException {
        Distance result = repository.findDistanceByDistanceId(7);

        assertNull(result);
    }

    @Test
    void shouldFindByDistanceName() throws DataAccessException {
        Distance result = repository.findDistanceByDistanceName("1K");

        assertNotNull(result);
        assertEquals(1, result.getDistanceId());
    }

    @Test
    void shouldNotFindByDistanceNameWhenDistanceNameDoesNotExist() throws DataAccessException {
        Distance result = repository.findDistanceByDistanceName("50K");

        assertNull(result);
    }

    @Test
    void shouldAddDistance() throws DataAccessException {
        Distance expected = new Distance();
        expected.setDistance("50K");

        Distance actual = repository.addDistance(expected);

        assertNotNull(actual);
        assertEquals(7, actual.getDistanceId());
    }

    @Test
    void shouldUpdateDistance() throws DataAccessException {
        Distance expected = repository.findDistanceByDistanceId(6);
        expected.setDistance("26.2 miles");

        boolean actual = repository.updateDistance(expected);

        assertTrue(actual);
        assertEquals("26.2 miles", expected.getDistance());
    }

    @Test
    void shouldDeleteDistance() throws DataAccessException {
        boolean expected = repository.deleteDistance(6);
        List<Distance> actual = repository.findAllDistance();

        assertTrue(expected);
        assertEquals(5, actual.size());
    }

    @Test
    void shouldNotDeleteDistanceWhenDistanceIdDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteDistance(7);
        List<Distance> actual = repository.findAllDistance();

        assertFalse(expected);
        assertEquals(6, actual.size());
    }

}