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
                        db.collection("users")
                            .document(name)
                            .update(mapOf(
                                "card" to false,
                                "cardName" to ""
                            ))
                        finish()
                    } else {
                        Toast.makeText(this, "You don't have any card in your mailbox", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{ e -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
        }

    private fun generateCard(pokemonName: String): Inventory {
        // Set image
        var imageInt = 0
        when(pokemonName){
            "Turtwig" -> imageInt = R.drawable.turtwig
            "Grotle" -> imageInt = R.drawable.grotle
            "Torterra" -> imageInt = R.drawable.torterra
            "Chimchar" -> imageInt = R.drawable.chimchar
            "Monferno" -> imageInt = R.drawable.monferno
            "Infernape" -> imageInt = R.drawable.infernape
            "Piplup" -> imageInt = R.drawable.piplup
            "Prinplup" -> imageInt = R.drawable.prinplup
            "Empoleon" -> imageInt = R.drawable.empoleon
            "Starly" -> imageInt = R.drawable.starly
            "Staravia" -> imageInt = R.drawable.staravia
            "Staraptor" -> imageInt = R.drawable.staraptor
        }

        // Set name
        var name = pokemonName

        // Set description
        var description = 0
        when(pokemonName){
            "Turtwig"-> description = R.string.turtwigdescription
            "Grotle"-> description = R.string.grotledescription
            "Torterra"-> description = R.string.torterradescription
            "Chimchar"-> description = R.string.chimchardescrption
            "Monferno"-> description = R.string.monfernodescription
            "Infernape"-> description = R.string.infernapedescription
            "Piplup"-> description = R.string.piplupdescription
            "Prinplup"-> description = R.string.prinplupdescription
            "Empoleon"-> description = R.string.empoleondescrpition
            "Starly"-> description = R.string.starlydescription
            "Staravia"-> description = R.string.staraviadescription
            "Staraptor"-> description = R.string.staraptordescription
        }

        return Inventory(imageInt, name, getString(description))
    }

}