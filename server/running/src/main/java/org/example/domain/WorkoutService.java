package org.example.domain;

import org.example.data.WorkoutRepository;
import org.example.data.WorkoutTypeRepository;
import org.example.models.Workout;
import org.example.models.WorkoutType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository repository;

    private final WorkoutTypeRepository workoutTypeRepository;

    public WorkoutService(WorkoutRepository repository, WorkoutTypeRepository workoutTypeRepository) {
        this.repository = repository;
        this.workoutTypeRepository = workoutTypeRepository;
    }

    @Transactional
    public Workout findWorkoutByWorkoutId(int workoutId) throws DataAccessException {
        return repository.findWorkoutByWorkoutId(workoutId);
    }

    @Transactional
    public List<Workout> findWorkoutByTrainingPlanId(int trainingPlanId) throws DataAccessException {
        return repository.findWorkoutByTrainingPlanId(trainingPlanId);
    }

    @Transactional
    public List<Workout> findWorkoutByTrainingPlanName(String trainingPlanName) throws DataAccessException {
        return repository.findWorkoutByTrainingPlanName(trainingPlanName);
    }

    @Transactional
    public List<Workout> findWorkoutByWorkoutTypeId(int workoutTypeId) throws DataAccessException {
        return repository.findWorkoutByWorkoutTypeId(workoutTypeId);
    }

    @Transactional
    public List<Workout> findWorkoutByAppUserId(int appUserId) throws DataAccessException {
        return repository.findWorkoutByAppUserId(appUserId);
    }

    @Transactional
    public List<Workout> findWorkoutByAppUserUsername(String appUserUsername) throws DataAccessException {
        return repository.findWorkoutByAppUserUsername(appUserUsername);
    }

    @Transactional
    public List<Workout> findWorkoutByWorkoutDate(LocalDate date) throws DataAccessException {
        return repository.findWorkoutByWorkoutDate(date);
    }

    @Transactional
    public Result<Workout> addWorkout(Workout workout) throws DataAccessException {
        Result<Workout> result = new Result<>();

        validate(workout, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateWorkoutIdForAdd(workout, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            result.setPayload(repository.addWorkout(workout));
        }

        return result;
    }

    private Result<Workout> validate(Workout workout, Result<Workout> result) {
        // APP USER VALIDATION
        validateAppUserIdNull(workout.getAppUserId(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // WORKOUT TYPE VALIDATION
        validateWorkoutTypeIdNull(workout.getWorkoutTypeId(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // DATE VALIDATION
        validateDate(workout.getDate(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // UNIT VALIDATION
        validateUnit(workout.getDistance(), workout.getUnit(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // TRAINING PLAN VALIDATION
        validateTrainingPlanIdNull(workout.getTrainingPlanId(), result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private Result<Workout> validateAppUserIdNull(int appUserId, Result<Workout> result) {
        if (appUserId <= 0) {
            result.addMessage(ActionStatus.INVALID, "This workout must be assigned to a user.");
            return result;
        }

        return result;
    }

    private Result<Workout> validateWorkoutTypeIdNull(int workoutTypeId, Result<Workout> result) {
        List<WorkoutType> allWorkoutType = workoutTypeRepository.findAllWorkoutType();

        for (WorkoutType wt : allWorkoutType) {
            if (workoutTypeId != wt.getWorkoutTypeId()) {
                result.addMessage(ActionStatus.INVALID, "This workout must be assigned to a workout type.");
                return result;
            }
        }

        return result;
    }

    private Result<Workout> validateDate(LocalDate date, Result<Workout> result) {
        if (date == null) {
            result.addMessage(ActionStatus.INVALID, "A date for this workout is required.");
            return result;
        }

        return result;
    }

    private Result<Workout> validateUnit(double distance, String unit, Result<Workout> result) {
        if ((distance != 0)
                && (unit == null || unit.isBlank())) {
            result.addMessage(ActionStatus.INVALID, "A distance unit is required for this workout.");
            return result;
        }

        return result;
    }

    private Result<Workout> validateTrainingPlanIdNull(int trainingPlanId, Result<Workout> result) {
        if (trainingPlanId <= 0) {
            result.addMessage(ActionStatus.INVALID, "This workout must be assigned to a training plan.");
            return result;
        }

        return result;
    }

    private Result<Workout> validateWorkoutIdForAdd(Workout workout, Result<Workout> result) {
        if (workout.getWorkoutId() != 0) {
            result.addMessage(ActionStatus.INVALID, "You cannot set this workout's ID.");
            return result;
        }

        return result;
    }
}
