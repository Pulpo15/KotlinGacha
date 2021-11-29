package com.example.kotlingacha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.random.Random

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gacha)

        findViewById<Button>(R.id.getCardButton).setOnClickListener {
            Inventory.inventoryData.add(GenerateCard())
        }

        findViewById<Button>(R.id.gachaBackButon).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun GenerateCard(): Inventory{
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

        return Inventory(imageInt, "SuperCarta $randomNum", "This is your super card enjoy this number $imageInt")
    }
}