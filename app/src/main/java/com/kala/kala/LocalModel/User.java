package com.kala.kala.LocalModel;

/**
 * Created by Edo on 2/24/2016.
 */
public class User {

    int id;
    public String fullname;
    String username;
    String email;
    String phone_number;
    String picture;
    String thumbnail;
    int status;
    String updated_time;
    String created_time;

    // constructors
    public User() {
        super();
    }

    public User(String fullname, String username, String email, String phone_number, String picture, String thumbnail, int status) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.picture = picture;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    public User(int id, String fullname, String username, String email, String phone_number, String picture, String thumbnail, int status) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.picture = picture;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    public void setUpdatedTime(String updated_time) {
        this.updated_time = updated_time;
    }

    public void setCreatedTime(String created_time) {
        this.created_time = created_time;
    }

    // getters
    public long getId() {
        return this.id;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return this.phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdated_time() {
        return this.updated_time;
    }

    public String getCreated_time() {
        return this.created_time;
    }

}
