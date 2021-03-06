package com.kala.kala.LocalModel;

import com.kala.kala.Util.list;

/**
 * Created by Edo on 2/12/2016.
 */
public class Reminder implements list {

    public int id;
    public String title;
    public String due_date;
    public String created_time;
    public String updated_time;

    // constructors
    public Reminder() {
        super();
    }

    public Reminder(String title, String due_date) {
        super();
        this.title = title;
        this.due_date = due_date;
    }

    public Reminder(int id, String title, String due_date) {
        super();
        this.id = id;
        this.title = title;
        this.due_date = due_date;
    }

    public void setDuedate(String due_date) {
        this.due_date = due_date;
    }

    // Getters
    public int getId() {
        return id;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return due_date;
    }

    public String getUpdatedTime() {
        return updated_time;
    }

    public void setUpdatedTime(String updated_at) {
        this.updated_time = updated_at;
    }

    public String getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(String created_at) {
        this.created_time = created_at;
    }

    public boolean isHeader() {
        return false;
    }
}
