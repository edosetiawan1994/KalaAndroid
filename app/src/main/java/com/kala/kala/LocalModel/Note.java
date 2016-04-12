package com.kala.kala.LocalModel;

/**
 * Created by Edo on 3/11/2016.
 */
public class Note {

    public long id;
    public String content;
    public String created_time;
    public String updated_time;

    // constructors
    public Note() {
        super();
    }

    public Note(String content) {
        super();
        this.content = content;
    }

    public Note(long id, String content) {
        super();
        this.id = id;
        this.content = content;
    }

    // Getters
    public long getId() {
        return id;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
