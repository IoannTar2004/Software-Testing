package org.example.utils;

public enum Tags {
    SPAN("span"), DIV("div");

    private String tag;

    Tags(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
