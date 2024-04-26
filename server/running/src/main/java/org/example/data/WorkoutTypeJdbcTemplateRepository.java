package org.example.data;

import org.example.models.WorkoutType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class WorkoutTypeJdbcTemplateRepository implements WorkoutTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkoutTypeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<WorkoutType> findAllWorkoutType() throws DataAccessException {
        final String sql = "select workout_type_id, name, description " +
                "from workout_type " +
                "order by name;";

        return jdbcTemplate.query(sql, new WorkoutTypeMapper());
    }

    @Override
    @Transactional
    public WorkoutType findWorkoutTypeByWorkoutTypeId(int workoutTypeId) throws DataAccessException {
        final String sql = "select workout_type_id, name, description " +
                "from workout_type " +
                "where workout_type_id = ?;";

        return jdbcTemplate.query(sql, new WorkoutTypeMapper(), workoutTypeId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public WorkoutType findWorkoutTypeByWorkoutTypeName(String workoutTypeName) throws DataAccessException {
        final String sql = "select workout_type_id, name, description " +
                "from workout_type " +
                "where name = ?;";

        return jdbcTemplate.query(sql, new WorkoutTypeMapper(), workoutTypeName)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public WorkoutType addWorkoutType(WorkoutType workoutType) throws DataAccessException {
        final String sql = "insert into workout_type (name, description) " +
                "values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, workoutType.getName());
            ps.setString(2, workoutType.getDescription());

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        workoutType.setWorkoutTypeId(keyHolder.getKey().intValue());

        return workoutType;
    }

    @Override
    @Transactional
    public boolean updateWorkoutType(WorkoutType workoutType) throws DataAccessException {
        final String sql = "update workout_type set " +
                "name = ?, " +
                "description = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                workoutType.getName(),
                workoutType.getDescription());

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean deleteWorkoutType(int workoutTypeId) throws DataAccessException {
        final String sql = "delete from workout_type " +
                "where workout_type_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, workoutTypeId);

        return rowsDeleted > 0;
    }
}
