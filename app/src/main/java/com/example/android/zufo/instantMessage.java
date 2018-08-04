package com.example.android.zufo;

/**
 * Created by Lokesh Gidds on 23-07-2018.
 */

public class instantMessage {
    private String author;
    private String message;

    public instantMessage(){


    }
    public instantMessage(String message, String author) {
        this.author = author;
        this.message = message;
    }


    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
