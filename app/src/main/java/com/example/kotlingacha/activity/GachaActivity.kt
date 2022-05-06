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
            //Inventory.inventoryData.add(generateCard())
            funGetPokemon()
        }

        binding.clearMemory.setOnClickListener{
            clearSharedPreferences()
            Inventory.inventoryData.clear()
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
                saveData()
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<PokemonCollection>, t: Throwable) {
                Toast.makeText(this@GachaActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        saveData()
    }

    // Save inventoryData to file, put first the size of the list
    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putInt("SIZE", Inventory.inventoryData.size)
            Inventory.inventoryData.forEachIndexed{ i, elem ->
                putString("IMAGE $i", elem.image)
                putString("NAME $i", elem.name)
                putString("HEIGHT $i", elem.height)
                putString("POKEDEXID $i", elem.pokedexid)
                putString("WEIGHT $i", elem.weight)
                putString("TYPE1 $i", elem.type1)
                putString("TYPE2 $i", elem.type2)
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