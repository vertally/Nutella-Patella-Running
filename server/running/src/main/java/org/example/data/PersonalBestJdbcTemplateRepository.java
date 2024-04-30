package org.example.data;

import org.example.models.PersonalBest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PersonalBestJdbcTemplateRepository implements PersonalBestRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonalBestJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public PersonalBest findPersonalBestByPersonalBestId(int personalBestId) throws DataAccessException {
        final String sql = "select personal_best_id, app_user_id, distance_id, time, date " +
                "from personal_best " +
                "where personal_best_id = ?;";

        return jdbcTemplate.query(sql, new PersonalBestMapper(), personalBestId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public List<PersonalBest> findPersonalBestByAppUserId(int appUserId) throws DataAccessException {
        final String sql = "select personal_best_id, app_user_id, distance_id, time, date " +
                "from personal_best " +
                "where app_user_id = ? " +
                "order by distance_id;";

        return jdbcTemplate.query(sql, new PersonalBestMapper(), appUserId);

    }

    @Override
    @Transactional
    public List<PersonalBest> findPersonalBestByAppUserUsername(String appUserUsername) throws DataAccessException {
        final String sql = "select personal_best_id, pb.app_user_id, distance_id, time, date " +
                "from personal_best pb " +
                "inner join app_user au on au.app_user_id = pb.app_user_id " +
                "where username = ? " +
                "order by distance_id;";

        return jdbcTemplate.query(sql, new PersonalBestMapper(), appUserUsername);
    }

    @Override
    @Transactional
    public List<PersonalBest> findPersonalBestByDistanceId(int distanceId) throws DataAccessException {
        final String sql = "select personal_best_id, app_user_id, distance_id, time, date " +
                "from personal_best " +
                "where distance_id = ? " +
                "order by time;";

        return jdbcTemplate.query(sql, new PersonalBestMapper(), distanceId);
    }

    @Override
    @Transactional
    public PersonalBest addPersonalBest(PersonalBest personalBest) throws DataAccessException {
        final String sql = "insert into personal_best (app_user_id, distance_id, time, date) " +
                "values (?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, personalBest.getAppUserId());
            ps.setInt(2, personalBest.getDistanceId());
            ps.setTime(3, personalBest.getTime());
            ps.setDate(4, Date.valueOf(personalBest.getDate()));

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        personalBest.setPersonalBestId(keyHolder.getKey().intValue());

        return personalBest;
    }

    @Override
    @Transactional
    public boolean updatePersonalBest(PersonalBest personalBest) throws DataAccessException {
        final String sql = "update personal_best set " +
                "app_user_id = ?, " +
                "distance_id = ?, " +
                "time = ?, " +
                "date = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                personalBest.getAppUserId(),
                personalBest.getDistanceId(),
                personalBest.getTime(),
                personalBest.getDate());

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean deletePersonalBest(int personalBestId) throws DataAccessException {
        final String sql = "delete from personal_best " +
                "where personal_best_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, personalBestId);

        return rowsDeleted > 0;
    }
}
