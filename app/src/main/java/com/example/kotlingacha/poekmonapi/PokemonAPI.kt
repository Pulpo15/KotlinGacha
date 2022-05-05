package com.example.kotlingacha.poekmonapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {
    @GET("pokemon/{url}")
    fun getPokemon(@Path("url")searchString: String): Call<PokemonCollection>
}