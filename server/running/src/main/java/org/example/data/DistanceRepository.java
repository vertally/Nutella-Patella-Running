package org.example.data;

import org.example.models.Distance;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DistanceRepository {
    @Transactional
    List<Distance> findAllDistance() throws DataAccessException;

    @Transactional
    Distance findDistanceByDistanceId(int distanceId) throws DataAccessException;

    @Transactional
    Distance findDistanceByDistanceName(String distance) throws DataAccessException;

    @Transactional
    Distance addDistance(Distance distance) throws DataAccessException;

    @Transactional
    boolean updateDistance(Distance distance) throws DataAccessException;

    @Transactional
    boolean deleteDistance(int distanceId) throws DataAccessException;
}
