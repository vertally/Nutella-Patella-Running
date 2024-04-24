package org.example.data;

import org.example.models.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserRoleJdbcTemplateRepositoryTest {

    @Autowired
    private AppUserRoleJdbcTemplateRepository repository;

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
    void shouldAdd() throws DataAccessException {
        AppUserRole expected = new AppUserRole();
        expected.setAppUserId(1);
        expected.setAppRoleId(2);

        AppUserRole actual = repository.addAppUserRole(expected);

        assertNotNull(actual);
        assertEquals(3, actual.getAppUserRoleId());
    }

    @Test
    void shouldDelete() throws DataAccessException {
        boolean expected = repository.deleteAppUserRole(2);

        assertTrue(expected);
    }

    @Test
    void shouldNotDeleteWhenAppUserRoleIdDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteAppUserRole(3);

        assertFalse(expected);
    }

}