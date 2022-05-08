package com.example.kotlingacha.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityCardViewBinding
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
            val intent = Intent(Intent(this, SendCardActivity::class.java))

            intent.putExtra(SendCardActivity.IMAGE, image)
            intent.putExtra(SendCardActivity.NAME, name)
            intent.putExtra(SendCardActivity.HEIGHT, height)
            intent.putExtra(SendCardActivity.POKEDEXID, pokedexid)
            intent.putExtra(SendCardActivity.WEIGHT, weight)
            intent.putExtra(SendCardActivity.TYPE1, type1)
            intent.putExtra(SendCardActivity.TYPE2, type2)
            intent.putExtra(SendCardActivity.ID, id)

            startActivity(intent)

        }

        binding.cardViewImageButton.setOnClickListener{
            val intent = Intent(this, CardImageViewActivity::class.java)

            intent.putExtra(CardImageViewActivity.IMAGE, image)

            startActivity(intent)
        }
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