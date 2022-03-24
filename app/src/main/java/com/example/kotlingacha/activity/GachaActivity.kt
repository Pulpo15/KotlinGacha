package com.example.kotlingacha.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlingacha.obj.Inventory
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityGachaBinding
import com.example.kotlingacha.databinding.ActivityMainBinding
import kotlin.random.Random

class GachaActivity : AppCompatActivity() {

    lateinit var binding: ActivityGachaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGachaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuMainActivity.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_recieve_card -> startActivity(
                    Intent(this, RecieveCardActivity::class.java)
                )
                R.id.action_send_card -> startActivity(
                    Intent(this, SendCardActivity::class.java)
                )
                else -> return@setOnMenuItemClickListener false
            }
            true
        }

        binding.getCardButton.setOnClickListener {
            Inventory.inventoryData.add(generateCard())
            saveData()
        }

        binding.clearMemory.setOnClickListener{
            clearSharedPreferences()
            Inventory.inventoryData.clear()
        }

        binding.gachaBackButton.setOnClickListener{
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        saveData()
    }

    // Function to add a new card to the list using rng
    private fun generateCard(): Inventory {
        val randomNum = Random.nextInt(1, 13)

        // Set image
        val imageInt = when(randomNum){
            1-> R.drawable.turtwig
            2-> R.drawable.grotle
            3-> R.drawable.torterra
            4-> R.drawable.chimchar
            5-> R.drawable.monferno
            6-> R.drawable.infernape
            7-> R.drawable.piplup
            8-> R.drawable.prinplup
            9-> R.drawable.empoleon
            10-> R.drawable.starly
            11-> R.drawable.staravia
            12-> R.drawable.staraptor
            else-> R.drawable.piplup
        }

        // Set name
        val name = when(randomNum){
            1->"Turtwig"
            2->"Grotle"
            3->"Torterra"
            4->"Chimchar"
            5->"Monferno"
            6->"Infernape"
            7->"Piplup"
            8->"Prinplup"
            9->"Empoleon"
            10->"Starly"
            11->"Staravia"
            12->"Staraptor"
            else->"Piplup"
        }

        // Set description
        val description = when(randomNum){
            1->R.string.turtwigdescription
            2->R.string.grotledescription
            3->R.string.torterradescription
            4->R.string.chimchardescrption
            5->R.string.monfernodescription
            6->R.string.infernapedescription
            7->R.string.piplupdescription
            8->R.string.prinplupdescription
            9->R.string.empoleondescrpition
            10->R.string.starlydescription
            11->R.string.staraviadescription
            12->R.string.staraptordescription
            else->R.string.piplupdescription
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