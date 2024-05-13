package org.example.domain;

import org.example.data.TrainingPlanRepository;
import org.example.models.TrainingPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainingPlanServiceTest {

    @Autowired
    TrainingPlanService service;

    @MockBean
    TrainingPlanRepository repository;

    @Test
    void shouldFindAllTrainingPlans() throws DataAccessException {
        TrainingPlan expected = makeTrainingPlan(1);
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        allTrainingPlan.add(expected);
        when(repository.findAllTrainingPlan()).thenReturn(allTrainingPlan);

        List<TrainingPlan> result = service.findAllTrainingPlan();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2024 NYC Marathon", result.get(0).getName());
    }

    @Test
    void shouldFindByTrainingPlanId() throws DataAccessException {
        TrainingPlan expected = makeTrainingPlan(1);
        when(repository.findTrainingPlanByTrainingPlanId(1)).thenReturn(expected);

        TrainingPlan result = service.findTrainingPlanByTrainingPlanId(1);

        assertNotNull(result);
        assertEquals("2024 NYC Marathon", result.getName());
    }

    @Test
    void shouldNotFindByTrainingPlanIdWhenTrainingPlanIdDoesNotExist() throws DataAccessException {
        when(repository.findTrainingPlanByTrainingPlanId(1)).thenReturn(null);

        TrainingPlan result = service.findTrainingPlanByTrainingPlanId(1);

        assertNull(result);
    }

    @Test
    void shouldFindByAppUserId() throws DataAccessException {
        TrainingPlan expected = makeTrainingPlan(1);
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        allTrainingPlan.add(expected);
        when(repository.findTrainingPlanByAppUserId(1)).thenReturn(allTrainingPlan);

        List<TrainingPlan> result = service.findTrainingPlanByAppUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2024 NYC Marathon", result.get(0).getName());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserIdDoesNotExist() throws DataAccessException {
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        when(repository.findTrainingPlanByAppUserId(1)).thenReturn(allTrainingPlan);

        List<TrainingPlan> result = service.findTrainingPlanByAppUserId(1);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByAppUserUsername() throws DataAccessException {
        TrainingPlan expected = makeTrainingPlan(1);
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        allTrainingPlan.add(expected);
        when(repository.findTrainingPlanByAppUserUsername("NutellaPatella")).thenReturn(allTrainingPlan);

        List<TrainingPlan> result = service.findTrainingPlanByAppUserUsername("NutellaPatella");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2024 NYC Marathon", result.get(0).getName());
    }

    @Test
    void shouldNotFindByAppUserUsernameWhenAppUserUsernameDoesNotExist() throws DataAccessException {
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        when(repository.findTrainingPlanByAppUserUsername("NutellaPatella")).thenReturn(allTrainingPlan);

        List<TrainingPlan> result = service.findTrainingPlanByAppUserUsername("NutellaPatella");

        assertEquals(0, result.size());
    }

    @Test
    void shouldAddTrainingPlan() throws DataAccessException {
        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(1);
        expected.setName("2024 NYC Marathon");
        expected.setStartDate(LocalDate.of(2024, 7, 1));
        expected.setEndDate(LocalDate.of(2024, 11, 3));
        expected.setDescription("A 16-week training plan for the New York City Marathon on November 3, 2024.");
        when(repository.addTrainingPlan(expected)).thenReturn(expected);

        Result<TrainingPlan> actual = service.addTrainingPlan(expected);

        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWhenAppUserIdIsNull() throws DataAccessException {
        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(0);
        expected.setName("2024 NYC Marathon");
        expected.setStartDate(LocalDate.of(2024, 7, 1));
        expected.setEndDate(LocalDate.of(2024, 11, 3));
        expected.setDescription("A 16-week training plan for the New York City Marathon on November 3, 2024.");
        when(repository.addTrainingPlan(expected)).thenReturn(expected);

        Result<TrainingPlan> actual = service.addTrainingPlan(expected);

        assertFalse(actual.isSuccess());
        assertEquals("This training plan must be assigned to a user.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenNameIsNull() throws DataAccessException {
        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(1);
        expected.setName("");
        expected.setStartDate(LocalDate.of(2024, 7, 1));
        expected.setEndDate(LocalDate.of(2024, 11, 3));
        expected.setDescription("A 16-week training plan for the New York City Marathon on November 3, 2024.");
        when(repository.addTrainingPlan(expected)).thenReturn(expected);

        Result<TrainingPlan> actual = service.addTrainingPlan(expected);

        assertFalse(actual.isSuccess());
        assertEquals("A name for this training plan is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenNameIsTooLong() throws DataAccessException {
        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(1);
        expected.setName("2024 TCS New York City Marathon || 26.2 miles, 42.2 kilometers, 138435 feet, 42195 meters");
        expected.setStartDate(LocalDate.of(2024, 7, 1));
        expected.setEndDate(LocalDate.of(2024, 11, 3));
        expected.setDescription("A 16-week training plan for the New York City Marathon on November 3, 2024.");
        when(repository.addTrainingPlan(expected)).thenReturn(expected);

        Result<TrainingPlan> actual = service.addTrainingPlan(expected);

        assertFalse(actual.isSuccess());
        assertEquals("A training plan name cannot be greater than 50 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenNameIsDuplicate() throws DataAccessException {
        TrainingPlan existingTrainingPlan = makeTrainingPlan(1);
        ArrayList<TrainingPlan> allTrainingPlan = new ArrayList<>();
        allTrainingPlan.add(existingTrainingPlan);
        when(repository.findAllTrainingPlan()).thenReturn(allTrainingPlan);

        TrainingPlan expected = new TrainingPlan();
        expected.setAppUserId(1);
        expected.setName("2024 NYC Marathon");
        expected.setStartDate(LocalDate.of(2024, 7, 1));
        expected.setEndDate(LocalDate.of(2024, 11, 3));
        expected.setDescription("A 16-week training plan for the New York City Marathon on November 3, 2024.");
        when(repository.addTrainingPlan(expected)).thenReturn(expected);

        Result<TrainingPlan> actual = service.addTrainingPlan(expected);

        assertFalse(actual.isSuccess());
        assertEquals("You've already created a training plan with this name.", actual.getMessages().get(0));
    }

    // HELPER METHODS
    private TrainingPlan makeTrainingPlan(int trainingPlanId) {
        TrainingPlan trainingPlan = new TrainingPlan(
                trainingPlanId,
                1,
                "2024 NYC Marathon",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 11, 3),
                "A 16-week training plan for the New York City Marathon on November 3, 2024."
        );

        return trainingPlan;
    }
}