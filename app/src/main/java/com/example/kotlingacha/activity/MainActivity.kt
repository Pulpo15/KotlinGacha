package com.example.kotlingacha.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
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

    companion object {
        const val NAME = "NAME"
    }

    // ArrayList of class ItemsViewModel
//    val data = ArrayList<Inventory>()

    // This will pass the ArrayList to our Adapter
    val adapter = CustomAdapter(this)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarRV.isGone = false

        // Getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        loadData {
            adapter.notifyDataSetChanged()
            if (Inventory.inventoryData.isEmpty())
                loadData {
                    adapter.notifyDataSetChanged()
                }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()

        // Button to access Gacha activity
        binding.gachaButton.setOnClickListener {
            val intent = Intent(this, GachaActivity::class.java)
            startActivity(intent)
        }
    }

    //Load data from shared prefs, in the future it will be loaded from db
    private fun loadData(onLoaded : () -> Unit){
        val db = Firebase.firestore
        db.collection("users")
            .document(Firebase.auth.currentUser?.displayName ?: NAME)
            .collection("Pokemon")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Inventory.inventoryData.add(Inventory(document.get("image").toString(),
                        document.get("name").toString(), document.get("height").toString(),
                        document.get("pokedexid").toString(), document.get("weight").toString(),
                        document.get("type1").toString(), document.get("type2").toString()))
                }
                onLoaded()
                binding.progressBarRV.isGone = true
            }
    }
}