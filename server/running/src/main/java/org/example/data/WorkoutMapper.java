package org.example.data;

import org.example.models.Workout;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutMapper implements RowMapper<Workout> {

    @Override
    public Workout mapRow(ResultSet rs, int i) throws SQLException {
        Workout workout = new Workout();
        workout.setWorkoutId(rs.getInt("workout_id"));
        workout.setAppUserId(rs.getInt("app_user_id"));
        workout.setWorkoutTypeId(rs.getInt("workout_type_id"));
        workout.setDate(rs.getDate("date").toLocalDate());
        workout.setDistance(rs.getDouble("distance"));
        workout.setUnit(rs.getString("unit"));
        workout.setDescription(rs.getString("description"));
        workout.setEffort(rs.getString("effort"));
        workout.setTrainingPlanId(rs.getInt("training_plan_id"));

        return workout;
    }
}
