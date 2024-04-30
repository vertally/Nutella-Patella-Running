package org.example.data;

import org.example.models.PersonalBest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonalBestRepository {
    @Transactional
    PersonalBest findPersonalBestByPersonalBestId(int personalBestId) throws DataAccessException;

    @Transactional
    List<PersonalBest> findPersonalBestByAppUserId(int appUserId) throws DataAccessException;

    @Transactional
    List<PersonalBest> findPersonalBestByAppUserUsername(String appUserUsername) throws DataAccessException;

    @Transactional
    List<PersonalBest> findPersonalBestByDistanceId(int distanceId) throws DataAccessException;

    @Transactional
    PersonalBest addPersonalBest(PersonalBest personalBest) throws DataAccessException;

    @Transactional
    boolean updatePersonalBest(PersonalBest personalBest) throws DataAccessException;

    @Transactional
    boolean deletePersonalBest(int personalBestId) throws DataAccessException;
}
