package org.example.domain;

import org.example.data.AppUserRepository;
import org.example.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserRepository repository;

    @Test
    void shouldFindByUsername() throws DataAccessException {
        AppUser expected = makeAppUser(1);
        when(repository.findAppUserByUsername("NutellaPatella")).thenReturn(expected);

        UserDetails actual = service.loadUserByUsername("NutellaPatella");

        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void shouldNotFindByUsernameWhenUsernameDoesNotExist() throws DataAccessException {
        AppUser expected = makeAppUser(1);
        when(repository.findAppUserByUsername("NutellaPatella")).thenReturn(expected);
        when(repository.findAppUserByUsername("CoachNutellaPatella")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("CoachNutellaPatella"),
                "CoachNutellaPatella could not be found.");
    }

    @Test
    void shouldAddAppUser() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("NutellaPatella");
        expected.setPassword("jellylegs2024");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWhenEmailIsEmpty() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("");
        expected.setUsername("NutellaPatella");
        expected.setPassword("jellylegs2024");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("Email is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenUsernameIsEmpty() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("");
        expected.setPassword("jellylegs2024");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("Username is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenUsernameIsDuplicate() throws DataAccessException {
        AppUser existingAppUser = makeAppUser(1);
        when(repository.findAppUserByUsername(existingAppUser.getUsername())).thenReturn(existingAppUser);

        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("NutellaPatella");
        expected.setPassword("jellylegs2024");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("This username is taken.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenUsernameIsTooLong() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("NutellaPatellaAndJellyLegsAndSloshingStomach");
        expected.setPassword("jellylegs2024");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("Username cannot be greater than 32 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordIsBlank() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("NutellaPatella");
        expected.setPassword("");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("Password is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordIsTooShort() throws DataAccessException {
        AppUser expected = new AppUser();
        expected.setEmail("nutellapatella@gmail.com");
        expected.setUsername("NutellaPatella");
        expected.setPassword("nutella");
        when(repository.addAppUser(expected)).thenReturn(expected);

        Result<AppUser> actual = service.addAppUser(expected);

        assertFalse(actual.isSuccess());
        assertEquals("Password must contain at least 8 characters.",actual.getMessages().get(0));
    }

    // HELPER METHODS
    private AppUser makeAppUser(int appUserId) {
        AppUser appUser = new AppUser(
                appUserId,
                "nutellapatella@gmail.com",
                "NutellaPatella",
                "jellylegs2024",
                true,
                List.of("runner")
        );

        return appUser;
    }

}