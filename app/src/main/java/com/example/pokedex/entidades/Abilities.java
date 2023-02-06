package com.example.pokedex.entidades;

import java.io.Serializable;

public class Abilities implements Serializable {
    private boolean is_hidden;
    private int slot;
    private Ability ability;

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    @Override
    public String toString() {
        return "Abilities{" +
                "is_hidden=" + is_hidden +
                ", slot=" + slot +
                ", ability=" + ability +
                '}';
    }
}
