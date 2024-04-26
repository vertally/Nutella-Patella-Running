package org.example.data;

import org.example.models.Workout;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class WorkoutJdbcTemplateRepository implements WorkoutRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkoutJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Workout findWorkoutByWorkoutId(int workoutId) throws DataAccessException {
        final String sql = "select workout_id, app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout " +
                "where workout_id = ?;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), workoutId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByTrainingPlanId(int trainingPlanId) throws DataAccessException {
        final String sql = "select workout_id, app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout " +
                "where training_plan_id = ? " +
                "order by date;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), trainingPlanId);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByTrainingPlanName(String trainingPlanName) throws DataAccessException {
        final String sql = "select workout_id, w.app_user_id, workout_type_id, date, distance, unit, w.description, effort, w.training_plan_id " +
                "from workout w " +
                "inner join training_plan tp on tp.training_plan_id = w.training_plan_id " +
                "where tp.name = ? " +
                "order by date;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), trainingPlanName);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByWorkoutTypeId(int workoutTypeId) throws DataAccessException {
        final String sql = "select workout_id, app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout " +
                "where workout_type_id = ? " +
                "order by date;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), workoutTypeId);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByAppUserId(int appUserId) throws DataAccessException {
        final String sql = "select workout_id, app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout " +
                "where app_user_id = ? " +
                "order by date;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), appUserId);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByAppUserUsername(String appUserUsername) throws DataAccessException {
        final String sql = "select workout_id, w.app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout w " +
                "inner join app_user au on au.app_user_id = w.app_user_id " +
                "where au.username = ? " +
                "order by date;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), appUserUsername);
    }

    @Override
    @Transactional
    public List<Workout> findWorkoutByWorkoutDate(LocalDate workoutDate) throws DataAccessException {
        final String sql = "select workout_id, app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id " +
                "from workout " +
                "where date = ? " +
                "order by workout_id;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), workoutDate);
    }

    @Override
    @Transactional
    public Workout addWorkout(Workout workout) throws DataAccessException {
        final String sql = "insert into workout (app_user_id, workout_type_id, date, distance, unit, description, effort, training_plan_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, workout.getAppUserId());
            ps.setInt(2, workout.getWorkoutTypeId());
            ps.setDate(3, Date.valueOf(workout.getDate()));
            ps.setDouble(4, workout.getDistance());
            ps.setString(5, workout.getUnit());
            ps.setString(6, workout.getDescription());
            ps.setString(7, workout.getEffort());
            ps.setInt(8, workout.getTrainingPlanId());

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        workout.setWorkoutId(keyHolder.getKey().intValue());

        return workout;
    }

    @Override
    @Transactional
    public boolean updateWorkout(Workout workout) throws DataAccessException {
        final String sql = "update workout set " +
                "app_user_id = ?, " +
                "workout_type_id = ?, " +
                "date = ?, " +
                "distance = ?, " +
                "unit = ?, " +
                "description = ?, " +
                "effort = ?, " +
                "training_plan_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                workout.getAppUserId(),
                workout.getWorkoutTypeId(),
                workout.getDate(),
                workout.getDistance(),
                workout.getUnit(),
                workout.getDescription(),
                workout.getEffort(),
                workout.getTrainingPlanId());

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean deleteWorkout(int workoutId) throws DataAccessException {
        deleteChildren(workoutId);

        final String sql = "delete from workout " +
                "where workout_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, workoutId);

        return rowsDeleted > 0;
    }

    private void deleteChildren(int workoutId) {
        final String sql = "delete comment from comment " +
                "join workout on workout.workout_id = comment.workout_id " +
                "where workout.workout_id = ?;";

        jdbcTemplate.update(sql, workoutId);
    }
}
