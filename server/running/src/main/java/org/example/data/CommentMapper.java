package org.example.data;

import org.example.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setWorkoutId(rs.getInt("workout_id"));
        comment.setAppUserId(rs.getInt("app_user_id"));
        comment.setParentCommentId(rs.getInt("parent_comment_id"));
        comment.setComment(rs.getString("comment"));
        comment.setDate(rs.getDate("date").toLocalDate());

        return comment;
    }
}
