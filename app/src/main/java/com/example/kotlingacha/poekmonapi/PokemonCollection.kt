package com.example.kotlingacha.poekmonapi

import android.net.Uri
import com.example.kotlingacha.obj.Inventory
import com.google.gson.annotations.SerializedName

data class PokemonCollection(val name: String, val sprites: Sprite) {
    data class Sprite(@SerializedName("front_default") val spriteUri: String)

    fun getPokemon(): Inventory {
        return Inventory(sprites.spriteUri, name, "")
    }

}
