package org.example.data;

import org.example.models.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Comment findCommentByCommentId(int commentId) throws DataAccessException {
        final String sql = "select comment_id, workout_id, app_user_id, parent_comment_id, comment, date " +
                "from comment " +
                "where comment_id = ?";

        return jdbcTemplate.query(sql, new CommentMapper(), commentId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public List<Comment> findByWorkoutId(int workoutId) throws DataAccessException {
        final String sql = "select comment_id, workout_id, app_user_id, parent_comment_id, comment, date " +
                "from comment " +
                "where workout_id = ? " +
                "order by parent_comment_id, comment_id;";

        return jdbcTemplate.query(sql, new CommentMapper(), workoutId);
    }

    @Transactional
    public List<Comment> findByAppUserUsername(int appUserId) throws DataAccessException {
        final String sql = "select comment_id, workout_id, app_user_id, parent_comment_id, comment, date " +
                "from comment " +
                "where app_user_id = ? " +
                "order by parent_comment_id, comment_id;";

        return jdbcTemplate.query(sql, new CommentMapper(), appUserId);
    }

    @Transactional
    public List<Comment> findByAppUserUsername(String appUserUsername) throws DataAccessException {
        final String sql = "select comment_id, workout_id, c.app_user_id, parent_comment_id, comment, date " +
                "from comment c " +
                "inner join app_user au on au.app_user_id = c.app_user_id " +
                "where au.username = ? " +
                "order by parent_comment_id, comment_id;";

        return jdbcTemplate.query(sql, new CommentMapper(), appUserUsername);
    }

    @Transactional
    public Comment addComment(Comment comment) throws DataAccessException {
        final String sql = "insert into comment (workout_id, app_user_id, parent_comment_id, comment, date) " +
                "values (?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getWorkoutId());
            ps.setInt(2, comment.getAppUserId());
            ps.setInt(3, comment.getParentCommentId());
            ps.setString(4, comment.getComment());
            ps.setDate(5, Date.valueOf(comment.getDate()));

            return ps;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        comment.setCommentId(keyHolder.getKey().intValue());

        return comment;
    }

    @Transactional
    public boolean updateComment(Comment comment) throws DataAccessException {
        final String sql = "update comment set " +
                "workout_id = ?, " +
                "app_user_id = ?, " +
                "parent_comment_id = ?, " +
                "comment = ?, " +
                "date = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                comment.getWorkoutId(),
                comment.getAppUserId(),
                comment.getParentCommentId(),
                comment.getComment(),
                comment.getDate());

        return rowsUpdated > 0;
    }

    @Transactional
    public boolean deleteComment(int commentId) throws DataAccessException {
        final String sql = "delete from comment " +
                "where comment_id = ?;";

        int rowsDeleted = jdbcTemplate.update(sql, commentId);

        return rowsDeleted > 0;
    }
}
