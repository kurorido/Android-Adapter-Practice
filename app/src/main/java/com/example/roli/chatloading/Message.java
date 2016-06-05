package com.example.roli.chatloading;

/**
 * Created by roli on 6/5/16.
 */
public class Message {

    public enum MessageType {
        TEXT,
        LOADING
    }

    private MessageType type;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
