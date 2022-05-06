package com.example.kotlingacha.poekmonapi

import com.example.kotlingacha.obj.Inventory
import com.google.gson.annotations.SerializedName

data class PokemonCollection(val name: String, val height: String,@SerializedName("id")
val pokedexid: String, val weight: String, val sprites: Sprite, val types: List<Type>) {
    data class Sprite(@SerializedName("other") val otherPhotos: OtherPhotos){
        data class OtherPhotos(@SerializedName("official-artwork") val officialArtwork: OfficialArtwork){
            data class OfficialArtwork(@SerializedName("front_default") val spriteUri: String)
        }
    }
    data class Type(@SerializedName("type") val container: NameContainer){
        data class NameContainer(val name: String)
    }

    fun getPokemon(): Inventory {
        val cardNameUpper = name[0].uppercaseChar() + name.substring(1)
        if (types.size < 2)
            return Inventory(sprites.otherPhotos.officialArtwork.spriteUri, cardNameUpper, height,
                pokedexid, weight, types[0].container.name, "")
        return Inventory(sprites.otherPhotos.officialArtwork.spriteUri, cardNameUpper, height,
            pokedexid, weight, types[0].container.name, types[1].container.name)
    }

}
