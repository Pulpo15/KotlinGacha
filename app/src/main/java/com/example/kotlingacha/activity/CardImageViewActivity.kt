package com.example.kotlingacha.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityCardImageViewBinding
import com.squareup.picasso.Picasso

class CardImageViewActivity : AppCompatActivity() {

    companion object{
        const val IMAGE = "IMAGE"
    }

    lateinit var binding: ActivityCardImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityCardImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide upper App bar
        supportActionBar?.hide()

        val image = intent.getStringExtra(CardViewActivity.IMAGE)

        Picasso.get()
            .load(image)
            .into(binding.cardImageView)
    }


}