package com.softrunapps.mvvmarchitecture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usernsme;
    private String email;

    public User(String usernsme, String email) {
        this.usernsme = usernsme;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsernsme() {
        return usernsme;
    }

    public String getEmail() {
        return email;
    }
}

