package com.example.demo;

public class Greeting {

    private final long id;
    private final String content;
    public final String something = "test";

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

//    public String getSomething() {
//        return this.something;
//    }
}