package com.example.kotlingacha.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlingacha.databinding.ActivitySendCardBinding
import com.example.kotlingacha.obj.Inventory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SendCardActivity : AppCompatActivity() {

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

    lateinit var binding: ActivitySendCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra(CardViewActivity.IMAGE)
        val cardName = intent.getStringExtra(CardViewActivity.NAME).toString()
        val height = intent.getStringExtra(CardViewActivity.HEIGHT)
        val pokedexid = intent.getStringExtra(CardViewActivity.POKEDEXID)
        val weight = intent.getStringExtra(CardViewActivity.WEIGHT)
        val type1 = intent.getStringExtra(CardViewActivity.TYPE1)
        val type2 = intent.getStringExtra(CardViewActivity.TYPE2)
        val id = intent.getStringExtra(CardViewActivity.ID)

        binding.SendCardButton.setOnClickListener{
            val userName = binding.userTextInputEditText.text.toString().lowercase()

            val cardNameUpper = cardName[0].uppercaseChar() + cardName.substring(1)

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
                    Inventory.inventoryData.add(Inventory(document.get("image").toString(),
                        document.get("name").toString(), document.get("height").toString(),
                        document.get("pokedexid").toString(), document.get("weight").toString(),
                        document.get("type1").toString(), document.get("type2").toString()))
                }
                finish()
            }

        Toast.makeText(this, "Sent ${cardNameUpper} to ${userName}", Toast.LENGTH_SHORT).show()
    }


}