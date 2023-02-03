package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pokedex.adapters.AdaptadorListaPokemon;
import com.example.pokedex.conexionPokeAPI.ServicioPokeAPI;
import com.example.pokedex.entidades.ListaPokemonAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //TODO saber como recargar la lista en caliente al activar el switch para ver la lista shiny

    private Retrofit conexionRetrofit;
    private Switch switchShiny;
    Context contexto;

    private int offset;

    private RecyclerView listaPokemon;
    private AdaptadorListaPokemon adaptadorListaPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contexto = this;

        listaPokemon = findViewById(R.id.listaPokemon);
        adaptadorListaPokemon = new AdaptadorListaPokemon(contexto);
        listaPokemon.setAdapter(adaptadorListaPokemon);
        switchShiny = findViewById(R.id.switchShiny);

        final GridLayoutManager layoutManager = new GridLayoutManager(contexto, 2);
        listaPokemon.setLayoutManager(layoutManager);

        listaPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                        if (dy > 0){
                            int visibles = listaPokemon.getLayoutManager().getChildCount();
                            int total = listaPokemon.getLayoutManager().getItemCount();
                            int retrasados = layoutManager.findFirstVisibleItemPosition();

                            /* comentado para evitar dupliidad de lista
                            if (visibles + retrasados >=total){
                                //al hacer scroll se actualiza el metodo, sumando al offset, realzando una llamada al metodo
                                //retornando una lista pequeña con los sigueintes pokemon
                                //offset+=60;
                                obtenerDatos(offset);
                            }*/
                        }
            }
        });

        conexionRetrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos(0);

        switchShiny.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchShiny.isChecked()){
                    adaptadorListaPokemon.hacerShiny();
                }else{
                    adaptadorListaPokemon.hacerNormal();
                }
            }
        });
    }

    private void obtenerDatos(int offset){
        ServicioPokeAPI servicio = conexionRetrofit.create(ServicioPokeAPI.class);

        //si pongo un limite mas bajo cada vez quqe hago scroll el offset se actualiza
        //haciendo que cargue la lista poco a poco, pero me gusta mas poder hacer scroll
        //infinito :D
        Call<ListaPokemonAPI> pokemonAPICall = servicio.hazteConTodos(10000,offset);
        pokemonAPICall.enqueue(new Callback<ListaPokemonAPI>() {
            @Override
            public void onResponse(Call<ListaPokemonAPI> call, Response<ListaPokemonAPI> response) {
                if(response.isSuccessful()){
                    ListaPokemonAPI listaPokemonAPI = response.body();

                    adaptadorListaPokemon.agregarListaPokemon(listaPokemonAPI.getResults());
                    Toast.makeText(contexto, "Conexion exitosa", Toast.LENGTH_LONG).show();

                    Log.e("aplicacion","Respuestita: "+response.body());
                }else{
                    Toast.makeText(contexto, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    Log.e("aplicacion","Respuestita: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ListaPokemonAPI> call, Throwable t) {
                Log.e("aplicacion", "Fallito: "+t.getMessage());
            }
        });
    }

}