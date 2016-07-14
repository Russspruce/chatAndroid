package com.epicodus.chatandroid.models;

import java.util.Date;

/**
 * Created by Guest on 7/14/16.
 */
public class Chat {

    private String message;
    private String name;


    private String date;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    public Chat(String name, String message, String date) {
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
