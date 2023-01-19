package com.example.pokedex.conexionPokeAPI;

import com.example.pokedex.entidades.ListaPokemonAPI;
import com.example.pokedex.entidades.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioPokeAPI {

    @GET("pokemon")
    Call<ListaPokemonAPI> hazteConTodos(@Query ("limit") int limit,@Query("offset") int offset);

    @GET("pokemon/{pokemon}")
    Call<Pokemon> pokemonPorID(@Path("pokemon") int idPokemon);

}