package org.example.data;

import org.example.models.WorkoutType;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkoutTypeRepository {
    @Transactional
    List<WorkoutType> findAllWorkoutType() throws DataAccessException;

    @Transactional
    WorkoutType findWorkoutTypeByWorkoutTypeId(int workoutTypeId) throws DataAccessException;

    @Transactional
    WorkoutType findWorkoutTypeByWorkoutTypeName(String workoutTypeName) throws DataAccessException;

    @Transactional
    WorkoutType addWorkoutType(WorkoutType workoutType) throws DataAccessException;

    @Transactional
    boolean updateWorkoutType(WorkoutType workoutType) throws DataAccessException;

    @Transactional
    boolean deleteWorkoutType(int workoutTypeId) throws DataAccessException;
}
