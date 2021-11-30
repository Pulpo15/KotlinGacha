package com.example.kotlingacha

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlin.random.Random

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gacha)

        findViewById<Button>(R.id.getCardButton).setOnClickListener {
            Inventory.inventoryData.add(generateCard())
            saveData()
        }

        findViewById<Button>(R.id.clearMemory).setOnClickListener{
            clearSharedPreferences()
            Inventory.inventoryData.clear()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun generateCard(): Inventory{
        var randomNum = Random.nextInt(1, 7)
        var imageInt = 0;
        when(randomNum){
            1-> imageInt = R.drawable.ic_launcher_foreground
            2-> imageInt = R.drawable.ic_android_black_24dp_blue
            3-> imageInt = R.drawable.ic_android_black_24dp_green
            4-> imageInt = R.drawable.ic_android_black_24dp_purple
            5-> imageInt = R.drawable.ic_android_black_24dp_red
            6-> imageInt = R.drawable.ic_android_black_24dp_yellow
        }

        Log.d("Item", randomNum.toString())

        return Inventory(imageInt, "SuperCarta $randomNum",
            "This is your super card enjoy this number $randomNum"
        )
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putInt("SIZE", Inventory.inventoryData.size)
            Inventory.inventoryData.forEachIndexed{ i, elem ->
                putInt("IMAGE $i", elem.image)
                putString("NAME $i", elem.name)
                putString("DESCRIPTION $i", elem.description)
            }
        }.apply()
    }

    private fun clearSharedPreferences(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}