package com.kala.kala;

/**
 * Created by Edo on 2/24/2016.
 */
public class Comment implements list {

    public int id;
    public boolean left;
    public String message;
    public String created_at;

    // Constructors
    public Comment(){
        super();
    }

    public Comment(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public Comment(int id, boolean left, String message) {
        super();
        this.id = id;
        this.left = left;
        this.message = message;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }


    // Getters
    public int getId() {
        return id;
    }
    public boolean getLeft() {
        return left;
    }
    public String getMessage() {
        return message;
    }

    public boolean isHeader(){
        return false;
    }
}
