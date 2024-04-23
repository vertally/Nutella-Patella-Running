package org.example.models;

import java.time.LocalDate;

public class Comment {

    private int commentId;
    private int appUserId;
    private int workoutId;
    private int parentCommentId;
    private String comment;
    private LocalDate date;

    public Comment() {
    }

    public Comment(int commentId, int appUserId, int workoutId, int parentCommentId, String comment, LocalDate date) {
        this.commentId = commentId;
        this.appUserId = appUserId;
        this.workoutId = workoutId;
        this.parentCommentId = parentCommentId;
        this.comment = comment;
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
