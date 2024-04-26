package org.example.data;

import org.example.models.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository {
    @Transactional
    Comment findCommentByCommentId(int commentId) throws DataAccessException;

    @Transactional
    List<Comment> findByWorkoutId(int workoutId) throws DataAccessException;

    @Transactional
    List<Comment> findByAppUserId(int appUserId) throws DataAccessException;

    @Transactional
    List<Comment> findByAppUserUsername(String appUserUsername) throws DataAccessException;

    @Transactional
    Comment addComment(Comment comment) throws DataAccessException;

    @Transactional
    boolean updateComment(Comment comment) throws DataAccessException;

    @Transactional
    boolean deleteComment(int commentId) throws DataAccessException;
}
