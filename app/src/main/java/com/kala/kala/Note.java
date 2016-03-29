package com.kala.kala;

/**
 * Created by Edo on 3/11/2016.
 */
public class Note {

    public long id;
    public String content;
    public String created_time;
    public String updated_time;

    // constructors
    public Note(){
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

    // Setters
    public void setId(long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreatedTime(String created_at) {
        this.created_time = created_at;
    }
    public void setUpdatedTime(String updated_at) {
        this.updated_time = updated_at;
    }


    // Getters
    public long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getUpdatedTime() {
        return updated_time;
    }
    public String getCreatedTime() {
        return created_time;
    }
}
