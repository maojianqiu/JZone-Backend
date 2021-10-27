package com.blog.portal.controller;

public class Type {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        switch (type) {
            case "0":
                return "ZERO";
            case "1":
                return "ONE";
            default:
                return "OTHER";
        }
    }
}