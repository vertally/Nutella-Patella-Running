package org.example.data;

import org.example.models.PersonalBest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalBestMapper implements RowMapper<PersonalBest> {
    @Override
    public PersonalBest mapRow(ResultSet rs, int i) throws SQLException {
        PersonalBest personalBest = new PersonalBest();
        personalBest.setPersonalBestId(rs.getInt("personal_best_id"));
        personalBest.setAppUserId(rs.getInt("app_user_id"));
        personalBest.setDistanceId(rs.getInt("distance_id"));
        personalBest.setTime(rs.getTime("time"));
        personalBest.setDate(rs.getDate("date").toLocalDate());

        return personalBest;
    }
}
