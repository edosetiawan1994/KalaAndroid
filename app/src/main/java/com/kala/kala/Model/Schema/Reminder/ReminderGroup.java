package com.kala.kala.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class ReminderGroup {
    private Object created_time;
    private Object updated_time;

    public ReminderGroup() {
    }

    public ReminderGroup(Object created_time, Object updated_time) {
        this.created_time = created_time;
        this.updated_time = updated_time;
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
