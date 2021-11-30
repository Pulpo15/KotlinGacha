package com.example.kotlingacha.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kotlingacha.obj.Inventory
import com.example.kotlingacha.R
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

    // Function to add a new card to the list using rng
    private fun generateCard(): Inventory {
        val randomNum = Random.nextInt(1, 13)

        // Set image
        var imageInt = 0;
        when(randomNum){
            1-> imageInt = R.drawable.turtwig
            2-> imageInt = R.drawable.grotle
            3-> imageInt = R.drawable.torterra
            4-> imageInt = R.drawable.chimchar
            5-> imageInt = R.drawable.monferno
            6-> imageInt = R.drawable.infernape
            7-> imageInt = R.drawable.piplup
            8-> imageInt = R.drawable.prinplup
            9-> imageInt = R.drawable.empoleon
            10-> imageInt = R.drawable.starly
            11-> imageInt = R.drawable.staravia
            12-> imageInt = R.drawable.staraptor
        }

        // Set name
        var name = ""
        when(randomNum){
            1-> name = "Turtwig"
            2-> name = "Grotle"
            3-> name = "Torterra"
            4-> name = "Chimchar"
            5-> name = "Monferno"
            6-> name = "Infernape"
            7-> name = "Piplup"
            8-> name = "Prinplup"
            9-> name = "Empoleon"
            10-> name = "Starly"
            11-> name = "Staravia"
            12-> name = "Staraptor"
        }

        // Set description
        var description = 0
        when(randomNum){
            1-> description = R.string.turtwigdescription
            2-> description = R.string.grotledescription
            3-> description = R.string.torterradescription
            4-> description = R.string.chimchardescrption
            5-> description = R.string.monfernodescription
            6-> description = R.string.infernapedescription
            7-> description = R.string.piplupdescription
            8-> description = R.string.prinplupdescription
            9-> description = R.string.empoleondescrpition
            10-> description = R.string.starlydescription
            11-> description = R.string.staraviadescription
            12-> description = R.string.staraptordescription
        }

        return Inventory(imageInt, name, getString(description))
    }

    // Save inventoryData to file, put first the size of the list
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

    // Editor only, erase all data
    private fun clearSharedPreferences(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}