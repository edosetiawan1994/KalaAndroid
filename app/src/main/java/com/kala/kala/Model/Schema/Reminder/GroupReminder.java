package com.kala.kala.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class GroupReminder {
    private Object completion_date;
    private Integer status;
    private Object created_time;
    private Object updated_time;

    public GroupReminder() {

    }

    public GroupReminder(Object completion_date, Integer status, Object created_time, Object updated_time) {
        this.completion_date = completion_date;
        this.status = status;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public Object getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(Object completion_date) {
        this.completion_date = completion_date;
    }

    public Integer getStatus() {
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
