package com.example.kotlingacha.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlingacha.databinding.ActivitySendCardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SendCardActivity : AppCompatActivity() {

    lateinit var binding: ActivitySendCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SendCardButton.setOnClickListener{
            val userName = binding.userTextInputEditText.text.toString()
            val cardName = binding.cardNameTextInputEditText.text.toString()

            val cardNameUpper = cardName[0].uppercaseChar() + cardName.substring(1)

            val db = Firebase.firestore

            db.collection("users")
                .document(userName)
                .get()
                .addOnSuccessListener {
                    db.collection("users")
                        .document(userName)
                        .update(mapOf(
                            "card" to true,
                            "cardName" to cardNameUpper
                        ))
                        .addOnSuccessListener {Toast.makeText(this, "Sent ${cardNameUpper} to ${userName}", Toast.LENGTH_SHORT).show()  }
                        .addOnFailureListener {  Toast.makeText(this, "Error sending card}", Toast.LENGTH_SHORT).show()}
                }
                .addOnFailureListener{ e -> Toast.makeText(this, "Can't find ${userName} in db", Toast.LENGTH_SHORT).show()}
        }

    }
}