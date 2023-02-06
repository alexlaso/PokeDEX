package com.example.pokedex;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.entidades.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetalle extends AppCompatActivity {
    private Pokemon pokemon;
    private Retrofit conexionRetrofit;
    Context context;
    private ImageView spritePokemon, tipo1Imagen, tipo2Imagen;
    private TextView nombrePokemon, hab1, hab2, habOc, hab1Nombre, hab2Nombre, habOcNombre;
    private Switch switchShinyDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.pokemon_detalle);
        Intent intent = getIntent();

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

        setNombrePokemon(pokemon.getName());
        setSpritePokemon(pokemon.getId());
        setTipo1Imagen(pokemon.getTypes().get(0).getType().getName());

        if(pokemon.getTypes().size()>=2){
            setTipo2Imagen(pokemon.getTypes().get(1).getType().getName());
        }else{
            tipo2Imagen.setVisibility(View.GONE);
        }

        hab1.setText(pokemon.getAbilities().get(0).getAbility().getName().toUpperCase());
        hab1Nombre.setText("HABILIDAD 1");
        if(pokemon.getAbilities().size()<=2){
            hab2.setVisibility(View.GONE);
            hab2Nombre.setVisibility(View.GONE);
            habOc.setText(pokemon.getAbilities().get(1).getAbility().getName().toUpperCase());
            habOcNombre.setText("HABILIDAD OC");
        }else if(pokemon.getAbilities().size()>2){
            hab2.setText(pokemon.getAbilities().get(1).getAbility().getName().toUpperCase());
            hab2Nombre.setText("HABILIDAD 2");
            habOc.setText(pokemon.getAbilities().get(2).getAbility().getName().toUpperCase());
            habOcNombre.setText("HABILIDAD OC");
        }


        conexionRetrofit = new Retrofit.Builder()
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
}

