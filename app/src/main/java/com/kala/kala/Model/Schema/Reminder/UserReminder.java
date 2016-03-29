package com.kala.kala.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class UserReminder {
    private Object completion_date;
    private Integer type;
    private Integer status;
    private String shared_user_id;
    private String shared_group_id;
    private Object created_time;
    private Object updated_time;

    public UserReminder() {
    }

    public UserReminder(Object completion_date, Integer type, Integer status, String shared_user_id, String shared_group_id, Object created_time, Object updated_time) {
        this.completion_date = completion_date;
        this.type = type;
        this.status = status;
        this.shared_user_id = shared_user_id;
        this.shared_group_id = shared_group_id;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public Object getCompletion_date() {

        return completion_date;
    }

    public void setCompletion_date(Object completion_date) {
        this.completion_date = completion_date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShared_user_id() {
        return shared_user_id;
    }

    public void setShared_user_id(String shared_user_id) {
        this.shared_user_id = shared_user_id;
    }

    public String getShared_group_id() {
        return shared_group_id;
    }

    public void setShared_group_id(String shared_group_id) {
        this.shared_group_id = shared_group_id;
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
