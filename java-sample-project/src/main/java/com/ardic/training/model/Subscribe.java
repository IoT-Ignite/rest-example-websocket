package com.ardic.training.model;


public class Subscribe {
    private SubscribeMessage message;
    private String type;

    public SubscribeMessage getMessage() {
        return message;
    }

    public void setMessage(SubscribeMessage message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "message=" + message +
                ", type='" + type + '\'' +
                '}';
    }
}
