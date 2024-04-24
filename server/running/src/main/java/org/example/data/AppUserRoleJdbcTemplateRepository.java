package org.example.data;

import org.example.models.AppUserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class AppUserRoleJdbcTemplateRepository implements AppUserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserRoleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AppUserRole addAppUserRole(AppUserRole appUserRole) throws DataAccessException {
        final String sql = "insert into app_user_role (app_user_id, app_role_id) " +
                "values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, appUserRole.getAppUserId());
            ps.setInt(2, appUserRole.getAppRoleId());
            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        appUserRole.setAppUserRoleId(keyHolder.getKey().intValue());

        return appUserRole;
    }

    @Override
    public boolean deleteAppUserRole(int appUserRoleId) throws DataAccessException {
        final String sql = "delete from app_user_role " +
                "where app_user_role_id = ?;";

        return jdbcTemplate.update(sql, appUserRoleId) > 0;
    }
}
