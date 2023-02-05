package com.example.pokedex.entidades;

import java.io.Serializable;

public class Type implements Serializable {
    private String name, url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
