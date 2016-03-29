package com.kala.kalamodel_android_library.Model.Schema.Reminder;

/**
 * Created by Kala on 2/29/16.
 */
public class ReminderContent {
    //private String content;
    private Integer type;
    private Object created_time;
    private Object updated_time;

    public ReminderContent() {
    }

    public ReminderContent(Integer type, Object created_time, Object updated_time) {
        this.type = type;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
