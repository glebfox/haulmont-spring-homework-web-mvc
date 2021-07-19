package org.example.domain;

public enum OrderStatus {

    NEW("new"),
    FINISHED("finished"),
    CANCELED("canceled");

    private String id;

    OrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
