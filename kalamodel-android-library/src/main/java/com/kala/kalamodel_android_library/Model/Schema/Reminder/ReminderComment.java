package com.kala.kalamodel_android_library.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class ReminderComment {
    private String user_id;
    private String message;
    private Object created_time;
    private Object updated_time;

    public ReminderComment() {
    }

    public ReminderComment(String user_id, String message, Object created_time, Object updated_time) {
        this.user_id = user_id;
        this.message = message;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Object created_time) {
        this.created_time = created_time;
    }

    public Object getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Object updated_time) {
        this.updated_time = updated_time;
    }
}
