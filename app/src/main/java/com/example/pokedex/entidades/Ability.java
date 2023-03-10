package com.example.pokedex.entidades;

import java.io.Serializable;

public class Ability implements Serializable {
    private String name, url;
    private int id;

    public int getId() {
        String[] partesURL = url.split("/");
        return Integer.parseInt(partesURL[partesURL.length-1]);    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "Ability{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
