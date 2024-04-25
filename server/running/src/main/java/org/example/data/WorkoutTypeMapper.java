package org.example.data;

import org.example.models.WorkoutType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutTypeMapper implements RowMapper<WorkoutType> {
    @Override
    public WorkoutType mapRow(ResultSet rs, int i) throws SQLException {
        WorkoutType workoutType = new WorkoutType();
        workoutType.setTrainingPlanId(rs.getInt("workout_type_id"));
        workoutType.setName(rs.getString("name"));
        workoutType.setDescription(rs.getString("description"));

        return workoutType;
    }
}
