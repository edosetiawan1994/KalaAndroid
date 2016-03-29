package com.kala.kala;

/**
 * Created by Edo on 2/24/2016.
 */
public class Photo {

    public int id;
    public int image;
    public String created_at;

    // Constructors
    public Photo(){
        super();
    }

    public Photo(int image) {
        super();
        this.image = image;
    }

    public Photo(int id, int image) {
        super();
        this.id = id;
        this.image = image;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }


    // Getters
    public int getId() {
        return id;
    }
    public int getImage() {
        return image;
    }
}
