package org.example.data;

import org.example.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    private AppUserJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindByUsername() throws DataAccessException {
        AppUser result = repository.findAppUserByUsername("NutellaPatella");

        assertNotNull(result);
        assertEquals(1, result.getAppUserId());
        assertEquals("nutellapatella@gmail.com", result.getEmail());
    }

    @Test
    void shouldNotFindByUsernameWhenUsernameDoesNotExist() throws DataAccessException {
        AppUser result = repository.findAppUserByUsername("NutellaElbow");

        assertNull(result);
    }

    @Test
    void shouldAddAppUser() throws DataAccessException {
        AppUser user = new AppUser(
                3,
                "nutellaelbow@gmail.com",
                "NutellaElbow",
                "jellylegs2024",
                true,
                List.of("runner")
        );

        AppUser expected = repository.addAppUser(user);

        assertNotNull(expected);
        assertEquals(3, expected.getAppUserId());
        assertEquals("NutellaElbow", expected.getUsername());
    }

    @Test
    void shouldDeleteAppUser() throws DataAccessException{
        boolean expected = repository.deleteAppUser(2);

        assertTrue(expected);
    }

    @Test
    void shouldNotDeleteWhenAppUserDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteAppUser(3);

        assertFalse(expected);
    }
}