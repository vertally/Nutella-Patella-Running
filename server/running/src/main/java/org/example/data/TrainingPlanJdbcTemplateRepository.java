package org.example.data;

import org.example.models.TrainingPlan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TrainingPlanJdbcTemplateRepository implements TrainingPlanRepository {

    private final JdbcTemplate jdbcTemplate;

    public TrainingPlanJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public TrainingPlan findTrainingPlanByTrainingPlanId(int trainingPlanId) throws DataAccessException {
        final String sql = "select training_plan_id, app_user_id, `name`, start_date, end_date, `description` " +
                "from training_plan " +
                "where training_plan_id = ?;";

        return jdbcTemplate.query(sql, new TrainingPlanMapper(), trainingPlanId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public List<TrainingPlan> findTrainingPlanByAppUserId(int appUserId) throws DataAccessException {
        final String sql = "select training_plan_id, app_user_id, `name`, start_date, end_date, `description` " +
                "from training_plan " +
                "where app_user_id = ?;";

        return jdbcTemplate.query(sql, new TrainingPlanMapper(), appUserId);
    }

    @Override
    @Transactional
    public TrainingPlan addTrainingPlan(TrainingPlan trainingPlan) throws DataAccessException {
        final String sql = "insert into training_plan (app_user_id, `name`, start_date, end_date, `description` " +
                "values (?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, trainingPlan.getAppUserId());
            ps.setString(2, trainingPlan.getName());
            ps.setDate(3, Date.valueOf(trainingPlan.getStartDate()));
            ps.setDate(4, Date.valueOf(trainingPlan.getEndDate()));
            ps.setString(5, trainingPlan.getDescription());

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        trainingPlan.setTrainingPlanId(keyHolder.getKey().intValue());

        return trainingPlan;
    }

    @Override
    @Transactional
    public boolean updateTrainingPlan(TrainingPlan trainingPlan) throws DataAccessException {
        final String sql = "update training_plan set " +
                "`name` = ?, " +
                "start_date = ?, " +
                "end_date = ?, " +
                "`description` = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                trainingPlan.getName(),
                trainingPlan.getStartDate(),
                trainingPlan.getEndDate(),
                trainingPlan.getDescription());

        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean deleteTrainingPlan(int trainingPlanId) {
        final String sql = "delete from training_plan " +
                "where training_plan_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, trainingPlanId);

        return rowsDeleted > 0;
    }
}
