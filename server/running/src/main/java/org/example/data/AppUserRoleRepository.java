package org.example.data;

import org.example.models.AppUserRole;
import org.springframework.dao.DataAccessException;

public interface AppUserRoleRepository {
    AppUserRole addAppUserRole(AppUserRole appUserRole) throws DataAccessException;

    boolean deleteAppUserRole(int appUserRoleId) throws DataAccessException;
}
