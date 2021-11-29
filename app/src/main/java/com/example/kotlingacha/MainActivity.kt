package com.example.kotlingacha

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..Inventory.inventoryData.size) {
            data.add(ItemsViewModel(Inventory.inventoryData[i].image, Inventory.inventoryData[i].name))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


    }

    fun GenerateCard(): Inventory{

        var randomNum = Random.nextInt(0, 6)
        var imageInt = 0;
        when(randomNum){
            1-> imageInt = R.drawable.ic_launcher_foreground
            2-> imageInt = R.drawable.ic_android_black_24dp_blue
            3-> imageInt = R.drawable.ic_android_black_24dp_green
            4-> imageInt = R.drawable.ic_android_black_24dp_purple
            5-> imageInt = R.drawable.ic_android_black_24dp_red
            6-> imageInt = R.drawable.ic_android_black_24dp_yellow
        }

        return Inventory(imageInt, "SuperCarta $imageInt", "This is your super card enjoy this number $imageInt")
    }
}