package com.epicodus.chatandroid.models;

/**
 * Created by Guest on 7/14/16.
 */
public class Chat {

    private String message;
    private String name;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    public Chat(String name, String message) {
        this.message = message;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
