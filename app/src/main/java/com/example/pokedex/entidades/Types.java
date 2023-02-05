package com.example.pokedex.entidades;

import java.io.Serializable;

public class Types implements Serializable {
    private int slot;
    private Type type;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Types{" +
                "slot=" + slot +
                ", type=" + type +
                '}';
    }
}
