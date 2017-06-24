package org.example.ws.model;

import java.math.BigInteger;

/**
 * Created by z063407 on 6/21/17.
 */
public class Greeting {
    private Long id;
    private String text;

    public Greeting(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Greeting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
