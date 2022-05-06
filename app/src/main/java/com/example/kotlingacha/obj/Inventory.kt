package com.example.kotlingacha.obj

import android.net.Uri

data class Inventory(val image: String,
                     val name: String,
                     val height: String,
                     val pokedexid: String,
                     val weight: String,
                     val type1: String,
                     val type2: String) {
    companion object{
        //Static list for the game inventory
        var inventoryData = ArrayList<Inventory>()
    }
}