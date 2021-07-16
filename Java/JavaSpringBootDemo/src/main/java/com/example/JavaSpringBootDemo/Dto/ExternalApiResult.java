package com.example.JavaSpringBootDemo.Dto;

public class ExternalApiResult {
    private String type;
    private Value value;

    //region Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    //endregion
}
