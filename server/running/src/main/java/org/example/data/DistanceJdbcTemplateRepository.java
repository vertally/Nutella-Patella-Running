package org.example.data;

import org.example.models.Distance;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DistanceJdbcTemplateRepository implements DistanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public DistanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<Distance> findAllDistance() throws DataAccessException {
        final String sql = "select distance_id, distance " +
                "from distance " +
                "order by distance_id;";

        return jdbcTemplate.query(sql, new DistanceMapper());
    }

    @Override
    @Transactional
    public Distance findDistanceByDistanceId(int distanceId) throws DataAccessException {
        final String sql = "select distance_id, distance " +
                "from distance " +
                "where distance_id = ?;";

        return jdbcTemplate.query(sql, new DistanceMapper(), distanceId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public Distance findDistanceByDistanceName(String distance) throws DataAccessException {
        final String sql = "select distance_id, distance " +
                "from distance " +
                "where distance = ?;";

        return jdbcTemplate.query(sql, new DistanceMapper(), distance)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public Distance addDistance(Distance distance) throws DataAccessException {
        final String sql = "insert into distance (distance) " +
                "values (?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, distance.getDistance());

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        distance.setDistanceId(keyHolder.getKey().intValue());

        return distance;
    }

    @Override
    @Transactional
    public boolean updateDistance(Distance distance) throws DataAccessException {
        final String sql = "update distance set " +
                "distance = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                distance.getDistance());

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean deleteDistance(int distanceId) throws DataAccessException {
        deleteChildren(distanceId);

        final String sql = "delete from distance " +
                "where distance_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, distanceId);

        return rowsDeleted > 0;
    }

    private void deleteChildren(int distanceId) {
        jdbcTemplate.update("delete from personal_best where distance_id = ?;", distanceId);
    }
}
