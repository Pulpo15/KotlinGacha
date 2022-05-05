package com.example.kotlingacha.obj

import android.net.Uri

data class Inventory(val image: Uri, val name: String, val description: String) {
    companion object{
        //Static list for the game inventory
        var inventoryData = ArrayList<Inventory>()
    }
}