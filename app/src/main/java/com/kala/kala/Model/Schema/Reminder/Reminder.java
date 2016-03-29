package com.kala.kala.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class Reminder {
    private String title;
    private String description;
    private Object due_date;
    private String user_id;
    private Integer status;
    private Object created_time;
    private Object updated_time;

    public Reminder() {
    }

    public Reminder(String title, String description, Object due_date, String user_id, Integer status, Object created_time, Object updated_time) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.user_id = user_id;
        this.status = status;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDue_date() {
        return due_date;
    }

    public void setDue_date(Object due_date) {
        this.due_date = due_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
