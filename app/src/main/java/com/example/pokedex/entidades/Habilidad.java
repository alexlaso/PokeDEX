package com.example.pokedex.entidades;

import java.io.Serializable;
import java.util.List;

public class Habilidad implements Serializable {
    private int id;
    private String name;
    private String url;
    private List<Names> names;

    @Override
    public String toString() {
        return "Habilidad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", names=" + names +
                '}';
    }

    public int getId() {
        String[] partesURL = url.split("/");
        return Integer.parseInt(partesURL[partesURL.length-1]);
    }

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

    public List<Names> getNames() {
        return names;
    }

    public void setNames(List<Names> names) {
        this.names = names;
    }
}
