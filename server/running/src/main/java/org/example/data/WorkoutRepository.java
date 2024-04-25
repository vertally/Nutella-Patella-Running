package org.example.data;

import org.example.models.Workout;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository {
    @Transactional
    Workout findWorkoutByWorkoutId(int workoutId) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByTrainingPlanId(int trainingPlanId) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByTrainingPlanName(String trainingPlanName) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByWorkoutTypeId(int workoutTypeId) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByAppUserId(int appUserId) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByAppUserUsername(String appUserUsername) throws DataAccessException;

    @Transactional
    List<Workout> findWorkoutByWorkoutDate(LocalDate workoutDate) throws DataAccessException;

    @Transactional
    Workout addWorkout(Workout workout) throws DataAccessException;

    @Transactional
    boolean updateWorkout(Workout workout) throws DataAccessException;

    @Transactional
    boolean deleteWorkout(int workoutId) throws DataAccessException;
}
