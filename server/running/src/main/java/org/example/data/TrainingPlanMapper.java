package org.example.data;

import org.example.models.TrainingPlan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingPlanMapper implements RowMapper<TrainingPlan> {

    @Override
    public TrainingPlan mapRow(ResultSet rs, int i) throws SQLException {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainingPlanId(rs.getInt("training_plan_id"));
        trainingPlan.setAppUserId(rs.getInt("app_user_id"));
        trainingPlan.setName(rs.getString("`name`"));
        trainingPlan.setStartDate(rs.getDate("start_date").toLocalDate());
        trainingPlan.setEndDate(rs.getDate("end_date").toLocalDate());
        trainingPlan.setDescription(rs.getString("`description`"));

        return trainingPlan;
    }
}
