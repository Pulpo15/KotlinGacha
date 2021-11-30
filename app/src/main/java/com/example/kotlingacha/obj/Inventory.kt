package com.example.kotlingacha.obj

class Inventory(val image: Int, val name: String, val description: String) {
    companion object{
        //Static list for the game inventory
        var inventoryData = ArrayList<Inventory>();
    }
}