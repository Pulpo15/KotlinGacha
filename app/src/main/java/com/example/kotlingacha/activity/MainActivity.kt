package com.example.kotlingacha.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlingacha.adapter.CustomAdapter
import com.example.kotlingacha.obj.Inventory
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
    }

    override fun onResume() {
        super.onResume()

        // Button to access Gacha activity
        binding.gachaButton.setOnClickListener {
            val intent = Intent(this, GachaActivity::class.java)
            startActivity(intent)
        }

        // Getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Inventory>()

        // This loop will create all the cards on the list
        Inventory.inventoryData.forEachIndexed{ _, elem ->
            data.add(Inventory(elem.image, elem.name, elem.description))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    //Load data from shared prefs, in the future it will be loaded from db
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedSize = sharedPreferences.getInt("SIZE", 0)
        for (i in 0..savedSize){
            val image = sharedPreferences.getInt("IMAGE $i", R.drawable.ic_launcher_foreground)
            val name = sharedPreferences.getString("NAME $i", "Placeholder")
            val description = sharedPreferences.getString("DESCRIPTION $i", "Placeholder")
            if(name == "Placeholder")
                return
            Inventory.inventoryData.add(Inventory(image,name ?: "",description ?: ""))
        }
    }
}