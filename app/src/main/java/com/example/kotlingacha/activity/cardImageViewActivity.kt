package com.example.kotlingacha.activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import com.example.kotlingacha.R

class cardImageViewActivity : AppCompatActivity() {

    companion object{
        const val IMAGE = "IMAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_card_image_view)

        //Hide upper App bar
        supportActionBar?.hide()

        val image:Int = intent.getIntExtra(CardViewActivity.IMAGE, 0)

        val cardViewImageButton: ImageView = findViewById(R.id.cardImageView)

        cardViewImageButton.setImageResource(image)
    }


}