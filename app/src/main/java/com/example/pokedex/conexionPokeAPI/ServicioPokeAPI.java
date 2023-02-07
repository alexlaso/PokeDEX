package com.example.pokedex.conexionPokeAPI;

import com.example.pokedex.entidades.Habilidad;
import com.example.pokedex.entidades.ListaHabilidadesAPI;
import com.example.pokedex.entidades.ListaPokemonAPI;
import com.example.pokedex.entidades.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioPokeAPI {

    @GET("pokemon")
    Call<ListaPokemonAPI> hazteConTodos(@Query ("limit") int limit,@Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> pokemonPorID(@Path("id") int idPokemon);

    @GET("ability")
    Call<ListaHabilidadesAPI> habilidadTotal(@Query ("limit") int idHabilidad);

    @GET("ability/{id}")
    Call<Habilidad> habilidadPorId(@Path("id") int idHabilidad);
}
