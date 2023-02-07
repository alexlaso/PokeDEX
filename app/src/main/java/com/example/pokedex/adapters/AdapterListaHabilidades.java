package com.example.pokedex.adapters;

import android.content.Context;

import com.example.pokedex.entidades.Habilidad;

import java.util.ArrayList;

public class AdapterListaHabilidades {
    private Context context;
    private ArrayList<Habilidad> listaHabilidades;

    public AdapterListaHabilidades(Context context){
        this.context = context;
        listaHabilidades = new ArrayList();
    }

    public void agregarListaHabilidades(ArrayList<Habilidad> lista){
        listaHabilidades.addAll(lista);
    }

    public ArrayList<Habilidad> devolverHabilidades(){return listaHabilidades;}
}
