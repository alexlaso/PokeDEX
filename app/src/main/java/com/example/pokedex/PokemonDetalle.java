package com.example.pokedex;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.conexionPokeAPI.ServicioPokeAPI;
import com.example.pokedex.entidades.Abilities;
import com.example.pokedex.entidades.Habilidad;
import com.example.pokedex.entidades.Pokemon;
import com.example.pokedex.entidades.Types;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetalle extends AppCompatActivity {
    private Pokemon pokemon;
    private Retrofit conexionRetrofit;
    Context context;
    private ImageView spritePokemon, tipo1Imagen, tipo2Imagen;
    private TextView nombrePokemon, hab1, hab2, habOc, hab1Nombre, hab2Nombre, habOcNombre;
    private Switch switchShinyDetalle;
    private ArrayList<Integer> listaIdHabilidades;
    private ArrayList<Habilidad> listaHabilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        conexionRetrofit = new Retrofit.Builder()
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        context = this;
        setContentView(R.layout.pokemon_detalle);
        Intent intent = getIntent();

        listaIdHabilidades = new ArrayList<>();
        listaHabilidades = new ArrayList<>();

        pokemon = (Pokemon) intent.getSerializableExtra("pokemon");

        switchShinyDetalle = findViewById(R.id.switchShinyDetalle);

        spritePokemon = findViewById(R.id.imageSpriteDetalle);
        tipo1Imagen = findViewById(R.id.imageTipo1);
        tipo2Imagen = findViewById(R.id.imageTipo2);

        nombrePokemon = findViewById(R.id.textNombreDetalle);
        hab1 = findViewById(R.id.textHab1);
        hab2 = findViewById(R.id.textHab2);
        habOc = findViewById(R.id.textHabOc);
        hab1Nombre = findViewById(R.id.textHab1Nombre);
        hab2Nombre = findViewById(R.id.textHab2Nombre);
        habOcNombre = findViewById(R.id.textHabOcNombre);
        for (int i=0; i<pokemon.getAbilities().size();i++){
            listaIdHabilidades.add(pokemon.getAbilities().get(i).getAbility().getId());
        }

        switchShinyDetalle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchShinyDetalle.isChecked()){
                    setSpritePokemonShiny(pokemon.getId());
                }else{
                    setSpritePokemon(pokemon.getId());
                }
            }
        });

        //listaDatosHabilidades(listaIdHabilidades);

        ServicioPokeAPI servicioPokeAPI = conexionRetrofit.create(ServicioPokeAPI.class);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=0;i<listaIdHabilidades.size();i++){
            Call<Habilidad> habilidadCall = servicioPokeAPI.habilidadPorId(listaIdHabilidades.get(i));
            habilidadCall.enqueue(new Callback<Habilidad>() {
                @Override
                public void onResponse(Call<Habilidad> call, Response<Habilidad> response) {
                    if (response.isSuccessful()){
                        Habilidad habilidad = response.body();
                        listaHabilidades.add(habilidad);
                        System.out.println("Lista: "+listaHabilidades.toString());
                    }else{
                        Toast.makeText(context,"Conexion fallida: " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.e("aplicación","Respuestita: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Habilidad> call, Throwable t) {
                    System.out.println("ERROR GET HABILIDADES");
                    Log.e("aplicación","Error: "+t.getMessage().toString());
                }
            });
        }
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("PostLista: "+listaHabilidades.toString());

        setHabilidadesPokemon(listaHabilidades);

        System.out.println("PRUEBA: "+listaHabilidades.toString());
        setNombrePokemon(pokemon.getName());
        setSpritePokemon(pokemon.getId());
        setTipo1Imagen(pokemon.getTypes().get(0).getType().getName());

        if(pokemon.getTypes().size()>=2){
            setTipo2Imagen(pokemon.getTypes().get(1).getType().getName());
        }else{
            tipo2Imagen.setVisibility(View.GONE);
        }

        System.out.println("LISTA: "+listaHabilidades.toString());
        System.out.println("FINAL: "+listaHabilidades.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listaHabilidades.clear();
    }

    public void setNombrePokemon(String nombre){nombrePokemon.setText(nombre.toString().toUpperCase());}

    public void setSpritePokemon(int idPokemon){
        Picasso.with(spritePokemon.getContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idPokemon + ".png")
                .into(spritePokemon);
    }

    public void setSpritePokemonShiny(int idPokemon){
        Picasso.with(spritePokemon.getContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + idPokemon + ".png")
                .into(spritePokemon);
    }

    public int returnIDTypes(String type){
        int id=0;
        switch(type.toUpperCase()){
            case "NORMAL":
                id = R.drawable.normal;
                break;
            case "FIRE":
                id = R.drawable.fuego;
                break;
            case "WATER":
                id = R.drawable.agua;
                break;
            case "GRASS":
                id = R.drawable.planta;
                break;
            case "ROCK":
                id = R.drawable.roca;
                break;
            case "GROUND":
                id = R.drawable.tierra;
                break;
            case "STEEL":
                id = R.drawable.acero;
                break;
            case "FLYING":
                id = R.drawable.volador;
                break;
            case "ELECTRIC":
                id = R.drawable.electrico;
                break;
            case "FIGHTING":
                id = R.drawable.lucha;
                break;
            case "BUG":
                id = R.drawable.bicho;
                break;
            case "DARK":
                id = R.drawable.siniestro;
                break;
            case "GHOST":
                id = R.drawable.fantasma;
                break;
            case "DRAGON":
                id = R.drawable.dragon;
                break;
            case "FAIRY":
                id = R.drawable.hada;
                break;
            case "ICE":
                id = R.drawable.hielo;
                break;
            case "POISON":
                id = R.drawable.veneno;
                break;
            case "PSYCHIC":
                id = R.drawable.psiquico;
                break;
        }
        return id;
    }

    public void setTipo1Imagen(String tipo1){tipo1Imagen.setImageDrawable(getResources().getDrawable(returnIDTypes(tipo1)));}

    public void setTipo2Imagen(String tipo2){tipo2Imagen.setImageDrawable(getResources().getDrawable(returnIDTypes(tipo2)));}

    /*public void listaDatosHabilidades(ArrayList<Integer> listaIdHabilidades){
        ServicioPokeAPI servicioPokeAPI = conexionRetrofit.create(ServicioPokeAPI.class);
        for(int i=0;i<listaIdHabilidades.size();i++){
            Call<Habilidad> habilidadCall = servicioPokeAPI.habilidadPorId(listaIdHabilidades.get(i));
            habilidadCall.enqueue(new Callback<Habilidad>() {
                @Override
                public void onResponse(Call<Habilidad> call, Response<Habilidad> response) {
                    if (response.isSuccessful()){
                        Habilidad habilidad = response.body();
                        listaHabilidades.add(habilidad);
                        setHabilidadesPokemon();
                    }else{
                        Toast.makeText(context,"Conexion fallida: " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.e("aplicación","Respuestita: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Habilidad> call, Throwable t) {
                    System.out.println("ERROR GET HABILIDADES");
                    Log.e("aplicación","Error: "+t.getMessage().toString());
                }
            });
        }*/


    public void setHabilidadesPokemon(ArrayList<Habilidad> listaHabilidades){
        hab1.setText(listaHabilidades.get(0).getNames().get(5).getName().toUpperCase());
        hab1Nombre.setText("HABILIDAD 1");
        if(pokemon.getAbilities().size()<=2){
            hab2.setVisibility(View.GONE);
            hab2Nombre.setVisibility(View.GONE);
            //habOc.setText(listaHabilidades.get(1).getNames().get(5).getName().toUpperCase());
            habOcNombre.setText("HABILIDAD OC");
        }else if(pokemon.getAbilities().size()>2) {
            //hab2.setText(listaHabilidades.get(1).getNames().get(5).getName().toUpperCase());
            hab2Nombre.setText("HABILIDAD 2");
            //habOc.setText(listaHabilidades.get(2).getNames().get(5).getName().toUpperCase());
            habOcNombre.setText("HABILIDAD OC");
        }
    }
}

