package org.example.data;

import org.example.models.TrainingPlan;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrainingPlanRepository {

    @Transactional
    List<TrainingPlan> findAllTrainingPlan() throws DataAccessException;

    @Transactional
    TrainingPlan findTrainingPlanByTrainingPlanId(int trainingPlanId) throws DataAccessException;

    @Transactional
    List<TrainingPlan> findTrainingPlanByAppUserId(int appUserId) throws DataAccessException;

    @Transactional
    List<TrainingPlan> findTrainingPlanByAppUserUsername(String appUserUsername) throws DataAccessException;

    @Transactional
    TrainingPlan addTrainingPlan(TrainingPlan trainingPlan) throws DataAccessException;

    @Transactional
    boolean updateTrainingPlan(TrainingPlan trainingPlan) throws DataAccessException;

    @Transactional
    boolean deleteTrainingPlan(int trainingPlanId);
}
