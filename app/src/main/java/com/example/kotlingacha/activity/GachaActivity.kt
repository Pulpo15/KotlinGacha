package com.example.kotlingacha.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlingacha.obj.Inventory
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityGachaBinding
import com.example.kotlingacha.poekmonapi.PokemonAPI
import com.example.kotlingacha.poekmonapi.PokemonCollection
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class GachaActivity : AppCompatActivity() {

    lateinit var binding: ActivityGachaBinding

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGachaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getCardButton.setOnClickListener {
            funGetPokemon()
        }
        binding.gachaBackButton.setOnClickListener{
            finish()
        }
    }

    private fun funGetPokemon() {
        val pokedexNum = Random.nextInt(1, 899)
        val call = retrofit.create(PokemonAPI::class.java).getPokemon(pokedexNum.toString())

        binding.progressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<PokemonCollection>{
            override fun onResponse(
                call: Call<PokemonCollection>,
                response: Response<PokemonCollection>
            ) {
                val res = response.body() ?: return
                Inventory.inventoryData.add(res.getPokemon())
                saveData(res.getPokemon())
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<PokemonCollection>, t: Throwable) {
                Toast.makeText(this@GachaActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Save inventoryData to file, put first the size of the list
    private fun saveData(pkmn: Inventory){
        val db = Firebase.firestore

        val pkmnMap = hashMapOf(
            "image" to pkmn.image,
            "name" to pkmn.name,
            "height" to pkmn.height,
            "pokedexid" to pkmn.pokedexid,
            "weight" to pkmn.weight,
            "type1" to pkmn.type1,
            "type2" to pkmn.type2
        )

        db.collection("users")
            .document(Firebase.auth.currentUser?.displayName ?: "")
            .collection("Pokemon")
            .document(pkmn.name)
            .set(pkmnMap)
    }
}