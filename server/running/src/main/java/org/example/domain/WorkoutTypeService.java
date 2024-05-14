package org.example.domain;

import org.example.data.WorkoutTypeRepository;
import org.example.models.WorkoutType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutTypeService {

    private final WorkoutTypeRepository repository;

    public WorkoutTypeService(WorkoutTypeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<WorkoutType> findAllWorkoutType() throws DataAccessException {
        return repository.findAllWorkoutType();
    }

    @Transactional
    public WorkoutType findWorkoutTypeByWorkoutTypeId(int workoutTypeId) throws DataAccessException {
        return repository.findWorkoutTypeByWorkoutTypeId(workoutTypeId);
    }

    @Transactional
    public WorkoutType findWorkoutTypeByWorkoutTypeName(String workoutTypeName) throws DataAccessException {
        return repository.findWorkoutTypeByWorkoutTypeName(workoutTypeName);
    }

    @Transactional
    public Result<WorkoutType> addWorkoutType(WorkoutType workoutType) throws DataAccessException {
        Result<WorkoutType> result = new Result<>();

        validate(workoutType, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateWorkoutTypeIdForAdd(workoutType, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            result.setPayload(repository.addWorkoutType(workoutType));
        }

        return result;
    }

    @Transactional
    public Result<WorkoutType> updateWorkoutType(WorkoutType workoutType) throws DataAccessException {
        Result<WorkoutType> result = new Result<>();

        validate(workoutType, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateWorkoutTypeIdForUpdate(workoutType, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            repository.updateWorkoutType(workoutType);
            result.setPayload(workoutType);
        }

        return result;
    }

    @Transactional
    public Result<WorkoutType> deleteWorkoutType(int workoutTypeId) throws DataAccessException {
        Result<WorkoutType> result = new Result<>();

        if (!repository.deleteWorkoutType(workoutTypeId)) {
            result.addMessage(ActionStatus.NOT_FOUND, "This workout type cannot be found.");
            return result;
        }

        return result;
    }

    private Result<WorkoutType> validate(WorkoutType workoutType, Result<WorkoutType> result) {
        // NAME VALIDATION
        validateNameNull(workoutType.getName(), result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private Result<WorkoutType> validateNameNull(String name, Result<WorkoutType> result) {
        if (name == null || name.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "A name for this workout type is required.");
            return result;
        }
        return result;
    }

    private Result<WorkoutType> validateWorkoutTypeIdForAdd(WorkoutType workoutType, Result<WorkoutType> result) {
        if (workoutType.getWorkoutTypeId() != 0) {
            result.addMessage(ActionStatus.INVALID, "You cannot set this workout type's ID.");
            return result;
        }

        return result;
    }

    private Result<WorkoutType> validateWorkoutTypeIdForUpdate(WorkoutType workoutType, Result<WorkoutType> result) {
        if (workoutType.getWorkoutTypeId() <= 0) {
            result.addMessage(ActionStatus.INVALID, "A workout type ID is required.");
            return result;
        }

        return result;
    }

}
