package org.example.data;

import org.example.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentJdbcTemplateRepositoryTest {

    @Autowired
    private CommentJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state()");
        }
    }

    @Test
    void shouldFindByCommentId() throws DataAccessException {
        Comment result = repository.findCommentByCommentId(1);

        assertNotNull(result);
        assertEquals(2, result.getAppUserId());
    }

    @Test
    void shouldNotFindByCommentIdWhenCommentIdDoesNotExist() throws DataAccessException {
        Comment result = repository.findCommentByCommentId(3);

        assertNull(result);
    }

    @Test
    void shouldFindByWorkoutId() throws DataAccessException {
        List<Comment> result = repository.findByWorkoutId(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAppUserId());
    }

    @Test
    void shouldNotFindByWorkoutIdWhenWorkoutIdDoesNotExist() throws DataAccessException {
        List<Comment> result = repository.findByWorkoutId(2);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByAppUserId() throws DataAccessException {
        List<Comment> result = repository.findByAppUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParentCommentId());
    }

    @Test
    void shouldNotFindByAppUserIdWhenAppUserIdDoesNotExist() throws DataAccessException {
        List<Comment> result = repository.findByAppUserId(3);

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByAppUserUsername() throws DataAccessException {
        List<Comment> result = repository.findByAppUserUsername("NutellaPatella");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParentCommentId());
    }

    @Test
    void shouldNotFindByAppUsernameWhenUsernameDoesNotExist() throws DataAccessException {
        List<Comment> result = repository.findByAppUserUsername("NutellaElbow");

        assertEquals(0, result.size());
    }

    @Test
    void shouldAddComment() throws DataAccessException {
        Comment expected = new Comment();
        expected.setWorkoutId(2);
        expected.setAppUserId(1);
        expected.setParentCommentId(1);
        expected.setComment("Good to know. If you're still fatigued tomorrow, switch tomorrow's run to 3 miles at RPE 5.");
        expected.setDate(LocalDate.of(2024, 7, 1));

        Comment actual = repository.addComment(expected);

        assertNotNull(actual);
        assertEquals(3, actual.getCommentId());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Comment expected = repository.findCommentByCommentId(2);
        expected.setDate(LocalDate.of(2024, 7, 2));

        boolean actual = repository.updateComment(expected);

        assertTrue(actual);
        assertEquals(LocalDate.of(2024, 7, 2), expected.getDate());
    }

    @Test
    void shouldDeleteComment() throws DataAccessException {
        boolean expected = repository.deleteComment(2);
        List<Comment> actual = repository.findByWorkoutId(1);

        assertTrue(expected);
        assertEquals(1, actual.size());
    }

    @Test
    void shouldNotDeleteCommentWhenCommentDoesNotExist() throws DataAccessException {
        boolean expected = repository.deleteComment(3);

        assertFalse(expected);
    }

}