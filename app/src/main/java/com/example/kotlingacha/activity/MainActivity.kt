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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        val recyclerview = binding.recyclerview

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Inventory>()

        // This loop will create all the cards on the list
        Inventory.inventoryData.forEachIndexed{ _, elem ->
            data.add(Inventory(elem.image, elem.name, elem.height, elem.pokedexid, elem.weight,
                elem.type1, elem.type2))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    //Load data from shared prefs, in the future it will be loaded from db
    private fun loadData(){
        val db = Firebase.firestore

        db.collection(Firebase.auth.currentUser?.displayName ?: "").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Inventory.inventoryData.add(Inventory(document.get("image").toString(),
                        document.get("name").toString(), document.get("height").toString(),
                        document.get("pokedexid").toString(), document.get("weight").toString(),
                        document.get("type1").toString(), document.get("type2").toString()))
                }
            }
    }
}