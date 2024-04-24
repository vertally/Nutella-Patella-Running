package org.example.data;

import org.example.models.AppUser;
import org.example.models.AppUserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findAppUserByUsername(String username) throws DataAccessException {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select app_user_id, email, username, password_hash, enabled " +
                "from app_user " +
                "where username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public AppUser addAppUser(AppUser user) throws DataAccessException {
        final String sql = "insert into app_user (email, username, password_hash) " +
                "values (?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    @Override
    @Transactional
    public boolean deleteAppUser(int appUserId) throws DataAccessException {
        deleteChildren(appUserId);

        final String sql = "delete from app_user " +
                "where app_user_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, appUserId);

        return rowsDeleted > 0;
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select ar.name " +
                "from app_user_role aur " +
                "inner join app_role ar on aur.app_role_id = ar.app_role_id " +
                "inner join app_user au on aur.app_user_id = au.app_user_id " +
                "where au.username = ?;";

        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }

    private void updateRoles(AppUser user) {
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) " +
                    "select ?, app_role_id from app_role " +
                    "where `name` = ?;";

            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }

    private void deleteChildren(int appUserId) {
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", appUserId);

        jdbcTemplate.update("delete from training_plan where app_user_id = ?;", appUserId);

        jdbcTemplate.update("delete from workout where app_user_id = ?;", appUserId);

        jdbcTemplate.update("delete from `comment` where app_user_id = ?;", appUserId);

        jdbcTemplate.update("delete from personal_best where app_user_id = ?;", appUserId);
    }
}
