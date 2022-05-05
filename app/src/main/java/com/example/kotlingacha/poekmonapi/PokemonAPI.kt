package com.example.kotlingacha.poekmonapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonAPI {
    @GET("pokemon/")
    fun getPokemon(searchString: String): Call<PokemonCollection>
}