package org.example.data;

import org.example.models.PersonalBest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalBestJdbcTemplateRepositoryTest {

    @Autowired
    private PersonalBestJdbcTemplateRepository repository;

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
    void shouldFindByPersonalBestId() throws DataAccessException {
        PersonalBest result = repository.findPersonalBestByPersonalBestId(1);

        assertNotNull(result);
        assertEquals(Time.valueOf("3:24:55"), result.getTime());
    }

    @Test
    void shouldNotFindByPersonalBestIdWhenPersonalBestIdDoesNotExist() throws DataAccessException {
        PersonalBest result = repository.findPersonalBestByPersonalBestId(2);

        assertNull(result);
    }

    @Test
    void shouldFindByAppUserId() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByAppUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Time.valueOf("3:24:55"), result.get(0).getTime());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserIdDoesNotExist() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByAppUserId(2);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByAppUserUsername() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByAppUserUsername("NutellaPatella");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Time.valueOf("3:24:55"), result.get(0).getTime());
    }

    @Test
    void shouldNotFindByAppUserUsernameWhenAppUserUsernameDoesNotExist() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByAppUserUsername("CoachNutellaPatella");

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByDistanceId() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByDistanceId(6);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Time.valueOf("3:24:55"), result.get(0).getTime());
    }

    @Test
    void shouldNotFindByDistanceIdWhenDistanceIdDoesNotExist() throws DataAccessException {
        List<PersonalBest> result = repository.findPersonalBestByDistanceId(1);

        assertEquals(0, result.size());
    }

    @Test
    void shouldAddPersonalBest() throws DataAccessException {
        PersonalBest expected = new PersonalBest();
        expected.setAppUserId(2);
        expected.setDistanceId(5);
        expected.setTime(Time.valueOf("1:40:00"));
        expected.setDate(LocalDate.of(2024, 3, 17));

        PersonalBest actual = repository.addPersonalBest(expected);
        List<PersonalBest> allPersonalBestsForUser = repository.findPersonalBestByAppUserId(2);

        assertNotNull(actual);
        assertEquals(1, allPersonalBestsForUser.size());
        assertEquals(Time.valueOf("1:40:00"), allPersonalBestsForUser.get(0).getTime());
    }

    @Test
    void shouldUpdatePersonalBest() throws DataAccessException {
        PersonalBest expected = repository.findPersonalBestByPersonalBestId(1);
        expected.setTime(Time.valueOf("3:30:00"));

        boolean actual = repository.updatePersonalBest(expected);
        List<PersonalBest> allPersonalBestsForUser = repository.findPersonalBestByAppUserId(1);

        assertTrue(actual);
        assertEquals(Time.valueOf("3:30:00"), allPersonalBestsForUser.get(0).getTime());
    }

    @Test
    void shouldDeletePersonalBest() throws DataAccessException {
        boolean expected = repository.deletePersonalBest(1);
        List<PersonalBest> actual = repository.findPersonalBestByAppUserId(1);

        assertTrue(expected);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldNotDeletePersonalBestWhenPersonalBestIdDoesNotExist() throws DataAccessException {
        boolean expected = repository.deletePersonalBest(2);

        assertFalse(expected);
    }

}