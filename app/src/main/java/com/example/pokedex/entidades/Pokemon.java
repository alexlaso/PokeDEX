package com.example.pokedex.entidades;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private String url;
    private List<Types> types;
    private List<Abilities> abilities;

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

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", types=" + types +
                ", abilities=" + abilities +
                '}';
    }
}
