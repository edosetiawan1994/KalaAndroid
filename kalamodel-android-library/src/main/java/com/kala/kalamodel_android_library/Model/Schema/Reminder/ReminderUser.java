package com.kala.kalamodel_android_library.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class ReminderUser {
    private Integer status;
    private Object created_time;
    private Object updated_time;

    public ReminderUser() {
    }

    public ReminderUser(Integer status, Object created_time, Object updated_time) {
        this.status = status;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
