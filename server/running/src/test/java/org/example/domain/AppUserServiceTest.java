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