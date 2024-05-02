package org.example.domain;

import org.example.data.TrainingPlanRepository;
import org.example.models.TrainingPlan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingPlanService {

    private final TrainingPlanRepository repository;

    public TrainingPlanService(TrainingPlanRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TrainingPlan findTrainingPlanByTrainingPlanId(int trainingPlanId) throws DataAccessException {
        return repository.findTrainingPlanByTrainingPlanId(trainingPlanId);
    }

    @Transactional
    public List<TrainingPlan> findTrainingPlanByAppUserId(int appUserId) throws DataAccessException {
        return repository.findTrainingPlanByAppUserId(appUserId);
    }

    @Transactional
    public Result<TrainingPlan> add(TrainingPlan trainingPlan) throws DataAccessException {
        Result<TrainingPlan> result = new Result<>();

        validate(trainingPlan, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateTrainingPlanIdForAdd(trainingPlan, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            result.setPayload(repository.addTrainingPlan(trainingPlan));
        }

        return result;
    }

    @Transactional
    public Result<TrainingPlan> update(TrainingPlan trainingPlan) throws DataAccessException {
        Result<TrainingPlan> result = new Result<>();

        validate(trainingPlan, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateTrainingPlanIdForUpdate(trainingPlan, result);
        if (!result.isSuccess()) {
            return result;
        } else {
            repository.updateTrainingPlan(trainingPlan);
            result.setPayload(trainingPlan);
        }

        return result;
    }

    @Transactional
    public Result<TrainingPlan> deleteTrainingPlan(int trainingPlanId) throws DataAccessException {
        Result<TrainingPlan> result = new Result<>();

        if (!repository.deleteTrainingPlan(trainingPlanId)) {
            result.addMessage(ActionStatus.NOT_FOUND, "This training plan cannot be found.");
            return result;
        }

        return result;
    }

    private Result<TrainingPlan> validate(TrainingPlan trainingPlan, Result<TrainingPlan> result) {
        // APP USER VALIDATION
        validateAppUserIdNull(trainingPlan.getAppUserId(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // NAME VALIDATION
        validateNameNull(trainingPlan.getName(), result);
        if (!result.isSuccess()) {
            return result;
        }

        validateNameFieldLength(trainingPlan.getName(), result);
        if (!result.isSuccess()) {
            return result;
        }

        validateNameDuplicate(trainingPlan.getName(), trainingPlan.getAppUserId(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // START DATE VALIDATION
        validateStartDateNull(trainingPlan.getStartDate(), result);
        if (!result.isSuccess()) {
            return result;
        }

        validateStartDateIsBeforeEndDate(trainingPlan.getStartDate(), trainingPlan.getEndDate(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // END DATE VALIDATION
        validateEndDateNull(trainingPlan.getEndDate(), result);
        if (!result.isSuccess()) {
            return result;
        }

        // DESCRIPTION VALIDATION
        validateDescriptionFieldLength(trainingPlan.getDescription(), result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    // APP USER VALIDATION
    private Result<TrainingPlan> validateAppUserIdNull(int appUserId, Result<TrainingPlan> result) {
        if (appUserId == 0) {
            result.addMessage(ActionStatus.INVALID, "This training plan must be assigned to a user.");
            return result;
        }

        return result;
    }

    // NAME VALIDATION
    private Result<TrainingPlan> validateNameNull(String name, Result<TrainingPlan> result) {
        if (name == null || name.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "A name for this training plan is required.");
            return result;
        }

        return result;
    }

    private Result<TrainingPlan> validateNameFieldLength(String name, Result<TrainingPlan> result) {
        if (name.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "A training plan name cannot be greater than 50 characters.");
            return result;
        }

        return result;
    }

    private Result<TrainingPlan> validateNameDuplicate(String name, int appUserId, Result<TrainingPlan> result) {
        List<TrainingPlan> allTrainingPlans = repository.findAllTrainingPlan();

        for (TrainingPlan tp : allTrainingPlans) {
            if ((name.equals(tp.getName()))
            && (appUserId == tp.getAppUserId())) {
                result.addMessage(ActionStatus.DUPLICATE, "You've already created a training plan with this name.");
                return  result;
            }
        }

        return result;
    }

    // START DATE VALIDATION
    private Result<TrainingPlan> validateStartDateNull(LocalDate startDate, Result<TrainingPlan> result) {
        if (startDate == null) {
            result.addMessage(ActionStatus.INVALID, "A start date is required.");
            return result;
        }

        return result;
    }

    private Result<TrainingPlan> validateStartDateIsBeforeEndDate(LocalDate startDate, LocalDate endDate, Result<TrainingPlan> result) {
        if (endDate.isBefore(startDate)) {
            result.addMessage(ActionStatus.INVALID, "A training plan's start date cannot be after its end date.");
            return result;
        }

        return result;
    }

    // END DATE VALIDATION
    private Result<TrainingPlan> validateEndDateNull(LocalDate endDate, Result<TrainingPlan> result) {
        if (endDate == null) {
            result.addMessage(ActionStatus.INVALID, "An end date is required.");
            return result;
        }

        return result;
    }

    // DESCRIPTION VALIDATION
    private Result<TrainingPlan> validateDescriptionFieldLength(String description, Result<TrainingPlan> result) {
        if (description.length() > 240) {
            result.addMessage(ActionStatus.INVALID, "A training plan description cannot be greater than 240 characters.");
            return result;
        }

        return result;
    }

    // TRAINING PLAN ID VALIDATION
    private Result<TrainingPlan> validateTrainingPlanIdForAdd(TrainingPlan trainingPlan, Result<TrainingPlan> result) {
        if (trainingPlan.getTrainingPlanId() != 0) {
            result.addMessage(ActionStatus.INVALID, "You cannot set this training plan's ID.");
            return result;
        }

        return result;
    }

    private Result<TrainingPlan> validateTrainingPlanIdForUpdate(TrainingPlan trainingPlan, Result<TrainingPlan> result) {
        if (trainingPlan.getTrainingPlanId() <= 0) {
            result.addMessage(ActionStatus.INVALID, "A training plan ID is required.");
            return result;
        }

        return result;
    }
}
