package org.example.data;

import org.example.models.AppUser;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {
    @Transactional
    AppUser findAppUserByUsername(String username);

    @Transactional
    AppUser addAppUser(AppUser user);

    @Transactional
    boolean deleteAppUser(int appUserId) throws DataAccessException;
}
