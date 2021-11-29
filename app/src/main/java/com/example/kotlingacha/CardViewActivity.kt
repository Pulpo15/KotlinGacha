package com.example.kotlingacha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class CardViewActivity : AppCompatActivity() {

    companion object{
        const val IMAGE = "IMAGE"
        const val NAME = "NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        findViewById<Button>(R.id.backButton).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        val image:String = intent.getStringExtra(IMAGE).toString()
        val name:String = intent.getStringExtra(NAME).toString()

        val cardName: TextView = findViewById<TextView>(R.id.cardNameTextView)
        //val cardViewImageButton: ImageButton = findViewById<ImageButton>(R.id.cardImageButton)

        cardName.text = name

        val adapter = CardViewAdapter(this, image, name)
    }
}