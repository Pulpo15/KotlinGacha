package com.example.kotlingacha.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.kotlingacha.R

class CardViewActivity : AppCompatActivity() {

    companion object{
        const val IMAGE = "IMAGE"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        // Get values from MainActivity
        val image:Int = intent.getIntExtra(IMAGE, 0)
        val name:String = intent.getStringExtra(NAME).toString()
        val description:String = intent.getStringExtra(DESCRIPTION).toString()

        val cardName: TextView = findViewById(R.id.cardNameTextView)
        val cardViewImageButton: ImageButton = findViewById(R.id.cardViewImageButton)
        val cardDescriptionTextView: TextView = findViewById(R.id.cardDescriptionTextView)

        // Assign values from MainActivity
        cardName.text = name
        cardViewImageButton.setImageResource(image)
        cardDescriptionTextView.text = description

        findViewById<ImageButton>(R.id.cardViewImageButton).setOnClickListener{
            val intent = Intent(this, cardImageViewActivity::class.java)

            intent.putExtra(cardImageViewActivity.IMAGE, image)

            startActivity(intent)
        }
    }
}