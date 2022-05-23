package com.example.kotlingacha.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityCardViewBinding
import com.example.kotlingacha.obj.Inventory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class CardViewActivity : AppCompatActivity() {

    companion object{
        const val IMAGE = "IMAGE"
        const val NAME = "NAME"
        const val HEIGHT = "HEIGHT"
        const val POKEDEXID = "POKEDEXID"
        const val WEIGHT = "WEIGHT"
        const val TYPE1 = "TYPE1"
        const val TYPE2 = "TYPE2"
        const val ID = "ID"
    }

    lateinit var binding: ActivityCardViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get values from MainActivity
        val image = intent.getStringExtra(IMAGE)
        val name = intent.getStringExtra(NAME)
        val height = intent.getStringExtra(HEIGHT)
        val pokedexid = intent.getStringExtra(POKEDEXID)
        val weight = intent.getStringExtra(WEIGHT)
        val type1 = intent.getStringExtra(TYPE1)
        val type2 = intent.getStringExtra(TYPE2)
        val id = intent.getStringExtra(ID)

        // Find references in layout
        val cardName = binding.cardNameTextView
        val cardViewImageButton = binding.cardViewImageButton
        val cardHeightTextView = binding.height
        val cardPokedexidTextView = binding.pokedexId
        val cardWeightTextView = binding.weight
        val cardType1ImageView = binding.type1ImageView
        val cardType2ImageView = binding.type2ImageView

        // Assign values from MainActivity to layout
        cardName.text = name
        Picasso.get()
            .load(image)
            .into(cardViewImageButton)
        cardHeightTextView.text = height
        cardPokedexidTextView.text = pokedexid
        cardWeightTextView.text = weight
        cardType1ImageView.setImageResource(getTypeImage(type1 ?: ""))
        cardType2ImageView.setImageResource(getTypeImage(type2 ?: ""))

        binding.sendCardButton.setOnClickListener{

            val sendCardDialog = AlertDialog.Builder(this)
            val input = EditText(this)
            input.hint = "User"

            sendCardDialog.setTitle("A quién le quieres mandar ${name}")
                .setView(input)
                .setPositiveButton("Ok"){ _ ,_ ->
                    val userName = input.text.toString().lowercase()

                    val cardNameUpper = cardName.text[0].uppercaseChar() + cardName.text.substring(1)

                    val db = Firebase.firestore

                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Confirmación")
                    dialog.setMessage("Realmente quieres mandar a $cardNameUpper al usuario $userName?")
                        .setPositiveButton("Si", DialogInterface.OnClickListener{ dialog, id ->
                            //Check user in DB
                            db.collection("users")
                                .document(userName)
                                .update(mapOf("name" to userName))
                                .addOnSuccessListener {
                                    //Send Card to user if exists
                                    addCardToSecondaryUser(userName, cardNameUpper, image, height, pokedexid,
                                        weight, type1, type2)
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
                                }
                        })
                        .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, id -> })

                    val alert = dialog.create()
                    alert.show()
                }
                .setNegativeButton("Cancelar", null)

            val alertShow = sendCardDialog.create()
            alertShow.show()
        }

        binding.cardViewImageButton.setOnClickListener{
            val intent = Intent(this, CardImageViewActivity::class.java)

            intent.putExtra(CardImageViewActivity.IMAGE, image)

            startActivity(intent)
        }
    }

    private fun addCardToSecondaryUser(
        userName: String, cardNameUpper: String, image: String?,
        height: String?, pokedexid: String?, weight: String?, type1: String?, type2: String?
    ){
        val db = Firebase.firestore
        db.collection("users")
            .document(userName)
            .collection("Pokemon")
            .document(cardNameUpper)
            .set((mapOf(
                "image" to image,
                "name" to cardNameUpper,
                "height" to height,
                "pokedexid" to pokedexid,
                "weight" to weight,
                "type1" to type1,
                "type2" to type2
            )))
            .addOnSuccessListener {
                //Delete selected card from your database
                deleteCardFromMainUser(cardNameUpper, userName)
            }
            .addOnFailureListener {Toast.makeText(this, "Error sending card", Toast.LENGTH_SHORT).show()}
    }

    private fun deleteCardFromMainUser(cardNameUpper: String, userName: String){
        val db = Firebase.firestore
        db.collection("users")
            .document(Firebase.auth.currentUser?.displayName ?: "")
            .collection("Pokemon")
            .document(cardNameUpper)
            .delete()

        Inventory.inventoryData.clear()

        db.collection("users")
            .document(Firebase.auth.currentUser?.displayName ?: "")
            .collection("Pokemon")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Inventory.inventoryData.add(
                        Inventory(document.get("image").toString(),
                        document.get("name").toString(), document.get("height").toString(),
                        document.get("pokedexid").toString(), document.get("weight").toString(),
                        document.get("type1").toString(), document.get("type2").toString())
                    )
                }
                finish()
            }

        Toast.makeText(this, "Sent ${cardNameUpper} to ${userName}", Toast.LENGTH_SHORT).show()
    }

    fun getTypeImage(type: String): Int {
        return when (type) {
            "fairy" -> R.drawable.fairytype
            "normal" -> R.drawable.normaltype
            "fire" -> R.drawable.firetype
            "water" -> R.drawable.watertype
            "electric" -> R.drawable.electrictype
            "grass" -> R.drawable.grasstype
            "bug" -> R.drawable.bugtype
            "flying" -> R.drawable.flyingtype
            "fighting" -> R.drawable.fightingtype
            "ice" -> R.drawable.icetype
            "rock" -> R.drawable.rocktype
            "ground" -> R.drawable.groundtype
            "poison" -> R.drawable.poisontype
            "psychic" -> R.drawable.psychictype
            "ghost" -> R.drawable.ghosttype
            "dragon" -> R.drawable.dragontype
            "dark" -> R.drawable.darktype
            "steel" -> R.drawable.steeltype
            else -> 0
        }
    }
}