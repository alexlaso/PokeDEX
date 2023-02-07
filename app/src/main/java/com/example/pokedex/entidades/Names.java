package com.example.pokedex.entidades;

import java.io.Serializable;
import java.util.List;

public class Names implements Serializable {
    private String name;
    private Language language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Names{" +
                "name='" + name + '\'' +
                ", language=" + language +
                '}';
    }
}
