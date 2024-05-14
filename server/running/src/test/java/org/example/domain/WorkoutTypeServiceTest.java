package org.example.domain;

import org.example.data.WorkoutTypeRepository;
import org.example.models.WorkoutType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WorkoutTypeServiceTest {

    @Autowired
    WorkoutTypeService service;

    @MockBean
    WorkoutTypeRepository repository;

    @Test
    void shouldFindAllWorkoutTypes() throws DataAccessException {
        WorkoutType expected = makeWorkoutType(1);
        ArrayList<WorkoutType> allWorkoutType = new ArrayList<>();
        allWorkoutType.add(expected);
        when(repository.findAllWorkoutType()).thenReturn(allWorkoutType);

        List<WorkoutType> result = service.findAllWorkoutType();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Threshold", result.get(0).getName());
    }

    @Test
    void shouldFindByWorkoutTypeId() throws DataAccessException {
        WorkoutType expected= makeWorkoutType(1);
        when(repository.findWorkoutTypeByWorkoutTypeId(1)).thenReturn(expected);

        WorkoutType result = service.findWorkoutTypeByWorkoutTypeId(1);

        assertNotNull(result);
        assertEquals("Threshold", result.getName());
    }

    @Test
    void shouldNotFindByWorkoutTypeIdWhenWorkoutTypeIdDoesNotExist() throws DataAccessException {
        when(repository.findWorkoutTypeByWorkoutTypeId(1)).thenReturn(null);

        WorkoutType result = service.findWorkoutTypeByWorkoutTypeId(1);

        assertNull(result);
    }

    @Test
    void shouldFindByWorkoutTypeName() throws DataAccessException {
        WorkoutType expected = makeWorkoutType(1);
        when(repository.findWorkoutTypeByWorkoutTypeName("Threshold")).thenReturn(expected);

        WorkoutType result = service.findWorkoutTypeByWorkoutTypeName("Threshold");

        assertNotNull(result);
        assertEquals(1, result.getWorkoutTypeId());
    }

    @Test
    void shouldNotFindByWorkoutTypeNameWhenWorkoutTypeNameDoesNotExist() throws DataAccessException {
        when(repository.findWorkoutTypeByWorkoutTypeName("Threshold")).thenReturn(null);

        WorkoutType result = service.findWorkoutTypeByWorkoutTypeName("Threshold");

        assertNull(result);
    }

    @Test
    void shouldAddWorkoutType() throws DataAccessException {
        WorkoutType expected = new WorkoutType();
        expected.setName("Threshold");
        when(repository.addWorkoutType(expected)).thenReturn(expected);

        Result<WorkoutType> actual = service.addWorkoutType(expected);

        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
        assertEquals("Threshold", actual.getPayload().getName());
    }

    @Test
    void shouldNotAddWhenNameIsNull() throws DataAccessException {
        WorkoutType expected = new WorkoutType();
        when(repository.addWorkoutType(expected)).thenReturn(expected);

        Result<WorkoutType> actual = service.addWorkoutType(expected);

        assertFalse(actual.isSuccess());
        assertEquals("A name for this workout type is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenWorkoutTypeIdIsSet() throws DataAccessException {
        WorkoutType expected = new WorkoutType();
        expected.setName("Threshold");
        expected.setWorkoutTypeId(1);
        when(repository.addWorkoutType(expected)).thenReturn(expected);

        Result<WorkoutType> actual = service.addWorkoutType(expected);

        assertFalse(actual.isSuccess());
        assertEquals("You cannot set this workout type's ID.", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdateWorkoutType() throws DataAccessException {
        WorkoutType expected = makeWorkoutType(1);
        expected.setDescription("A 15-20 minute jog at 90% of your FTHR.");
        when(repository.updateWorkoutType(expected)).thenReturn(true);

        Result<WorkoutType> actual = service.updateWorkoutType(expected);

        assertTrue(actual.isSuccess());
        assertEquals("A 15-20 minute jog at 90% of your FTHR.", actual.getPayload().getDescription());
    }

    @Test
    void shouldNotUpdateWhenWorkoutTypeIdIsNotSet() throws DataAccessException {
        WorkoutType expected = makeWorkoutType(1);
        expected.setWorkoutTypeId(0);
        when(repository.updateWorkoutType(expected)).thenReturn(true);

        Result<WorkoutType> actual = service.updateWorkoutType(expected);

        assertFalse(actual.isSuccess());
        assertEquals("A workout type ID is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldDeleteWorkoutType() throws DataAccessException {
        WorkoutType expected = makeWorkoutType(1);
        ArrayList<WorkoutType> allWorkoutType = new ArrayList<>();
        allWorkoutType.add(expected);
        when(repository.deleteWorkoutType(1)).thenReturn(true);

        Result<WorkoutType> actual = service.deleteWorkoutType(1);

        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteWhenWorkoutTypeIdDoesNotExist() throws DataAccessException {
        Result<WorkoutType> actual = service.deleteWorkoutType(1);

        assertFalse(actual.isSuccess());
        assertEquals("This workout type cannot be found.", actual.getMessages().get(0));
    }

    // HELPER METHODS
    private WorkoutType makeWorkoutType(int workoutTypeId) {
        WorkoutType workoutType = new WorkoutType(
                workoutTypeId,
                "Threshold",
                null
        );

        return workoutType;
    }

}