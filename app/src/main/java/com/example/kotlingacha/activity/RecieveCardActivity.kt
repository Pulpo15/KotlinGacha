package com.example.kotlingacha.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityRecieveCardBinding
import com.example.kotlingacha.obj.Inventory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class RecieveCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecieveCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecieveCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        binding.checkCardButton.setOnClickListener {
            val name = binding.usernameTextInput.text.toString()
            db.collection("users")
                .document(name)
                .get()
                .addOnSuccessListener { document ->
                    val data = document.data
                    if (data?.get("card") as Boolean){
                        Inventory.inventoryData.add(generateCard(document.data?.get("cardName").toString()))
                        Toast.makeText(this, "${document.data?.get("cardName").toString()} recieved", Toast.LENGTH_SHORT).show()
                        db.collection("users")
                            .document(name)
                            .update(mapOf(
                                "card" to false,
                                "cardName" to ""
                            ))
                    } else {
                        Toast.makeText(this, "You don't have any card in your mailbox", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
                .addOnFailureListener{ e -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
        }


    //Se que està lleig tenir funcions redundants, sry :(
    private fun generateCard(pokemonName: String): Inventory {
        // Set image
        val imageInt = when(pokemonName){
            "Turtwig"->R.drawable.turtwig
            "Grotle"->R.drawable.grotle
            "Torterra"->R.drawable.torterra
            "Chimchar"->R.drawable.chimchar
            "Monferno"->R.drawable.monferno
            "Infernape"->R.drawable.infernape
            "Piplup"->R.drawable.piplup
            "Prinplup"->R.drawable.prinplup
            "Empoleon"->R.drawable.empoleon
            "Starly"->R.drawable.starly
            "Staravia"->R.drawable.staravia
            "Staraptor"->R.drawable.staraptor
            else->R.drawable.piplup
        }

        // Set name
        val name = pokemonName

        // Set description
        val description = when(pokemonName){
            "Turtwig"->R.string.turtwigdescription
            "Grotle"->R.string.grotledescription
            "Torterra"->R.string.torterradescription
            "Chimchar"->R.string.chimchardescrption
            "Monferno"->R.string.monfernodescription
            "Infernape"->R.string.infernapedescription
            "Piplup"->R.string.piplupdescription
            "Prinplup"->R.string.prinplupdescription
            "Empoleon"->R.string.empoleondescrpition
            "Starly"->R.string.starlydescription
            "Staravia"->R.string.staraviadescription
            "Staraptor"->R.string.staraptordescription
            else->R.string.piplupdescription
        }

        return Inventory(imageInt, name, getString(description))
    }
}