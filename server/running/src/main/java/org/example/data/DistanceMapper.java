package org.example.data;

import org.example.models.Distance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanceMapper implements RowMapper<Distance> {

    @Override
    public Distance mapRow(ResultSet rs, int i) throws SQLException {
       Distance distance = new Distance();
       distance.setDistanceId(rs.getInt("distance_id"));
       distance.setDistance(rs.getString("distance"));

        return distance;
    }
}
